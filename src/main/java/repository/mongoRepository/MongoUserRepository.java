package repository.mongoRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import model.*;
import model.notificaciones.Notificacion;
import model.notificaciones.PreferenciaNotificacion;
import org.bson.Document;
import org.bson.conversions.Bson;
import repository.PartidoRepository;
import repository.UserDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoUserRepository implements UserDAO {
    private final MongoDatabase db = DBConnection.getDBConnection();
    private final MongoCollection<Document> users;

    public MongoUserRepository(){
        users = db.getCollection("Users");
    }

    private Document userToDocument(Usuario user){
        Document doc = new Document();
        Document gl = new Document();
        Geolocalizacion geoLocation = user.getUbicacion();
        gl.append("ciudad", geoLocation.getCiudad())
                .append("latitud", geoLocation.getLatitud())
                .append("longitud", geoLocation.getLongitud())
                .append("varianza", geoLocation.getVarianza());
        doc.append("_id", user.getIdUsuario()).
                append("nombre", user.getNombre()).
                append("email", user.getEmail()).
                append("contrasenia", user.getContraseña()).
                append("ubicacion", gl);

        if(user.getDeportes() != null) {
            List<Document> deportes = new ArrayList<>();
            for (Deporte deporte : user.getDeportes()) {
                Document deporteDoc = new Document();
                String nombre = deporte.getNombre();
                String nivelDeJuego = deporte.getNivelJuego().toString();
                int score = deporte.getScore();
                int cantPartidos = deporte.getCantPartidos();
                deporteDoc.append("nombre", nombre)
                          .append("nivelDeJuego", nivelDeJuego)
                          .append("score", score)
                          .append("cantPartidos", cantPartidos);
                deportes.add(deporteDoc);
            }
            doc.append("deportes", deportes);
        }

        List<Document> notificaciones = new ArrayList<>();
        notificaciones.add(new Document("mensaje", "Bienvenido a la app!!"));

        doc.append("notificaciones", notificaciones);

        String preferenciaNoti = user.getPreferenciaNotificacion().name();
        doc.append("preferenciaNotificacion", preferenciaNoti);

        return doc;
    }

    private Usuario DocumentToUser(Document document){
        String idUsuario = document.getString("_id");
        String nombre = document.getString("nombre");
        String email = document.getString("email");
        String contrasenia = document.getString("contrasenia");

        Document GeoLoc = (Document) document.get("ubicacion");
        String ciudad = GeoLoc.getString("ciudad");
        Double latitud = GeoLoc.getDouble("latitud");
        Double longitud = GeoLoc.getDouble("longitud");
        Double varianza = GeoLoc.getDouble("varianza");
        Geolocalizacion gl = new Geolocalizacion(latitud, longitud, varianza, ciudad);

        List<Deporte> deportes = new ArrayList<>();
        List<Document> deportesDoc = (List<Document>) document.get("deportes");

        if (deportesDoc != null) {
            for (Document doc : deportesDoc) {
                String nombreDeporte = doc.getString("nombre");
                String nivelString = doc.getString("nivelDeJuego");
                Nivel nivel = Nivel.valueOf(nivelString);
                int score = doc.getInteger("score", 0);
                int cantPartidos = doc.getInteger("cantPartidos", 0);
                Deporte d = new Deporte(nombreDeporte, nivel, score, cantPartidos);
                deportes.add(d);
            }
        }
        //se asigna por defecto
        PreferenciaNotificacion preferencia = PreferenciaNotificacion.EMAIL_PREFERENCE;
        try {
            String notificacionPref = document.getString("preferenciaNotificacion");
            preferencia = PreferenciaNotificacion.valueOf(notificacionPref);
        }catch (Exception e){
            System.out.println("Error buscando las preferencias de notificación: "+e.getMessage());
        }

        return new Usuario(idUsuario, nombre, email, contrasenia, deportes, gl, preferencia);
    }

    @Override
    public void save(Usuario usuario) {
        Document doc = userToDocument(usuario);
        System.out.println("Insertando documento: " + doc.toJson());
        try {
            users.insertOne(doc);
            System.out.println("Usuario guardado");
        } catch (Exception e) {
            System.out.println("Error guardando el usuario: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        Bson filter = eq("_id", id);
        try {
            users.deleteOne(filter);
            System.out.println("Usuario eliminado");
        } catch (Exception e) {
            System.out.println("Error eliminando el usuario: " + e.getMessage());
        }
    }

    public Usuario findById(String idUsuario) {
        Document user = users.find(eq("_id", idUsuario)).first();

        if(user != null) {
            return DocumentToUser(user);
        } else {
            System.out.println("Usuario no encontrado");
        }

        return null;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> userList = new ArrayList<>();
        for(Document userDoc : users.find()){
            Usuario u = DocumentToUser(userDoc);
            userList.add(u);
        }
        return userList;
    }

    @Override
    public Usuario findByField(String field, String value) {
        try {
            Document user = users.find(eq(field, value)).first();
            return DocumentToUser(user);
        } catch (Exception e) {
            System.out.println("Usuario no encontrado: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Usuario usuario) {
        Document doc = userToDocument(usuario);
        Bson filter = eq("_id", usuario.getIdUsuario());
        try {
            users.replaceOne(filter, doc);
            System.out.println("Usuario actualizado");
        } catch (Exception e) {
            System.out.println("Error actualizando usuario: " + e.getMessage());
        }
    }

    public List<Usuario> findByDeporte(String deporte) {
        List<Usuario> usuarios = new ArrayList<>();
        Bson filtro = Filters.elemMatch("deportes", Filters.eq("nombre", deporte));

        try (MongoCursor<Document> cursor = users.find(filtro).iterator()) {
            while (cursor.hasNext()) {
                usuarios.add(DocumentToUser(cursor.next()));
            }
        } catch (Exception e) {
            System.out.println("Error buscando usuarios por deporte: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public void notificarUsuario(String userID, Notificacion notificacion) {
        Bson filter = eq("_id", userID);
        Document notiDoc = new Document().append("mensaje", notificacion.getMensaje()).append("partidoID", notificacion.getPartido().getIdPartido());
        Document update = new Document("$push", new Document("notificaciones", notiDoc));

        try {
            users.updateOne(filter, update);
        } catch (Exception e) {
            System.out.println("Error notificando usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Notificacion> getNotificaciones(String userID) {
        PartidoRepository partidoRepository = new PartidoRepository();
        List<Notificacion> notificaciones = new ArrayList<>();
        Bson filter = eq("_id", userID);
        try {
            Document userDoc = users.find(filter).first();
            if (userDoc != null && userDoc.containsKey("notificaciones")) {
                List<Document> notificacionesDoc = userDoc.getList("notificaciones", Document.class);
                for (Document doc : notificacionesDoc) {
                    String mensaje = doc.getString("mensaje");
                    Partido partido = partidoRepository.findById(doc.getString("partidoID"));
                    Notificacion n = new Notificacion(partido, mensaje);
                    notificaciones.add(n);
                }
            }
        } catch (Exception e) {
            System.out.println("Error buscando notificaciones: ");
            e.printStackTrace();
        }
        return notificaciones;
    }

    @Override
    public void actualizarScore(String userID, String deporte, int score) {
        Bson filter = eq("_id", userID);
        Bson update = Updates.inc("deportes.$[dep].score", score);
        UpdateOptions options = new UpdateOptions().arrayFilters(
                Arrays.asList(Filters.eq("dep.nombre", deporte))
        );

        try {
            users.updateOne(filter, update, options);
            System.out.println("score actualizado");
        } catch (Exception e) {
            System.out.println("Error actualizando score: " + e.getMessage());
        }
    }

    @Override
    public void actualizarCantPartidos(String userID, String deporte) {
        Bson filter = eq("_id", userID);
        Bson update = Updates.inc("deportes.$[dep].cantPartidos", 1);
        UpdateOptions options = new UpdateOptions().arrayFilters(
                Arrays.asList(Filters.eq("dep.nombre", deporte))
        );

        try {
            users.updateOne(filter, update, options);
            System.out.println("Cantidad de partidos incrementada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al incrementar cantidad de partidos: " + e.getMessage());
        }
    }

    @Override
    public void agregarDeporteFavorito(Deporte d) {
        //implementar después, no es muy relevante
    }

    @Override
    public void eliminarDeporteFavorito(Deporte d) {
        //implementar después, no es muy relevante
    }
}
