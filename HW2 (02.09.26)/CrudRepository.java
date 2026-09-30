package DZ2;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findAll() throws SQLException;
    Optional<T> findById(Long id) throws SQLException;
    void save(T entity) throws SQLException;
    void update (T entity) throws SQLException;
    void remove (T entity)throws SQLException;
    void removeById(Long id) throws SQLException;
}