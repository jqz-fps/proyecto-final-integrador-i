package pe.edu.utp.integradori.proyectofinal.dao;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Conexion {

    Logger logger = LoggerFactory.getLogger(Conexion.class);

    protected Connection getConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("No se pudo acceder a la base de datos", e);
            throw new SQLException("No se pudo cargar el driver JDBC", e);
        }

        return DriverManager.getConnection(url, user, password);
    }
}
