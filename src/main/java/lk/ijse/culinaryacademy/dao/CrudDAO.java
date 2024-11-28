package lk.ijse.culinaryacademy.dao;

import java.util.List;

public interface CrudDAO<T> extends SuperDAO {

    boolean add(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    boolean delete(String id) throws Exception;

    String currentId() throws Exception;

    List<T> getAll() throws Exception;

    List<String> getIds() throws Exception;

    T searchById(String id) throws Exception;
}
