package pe.edu.utp.integradori.proyectofinal.interfaces;

import pe.edu.utp.integradori.proyectofinal.model.Trabajador;

public interface TrabajadorDAO extends DataAccessObject<Trabajador, Integer> {
    public Trabajador readLogin(String usuario, String contrasena); // "usuario" es el correo del trabajador
}
