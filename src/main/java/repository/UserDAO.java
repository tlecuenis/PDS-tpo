package repository;

import model.Deporte;
import model.Usuario;

public interface UserDAO extends DAO<Usuario>{
    void agregarDeporteFavorito(Deporte d);
    void eliminarDeporteFavorito(Deporte d);
}
