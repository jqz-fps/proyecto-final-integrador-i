package pe.edu.utp.integradori.proyectofinal.model;

public class Distribuidora {
    private int id_distribuidora;
    private String nombre;
    private String alias;
    private String email;
    private String telefono;

    public Distribuidora(int id_distribuidora, String nombre, String alias, String email, String telefono) {
        this.id_distribuidora = id_distribuidora;
        this.nombre = nombre;
        this.alias = alias;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id_distribuidora;
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
