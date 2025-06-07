package repository.mongoRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import repository.UserDAO;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoUserRepository implements UserDAO {
    public final MongoDatabase db = DBConnection.getDBConnection();
    public final MongoCollection<Document> users;

    public MongoUserRepository(){
        users = db.getCollection("Users");
    }

    /*  ESQUEMA EN LA DB POR SI QUIEREN CAMBIAR/IMPLEMENTAR ALGO
_id: "1"
nombre: "juan"
email: "juan@mail.com"
contrasenia: "juan1234"
ubicacion: "San Miguel"
deportes:Array (3)
    0: Object
    nombre:"fútbol"
    nivelDeJuego:"INTERMEDIO"
    1:Object
    nombre:"Basquet"
    nivelDeJuego:"INTERMEDIO"
    2:Object
    nombre:"boxeo"
    nivelDeJuego:"INTERMEDIO"
*/


    private Document userToDocument(Usuario user){
        Document doc = new Document();
        doc.append("_id", user.getIdUsuario()).
                append("nombre", user.getNombre()).
                append("email", user.getEmail()).
                append("contrasenia", user.getContraseña()).
                append("ubicacion", user.getUbicacion().getCiudad());


        //Se puede mejorar usando programacion funcional con streams
        if(user.getDeportes() != null) {
            List<Document> deportes = new ArrayList<Document>();
            for (Deporte deporte : user.getDeportes()) {
                Document deporteDoc = new Document();
                String nombre = deporte.getNombre();
                String nivelDeJuego = deporte.getNivelJuego().toString();
                deporteDoc.append("nombre",nombre).
                        append("nivelDeJuego", nivelDeJuego);
                deportes.add(deporteDoc);
            }
            doc.append("deportes", deportes);
        }
        return doc;
    }

    private Usuario DocumentToUser(Document document){
        String idUsuario = document.getString("_id");
        String nombre = document.getString("nombre");
        String email = document.getString("email");
        String contrasenia = document.getString("contrasenia");
        Geolocalizacion gl = new Geolocalizacion(10.4, 0.2, 111.2, document.getString("ciudad"));

        List<Deporte> deportes = new ArrayList<>();
        List<Document> deportesDoc = (List<Document>) document.get("deportes");

        for(Document doc : deportesDoc){
            String nombreDeporte = doc.getString("nombre");
            String nivelString = doc.getString("nivelDeJuego");
            Nivel nivel = Nivel.valueOf(nivelString);
            Deporte d = new Deporte(nombreDeporte, nivel);
            deportes.add(d);
        }

        return new Usuario(idUsuario, nombre, email, contrasenia, deportes, gl);
    }

    @Override
    public void save(Usuario usuario) {
        Document doc = userToDocument(usuario);
        System.out.println("Insertando documento: " + doc.toJson());
        try {
            users.insertOne(doc);
            System.out.println("Usuario guardado");
        }
        catch (Exception e) {
            System.out.println("Error guardando el usuario: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        Bson filter = eq("_id",id);
        try{
            users.deleteOne(filter);
            System.out.println("Usuario eliminado");
        }
        catch (Exception e) {
            System.out.println("Error eliminando el usuario: " + e.getMessage());
        }
    }


    //Si necesitan usar los datos para alguna vista y no quieren mostrar la contraseña por ejemplo
    //Pueden excluirla del resultado de la query para manejarlo directamente acá y no en la vista
    //Se hace con los projections fields, les dejo ejemplo
    public Usuario findById(String idUsuario) {
        /*
        Bson projection_fields = Projections.fields(
                Projections.exclude("_id", "contrasenia"));
        Document user = users.find(eq("_id",idUsuario)).projection(projection_fields).first();
        */
        Document user = users.find(eq("_id",idUsuario)).first();

        if(user != null) {
            return DocumentToUser(user);
        }
        else {
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
    //Pensar cómo hacer que funcione para deportes, que son un array de objetos
    public Usuario findByField(String field, String value) {
        try {
            Document user = users.find(eq(field, value)).first();
            return DocumentToUser(user);
        }
        catch (Exception e) {
            System.out.println("Usuario no encontrado: "+e.getMessage());
        }
        return null;
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
