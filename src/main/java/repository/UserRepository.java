package repository;
import model.Usuario;
import repository.mongoRepository.MongoUserRepository;
import java.util.List;

public class UserRepository implements DAO<Usuario> {
    //Si queres cambiar la db simplemente instancias otra implementacion del mismo DAO, por ej MYSQL
    DAO<Usuario> repo = new MongoUserRepository();

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
}
