package repository.mongoRepository;

import model.Equipo;
import model.Geolocalizacion;
import model.Partido;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Usuario;
import org.bson.Document;
import repository.PartidoDAO;

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

    }

    @Override
    public Partido findById(String id) {
        return null;
    }

    @Override
    public List<Partido> findAll() {
        return null;
    }

    @Override
    public Partido findByField(String field, String value) {
        return null;
    }

    @Override
    public void pendientehaceresto() {

    }
}
