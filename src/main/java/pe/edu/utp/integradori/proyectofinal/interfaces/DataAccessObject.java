package pe.edu.utp.integradori.proyectofinal.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface DataAccessObject<E, K> {
    void create(E e) throws SQLException;
    E read(K id) throws SQLException;
    void update(E e) throws SQLException;
    void delete(K id) throws SQLException;

    List<E> readAll();
}
