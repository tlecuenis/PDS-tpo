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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        if (g != null) {
            geoLocation.append("ciudad", g.getCiudad())
                    .append("latitud", g.getLatitud())
                    .append("longitud", g.getLongitud())
                    .append("varianza", g.getVarianza());
            d.append("Geolocalizacion", geoLocation);
        }

        if (p.getFecha() != null) {
            d.append("horario", p.getFecha().toString());
        }

        List<Document> equipos = new ArrayList<>();
        if (p.getEquipos() != null) {
            for(Equipo e : p.getEquipos()){
                Document equipo = new Document();
                equipo.append("nombre", e.getNombre());
                List<Document> players = new ArrayList<>();
                if (e.getJugadores() != null) {
                    for (Usuario u : e.getJugadores()) {
                        if (u != null) {
                            Document player = new Document();
                            player.append("id", u.getIdUsuario())
                                  .append("nombre", u.getNombre());
                            players.add(player);
                        }
                    }
                }
                equipo.append("jugadores", players);
                equipos.add(equipo);
            }
        }
        d.append("equipos", equipos);

        d.append("estado", p.getEstado() != null ? p.getEstado().getClass().getSimpleName() : null);
        d.append("estadistica", p.getEstadistica());
        d.append("comentario", p.getComentario());

        if(p.getObservador() != null){
            List<Document> observadores = new ArrayList<>();
            for(IObserver o : p.getObservador()){
                if (o instanceof Usuario) {
                    Usuario obs = (Usuario) o;
                    Document observador = new Document();
                    observador.append("id", obs.getIdUsuario());
                    observador.append("nombre", obs.getNombre());
                    observadores.add(observador);
                }
            }
            d.append("observadores", observadores);
        }

        d.append("nivelMinimo", p.getNivelJugadorMinimo());
        d.append("nivelMaximo", p.getNivelJugadorMaximo());

        // Validación para creador no nulo
        if (p.getCreador() != null) {
            System.out.println("Guardando creadorId: " + p.getCreador().getIdUsuario());
            d.append("creadorId", p.getCreador().getIdUsuario());
        } else {
            System.out.println("Creador es null al guardar Partido");
        }

        return d;
    }

    private Partido documentToPartido(Document d) {
        if (d == null) return null;

        String idPartido = d.getString("_id");
        String deporte = d.getString("deporte");
        int cantidadJugadores = d.getInteger("cantJugadores", 0);
        double duracion = d.getDouble("duracion") != null ? d.getDouble("duracion") : 0.0;

        Geolocalizacion geoLoc = null;
        Document geoDoc = (Document) d.get("Geolocalizacion");
        if (geoDoc != null) {
            String ciudad = geoDoc.getString("ciudad");
            double latitud = geoDoc.getDouble("latitud") != null ? geoDoc.getDouble("latitud") : 0.0;
            double longitud = geoDoc.getDouble("longitud") != null ? geoDoc.getDouble("longitud") : 0.0;
            double varianza = geoDoc.getDouble("varianza") != null ? geoDoc.getDouble("varianza") : 0.0;
            geoLoc = new Geolocalizacion(latitud, longitud, varianza, ciudad);
        }

        LocalDateTime horario = null;
        String fechaStr = d.getString("horario");
        if (fechaStr != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                        "EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(fechaStr, formatter);
                horario = zonedDateTime.toLocalDateTime();
            } catch (Exception e) {
                // Si no puede parsear, intentar con LocalDateTime.parse simple
                try {
                    horario = LocalDateTime.parse(fechaStr);
                } catch (Exception ex) {
                    horario = null;
                }
            }
        }

        List<IObserver> observadores = new ArrayList<>();
        List<Document> observadoresDoc = (List<Document>) d.get("observadores");
        if (observadoresDoc != null) {
            for (Document obsDoc : observadoresDoc) {
                String idObs = obsDoc.getString("id");
                Usuario obs = userRepository.findById(idObs);
                if (obs != null) {
                    observadores.add(obs);
                }
            }
        }

        String estadistica = d.getString("estadistica");
        String comentario = d.getString("comentario");

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

        int nivelMinimo = d.getInteger("nivelMinimo", 0);
        int nivelMaximo = d.getInteger("nivelMaximo", 0);

        Partido partido = new Partido(idPartido, deporte, duracion, cantidadJugadores, geoLoc, horario, state, estadistica, comentario, observadores, nivelMinimo, nivelMaximo);

        // Creador
        String creadorId = d.getString("creadorId");
        System.out.println("creadorId leído de la DB: " + creadorId);
        if (creadorId != null) {
            Usuario creador = userRepository.findById(creadorId);
            if (creador == null) {
                System.out.println("No se encontró usuario con creadorId: " + creadorId);
            }
            partido.setCreador(creador);
        } else {
            System.out.println("No existe campo creadorId en el documento");
        }

        List<Equipo> equipos = new ArrayList<>();
        List<Document> equiposDoc = (List<Document>) d.get("equipos");
        if (equiposDoc != null) {
            for (Document equipoDoc : equiposDoc) {
                String nombreEquipo = equipoDoc.getString("nombre");
                Equipo e = new Equipo();
                e.setNombre(nombreEquipo);
                partido.crearEquipo(e);

                List<Document> jugadoresDoc = (List<Document>) equipoDoc.get("jugadores");
                if (jugadoresDoc != null) {
                    for (Document jugadorDoc : jugadoresDoc) {
                        Usuario u = userRepository.findById(jugadorDoc.getString("id"));
                        if (u != null) {
                            partido.añadirAlEquipo(u, nombreEquipo);
                        }
                    }
                }
            }
        }
        return partido;
    }

    @Override
    public void save(Partido p) {
        Document doc = partidoToDocument(p);
        try {
            partidos.replaceOne(eq("_id", p.getIdPartido()), doc, new com.mongodb.client.model.ReplaceOptions().upsert(true));
            System.out.println("partido insertado o actualizado");
        } catch(Exception e){
            System.out.println("Error al guardar el partido: " + e.getMessage());
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
        // pendiente implementar
    }
}
