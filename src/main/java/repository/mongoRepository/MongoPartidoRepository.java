package repository.mongoRepository;

import model.Equipo;
import model.Geolocalizacion;
import model.Partido;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Usuario;
import org.bson.Document;
import repository.PartidoDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MongoPartidoRepository implements PartidoDAO {
    private final MongoDatabase db = DBConnection.getDBConnection();
    private final MongoCollection<Document> partidos;

    public MongoPartidoRepository() {
        partidos = db.getCollection("Partidos");
    }

    private Document partidoToDocument(Partido p){
        Document d = new Document();
        d.append("_id", p.getIdPartido());
        Document deporte = new Document();
        deporte.append("nombre", p.getDeporte();
        deporte.append("nivelDeJuego", p.getDeporte().getNivelJuego().toString());
        d.append("deporte", deporte);
        d.append("cantJugadores", p.getCantJugadores());
        d.append("duracion", p.getDuracion());
        Document geoLocation = new Document();
        Geolocalizacion g = p.getUbicacion();
        geoLocation.append("ciudad", g.getCiudad())
                .append("latitud", g.getLatitud())
                .append("longitud", g.getLongitud())
                .append("varianza", g.getVarianza());
        d.append("Geolocalizacion", geoLocation);
        d.append("horario", p.getHorario().toString());

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
            for(Usuario o : p.getObservador().getDestinatarios()){
                Document observador = new Document();
                observador.append("id", o.getIdUsuario());
                observador.append("nombre", o.getNombre());
                observadores.add(observador);
            }
            d.append("observadores", observadores);
        }
        return d;
    }

    @Override
    public void save(Partido p) {
        Document doc = partidoToDocument(p);

        try {
            Document filtro = new Document("_id", p.getIdPartido());
            Document existente = partidos.find(filtro).first();

            if (existente != null) {
                partidos.replaceOne(filtro, doc); // Actualiza el existente
                System.out.println("Partido actualizado");
            } else {
                partidos.insertOne(doc); // Nuevo
                System.out.println("Partido insertado");
            }
        } catch (Exception e) {
            System.out.println("Error guardando el partido: " + e.getMessage());
        }
    }


    @Override
    public void deleteById(String id) {

    }

    @Override
    public Partido findById(String id) {
        return null;
    }

    @Override
    public List<Partido> findAll() {
        List<Partido> partidosList = new ArrayList<>();

        for (Document doc : partidos.find()) {
            Partido partido = new Partido();
            partido.setIdPartido(doc.getString("_id"));
            partido.setDeporte(doc.getString("deporte")); // simplificado, adaptar si es objeto
            partido.setCantJugadores(doc.getInteger("cantJugadores"));
            partido.setDuracion(doc.getDouble("duracion"));

            // Geolocalización
            Document geoDoc = (Document) doc.get("Geolocalizacion");
            Geolocalizacion geo = new Geolocalizacion(
                    geoDoc.getString("ciudad"),
                    geoDoc.getDouble("latitud"),
                    geoDoc.getDouble("longitud"),
                    geoDoc.getDouble("varianza")
            );
            
            partido.setUbicacion(geo);

            // Fecha
            partido.setFecha(LocalDateTime.parse(doc.getString("horario")));

            // Equipos y jugadores
            List<Document> equiposDoc = (List<Document>) doc.get("equipos");
            for (Document equipoDoc : equiposDoc) {
                Equipo equipo = new Equipo();
                equipo.setNombre(equipoDoc.getString("nombre"));
                List<Document> jugadoresDoc = (List<Document>) equipoDoc.get("jugadores");
                for (Document jugadorDoc : jugadoresDoc) {
                    Usuario jugador = new Usuario();
                    jugador.setIdUsuario(jugadorDoc.getString("id"));
                    jugador.setNombre(jugadorDoc.getString("nombre"));
                    equipo.agregarJugador(jugador);
                }
                partido.crearEquipo(equipo);
            }

            // Creador (opcional: adaptar si lo guardás en la base)
            // Estado, observador, etc. pueden agregarse si están serializados también

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
