package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.DetalleVentaDAO;
import pe.edu.utp.integradori.proyectofinal.model.DetalleVenta;

import java.sql.SQLException;
import java.util.List;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {
    @Override
    public void create(DetalleVenta detalleVenta) throws SQLException {

    }

    @Override
    public DetalleVenta read(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void update(DetalleVenta detalleVenta) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<DetalleVenta> readAll() {
        return List.of();
    }
}
