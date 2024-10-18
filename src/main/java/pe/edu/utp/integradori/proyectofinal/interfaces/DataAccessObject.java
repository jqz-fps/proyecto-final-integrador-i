package pe.edu.utp.integradori.proyectofinal.interfaces;

import java.sql.SQLException;

public interface DataAccessObject<E, K> {
    void create(E e) throws SQLException;
    E read(K id) throws SQLException;
    void update(K id) throws SQLException;
    void delete(K id) throws SQLException;
}
