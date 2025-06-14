package repository;

import model.Partido;
import repository.mongoRepository.MongoPartidoRepository;

import java.util.List;

public class PartidoRepository implements PartidoDAO{
    DAO<Partido> repo = new MongoPartidoRepository();

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
    public void pendientehaceresto() {

    }
}
