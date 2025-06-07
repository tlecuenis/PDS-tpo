package repository;

import java.util.List;

public interface DAO <T>{
    void save(T obj);
    void deleteById(String id);
    T findById(String id);
    List<T> findAll();
    T findByField(String field, String value);
}
