package repository;

import model.Partido;
import model.Usuario;
import model.notificaciones.IObserver;

public interface PartidoDAO extends DAO<Partido> {
    void agregarJugador(String partidoID, String equipo, Usuario usuario);
    void eliminarJugador(String partidoID, String equipo, Usuario usuario);
    void agregarObserver(String partidoID, IObserver observer);
    void eliminarObserver(String partidoID, IObserver observer);
}
