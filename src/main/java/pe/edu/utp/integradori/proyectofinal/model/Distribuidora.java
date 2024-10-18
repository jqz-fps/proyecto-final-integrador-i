package pe.edu.utp.integradori.proyectofinal.model;

import static com.google.common.base.Preconditions.*;

public class Distribuidora {
    private int id;
    private String nombre;
    private String alias;
    private String email;
    private String telefono;

    public Distribuidora(int id, String nombre, String alias, String email, String telefono) {
        checkArgument(id > 0, "El id debe ser mayor a cero");
        checkNotNull(nombre, "El nombre no puede ser nulo");
        checkNotNull(alias, "El alias no puede ser nulo");
        checkNotNull(email, "El email no puede ser nulo");
        checkNotNull(telefono, "El telefono no puede ser nulo");
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlias() {
        return alias;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }
}
