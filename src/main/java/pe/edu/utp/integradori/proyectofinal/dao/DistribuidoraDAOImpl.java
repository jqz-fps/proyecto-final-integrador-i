package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.DistribuidoraDAO;
import pe.edu.utp.integradori.proyectofinal.model.Distribuidora;

import java.sql.SQLException;
import java.util.List;

public class DistribuidoraDAOImpl implements DistribuidoraDAO {
    @Override
    public void create(Distribuidora distribuidora) throws SQLException {

    }

    @Override
    public Distribuidora read(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void update(Distribuidora distribuidora) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<Distribuidora> readAll() {
        return List.of();
    }
}
