package repository.mongoRepository;

import model.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.notificaciones.IObserver;
import org.bson.Document;
import repository.PartidoDAO;
import repository.UserRepository;
import org.bson.conversions.Bson;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;

public class MongoPartidoRepository implements PartidoDAO {
    private final MongoDatabase db = DBConnection.getDBConnection();
    private final MongoCollection<Document> partidos;
    private final UserRepository userRepository = new UserRepository();

    public MongoPartidoRepository() {
        partidos = db.getCollection("Partidos");
    }

    private Document partidoToDocument(Partido p){
        Document d = new Document();
        d.append("_id", p.getIdPartido());
        d.append("deporte", p.getDeporte());
        d.append("cantJugadores", p.getCantJugadores());
        d.append("duracion", p.getDuracion());
        Document geoLocation = new Document();
        Geolocalizacion g = p.getUbicacion();
        geoLocation.append("ciudad", g.getCiudad())
                .append("latitud", g.getLatitud())
                .append("longitud", g.getLongitud())
                .append("varianza", g.getVarianza());
        d.append("Geolocalizacion", geoLocation);
        d.append("horario", p.getFecha().toString());

        List<Document> equipos = new ArrayList<>();
        for(Equipo e : p.getEquipos()){
            Document equipo = new Document();
            equipo.append("nombre", e.getNombre());
            List<Document> players = new ArrayList<>();
            for (Usuario u : e.getJugadores()) {
                Document player = new Document();
                player.append("id", u.getIdUsuario())
                        .append("nombre", u.getNombre());
                players.add(player);
            }
            equipo.append("jugadores", players);
            equipos.add(equipo);
        }
        d.append("equipos", equipos);
        d.append("estado", p.getEstado());
        d.append("estadistica", p.getEstadistica());
        d.append("comentario", p.getComentario());

        if(p.getObservador() != null){
            List<Document> observadores = new ArrayList<>();
            for(IObserver o : p.getObservador()){
                Usuario obs = (Usuario) o;
                Document observador = new Document();
                observador.append("id", obs.getIdUsuario());
                observador.append("nombre", obs.getNombre());
                observadores.add(observador);
            }
            d.append("observadores", observadores);
        }

        d.append("nivelMinimo", p.getNivelJugadorMinimo());
        d.append("nivelMaximo", p.getNivelJugadorMaximo());

        return d;
    }

    private Partido documentToPartido(Document d) {
        String idPartido = d.getString("_id");

        // Deporte
        String deporte = d.getString("deporte");

        // Cantidad jugadores
        int cantidadJugadores = d.getInteger("cantJugadores");
        //Duración
        double duracion = d.getDouble("duracion");

        // Geolocalización
        Document geoDoc = (Document) d.get("Geolocalizacion");
        String ciudad = geoDoc.getString("ciudad");
        double latitud = geoDoc.getDouble("latitud");
        double longitud = geoDoc.getDouble("longitud");
        double varianza = geoDoc.getDouble("varianza");
        Geolocalizacion geoLoc = new Geolocalizacion(latitud, longitud, varianza, ciudad);

        // Horario
        String fechaStr = d.getString("horario");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(fechaStr, formatter);
        LocalDateTime horario = zonedDateTime.toLocalDateTime();


        // Observadores
        List<IObserver> observadores = new ArrayList<>();
        List<Document> observadoresDoc = (List<Document>) d.get("observadores");

        for (Document obsDoc : observadoresDoc) {
            String idObs = obsDoc.getString("id");
            Usuario obs = userRepository.findById(idObs);
            observadores.add(obs);
        }

        //comentarios y estadisticas
        String estadistica = d.getString("estadistica");
        String comentario = d.getString("comentario");

        // El estado se puede setear si se serializa como string, por ejemplo:
        String estado = d.getString("estado");
        IEstadoPartido state = null;
        if (estado != null) {
            switch (estado) {
                case "Confirmado":
                    state = new Confirmado();
                    break;
                case "Armado":
                    state = new Armado();
                    break;
                case "Cancelado":
                    state =  new Cancelado();
                    break;
                case "NecesitamosJugadores":
                    state =  new NecesitamosJugadores();
                    break;
                default:
                    break;
            }
        }

        //niveles
        int nivelMinimo = d.getInteger("nivelMinimo");
        int nivelMaximo = d.getInteger("nivelMaximo");

        Partido partido = new Partido(idPartido, deporte, duracion, cantidadJugadores, geoLoc, horario, state, estadistica, comentario, observadores, nivelMinimo, nivelMaximo);

        // Equipos
        List<Equipo> equipos = new ArrayList<>();
        List<Document> equiposDoc = (List<Document>) d.get("equipos");

        for (Document equipoDoc : equiposDoc) {
            String nombreEquipo = equipoDoc.getString("nombre");
            Equipo e = new Equipo();
            e.setNombre(nombreEquipo);
            partido.crearEquipo(e);
            List<Usuario> jugadores = new ArrayList<>();
            List<Document> jugadoresDoc = (List<Document>) equipoDoc.get("jugadores");

            for (Document jugadorDoc : jugadoresDoc) {
                Usuario u = userRepository.findById(jugadorDoc.getString("id"));
                partido.añadirAlEquipo(u, nombreEquipo);
            }
        }
        return partido;
    }

    @Override
    public void save(Partido p) {
        Document doc = partidoToDocument(p);
        try{
            partidos.insertOne(doc);
            System.out.println("partido agregado");
        }
        catch(Exception e){
            System.out.println("Error agregando el partido " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        Bson filter = eq("_id", id);
        try {
            partidos.deleteOne(filter);
            System.out.println("partido eliminado");
        }
        catch(Exception e){
            System.out.println("Error eliminando el partido " + e.getMessage());
        }

    }

    @Override
    public Partido findById(String id) {
        Partido partido = null;
        Document partidoDoc = null;
        try {
            partidoDoc = partidos.find(eq("_id", id)).first();
        }
        catch (Exception e){
            System.out.println("Error buscando el partido " + e.getMessage());
        }
        partido = documentToPartido(partidoDoc);
        return partido;
    }

    @Override
    public List<Partido> findAll() {
        List<Partido> partidosList = new ArrayList<>();
        for (Document doc : partidos.find()) {
            Partido partido = documentToPartido(doc);
            partidosList.add(partido);
        }
        return partidosList;
    }

    @Override
    public Partido findByField(String field, String value) {
        return null;
    }

    @Override
    public void pendientehaceresto() {

    }
}
