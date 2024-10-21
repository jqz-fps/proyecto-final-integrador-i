package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.CategoriaDAO;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;
import pe.edu.utp.integradori.proyectofinal.model.Farmaco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl extends Conexion implements CategoriaDAO {
    @Override
    public void create(Categoria categoria) throws SQLException {

    }

    @Override
    public Categoria read(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void update(Integer id) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<Categoria> readAll() {
        return List.of();
    }
}
