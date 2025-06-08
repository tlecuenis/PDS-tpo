package repository;

import model.Deporte;
import model.Usuario;

import java.util.List;

public interface UserDAO extends DAO<Usuario>{
    void agregarDeporteFavorito(Deporte d);
    void eliminarDeporteFavorito(Deporte d);
    void update(Usuario usuario);
    List<Usuario> findByDeporte(String deporte);
}
