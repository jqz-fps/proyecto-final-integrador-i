package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.LaboratorioDAO;
import pe.edu.utp.integradori.proyectofinal.model.Laboratorio;

import java.sql.SQLException;
import java.util.List;

public class LaboratorioDAOImpl implements LaboratorioDAO {
    @Override
    public void create(Laboratorio laboratorio) throws SQLException {

    }

    @Override
    public Laboratorio read(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void update(Laboratorio laboratorio) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<Laboratorio> readAll() {
        return List.of();
    }
}
