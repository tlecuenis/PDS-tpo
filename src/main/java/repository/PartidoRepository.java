package repository;

import model.Partido;
import model.Usuario;
import model.notificaciones.IObserver;
import repository.mongoRepository.MongoPartidoRepository;

import java.util.List;

public class PartidoRepository implements PartidoDAO{
    PartidoDAO repo = new MongoPartidoRepository();

    @Override
    public void save(Partido partido) {
        repo.save(partido);
    }

    @Override
    public void deleteById(String id) {
        repo.deleteById(id);
    }

    @Override
    public Partido findById(String id) {
        return repo.findById(id);
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
    public void agregarJugador(String partidoID, String equipo, Usuario usuario) {
        repo.agregarJugador(partidoID, equipo, usuario);
    }

    @Override
    public void eliminarJugador(String partidoID, String equipo, Usuario usuario) {
        repo.eliminarJugador(partidoID, equipo, usuario);
    }

    @Override
    public void agregarObserver(String partidoID, IObserver observer) {
        repo.agregarObserver(partidoID, observer);
    }

    @Override
    public void eliminarObserver(String partidoID, IObserver observer) {
        repo.eliminarObserver(partidoID, observer);
    }
}
