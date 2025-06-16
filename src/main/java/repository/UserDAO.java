package repository;

import model.Deporte;
import model.Usuario;
import model.notificaciones.Notificacion;

import java.util.List;

public interface UserDAO extends DAO<Usuario>{
    void agregarDeporteFavorito(Deporte d);
    void eliminarDeporteFavorito(Deporte d);
    void update(Usuario usuario);
    List<Usuario> findByDeporte(String deporte);
    void notificarUsuario(String userID, Notificacion notificacion);
    List<Notificacion> getNotificaciones(String userID);
    void actualizarScore(String userID, String deporte,int score);
    void actualizarCantPartidos(String userID, String deporte);
}
