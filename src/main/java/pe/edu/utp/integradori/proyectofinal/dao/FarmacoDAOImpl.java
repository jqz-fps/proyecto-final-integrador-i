package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.FarmacoDAO;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;
import pe.edu.utp.integradori.proyectofinal.model.Farmaco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FarmacoDAOImpl extends Conexion implements FarmacoDAO {

    @Override
    public void create(Farmaco farmaco) throws SQLException {

    }

    @Override
    public Farmaco read(Integer id) throws SQLException {
        String query = "SELECT * FROM farmaco WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            LaboratorioDAOImpl laboratorioDAO = new LaboratorioDAOImpl();
            CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
            DistribuidoraDAOImpl distribuidoraDAO = new DistribuidoraDAOImpl();
            while (rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM categoria AS C " +
                        "INNER JOIN farmaco_categoria AS FC ON C.id = FC.id_categoria WHERE FC.id_farmaco = ?");
                stmt2.setInt(1, rs.getInt("id"));
                ResultSet categoriasBD = stmt2.executeQuery();
                List<Categoria> categorias = new ArrayList<>();
                while (categoriasBD.next()) {
                    categorias.add(new Categoria(categoriasBD.getInt("id"),
                            categoriasBD.getString("nombre")));
                }
                Farmaco farmaco = new Farmaco(
                        rs.getInt("id"),
                        laboratorioDAO.read(rs.getInt("id_laboratorio")),
                        categorias,
                        null,
                        rs.getInt("stock"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("presentacion"),
                        rs.getString("composicion"),
                        rs.getFloat("precio")
                );
                return farmaco;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Farmaco farmaco) throws SQLException {
        String query = "UPDATE farmaco SET nombre = ?, descripcion = ?, presentacion = ?, composicion = ?, precio = ?, stock = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, farmaco.getNombre());
            stmt.setString(2, farmaco.getDescripcion());
            stmt.setString(3, farmaco.getPresentacion());
            stmt.setString(4, farmaco.getComposicion());
            stmt.setFloat(5, farmaco.getPrecio());
            stmt.setInt(6, farmaco.getStock());
            stmt.setInt(7, farmaco.getId());
            stmt.executeUpdate();
        }
    }


    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<Farmaco> readAll() {
        String query = "SELECT * FROM farmaco";
        List<Farmaco> farmacos = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            LaboratorioDAOImpl laboratorioDAO = new LaboratorioDAOImpl();
            CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
            DistribuidoraDAOImpl distribuidoraDAO = new DistribuidoraDAOImpl();
            while (rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM categoria AS C " +
                        "INNER JOIN farmaco_categoria AS FC ON C.id = FC.id_categoria WHERE FC.id_farmaco = ?");
                stmt2.setInt(1, rs.getInt("id"));
                ResultSet categoriasBD = stmt2.executeQuery();
                List<Categoria> categorias = new ArrayList<>();
                while (categoriasBD.next()) {
                    categorias.add(new Categoria(categoriasBD.getInt("id"),
                            categoriasBD.getString("nombre")));
                }
                Farmaco farmaco = new Farmaco(
                        rs.getInt("id"),
                        laboratorioDAO.read(rs.getInt("id_laboratorio")),
                        categorias,
                        null,
                        rs.getInt("stock"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("presentacion"),
                        rs.getString("composicion"),
                        rs.getFloat("precio")
                );
                farmacos.add(farmaco);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return farmacos;
    }

}
