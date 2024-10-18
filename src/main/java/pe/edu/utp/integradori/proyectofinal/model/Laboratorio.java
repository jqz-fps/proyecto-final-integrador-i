package pe.edu.utp.integradori.proyectofinal.model;

public class Laboratorio {
    private int id;
    private String nombre;

    public Laboratorio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
