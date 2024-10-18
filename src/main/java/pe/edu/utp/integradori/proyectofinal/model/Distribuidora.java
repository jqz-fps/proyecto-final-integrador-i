package pe.edu.utp.integradori.proyectofinal.model;

public class Distribuidora {
    private int id;
    private String nombre;
    private String alias;
    private String email;
    private String telefono;

    public Distribuidora(int id, String nombre, String alias, String email, String telefono) {
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
