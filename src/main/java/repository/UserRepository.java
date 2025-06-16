package repository;
import model.Deporte;
import model.Usuario;
import model.notificaciones.Notificacion;
import repository.mongoRepository.MongoUserRepository;
import java.util.List;

public class UserRepository implements UserDAO {
    //Si queres cambiar la db simplemente instancias otra implementacion del mismo DAO, por ej MYSQL
    UserDAO repo = new MongoUserRepository();

    @Override
    public void save(Usuario user) {
        repo.save(user);
    }

    @Override
    public void deleteById(String id) {
        repo.deleteById(id);
    }

    @Override
    public Usuario findById(String id) {
        Usuario user = repo.findById(id);
        return user;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> users = repo.findAll();
        return users;
    }

    @Override
    public Usuario findByField(String field, String value) {
        Usuario user = repo.findByField(field, value);
        return user;
    }

    @Override
    public void agregarDeporteFavorito(Deporte d) {
        repo.agregarDeporteFavorito(d);
    }

    @Override
    public void eliminarDeporteFavorito(Deporte d) {
        repo.eliminarDeporteFavorito(d);
    }

    @Override
    public void update(Usuario usuario) {
        repo.update(usuario);
    }

    @Override
    public List<Usuario> findByDeporte(String deporte) {
        return repo.findByDeporte(deporte);
    }

    @Override
    public void notificarUsuario(String userID, Notificacion notificacion) {
        repo.notificarUsuario(userID, notificacion);
    }

    @Override
    public List<Notificacion> getNotificaciones(String userID) {
        return repo.getNotificaciones(userID);
    }

    @Override
    public void actualizarScore(String userID, String deporte, int score) {
        repo.actualizarScore(userID, deporte, score);
    }

    @Override
    public void actualizarCantPartidos(String userID, String deporte) {
        repo.actualizarCantPartidos(userID, deporte);
    }
}
