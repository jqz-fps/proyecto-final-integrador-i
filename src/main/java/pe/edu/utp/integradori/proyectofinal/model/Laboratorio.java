package pe.edu.utp.integradori.proyectofinal.model;

import static com.google.common.base.Preconditions.*;

public class Laboratorio {
    private int id;
    private String nombre;

    public Laboratorio(int id, String nombre) {
        checkArgument(id > 0, "El id debe ser mayor a cero");
        checkNotNull(nombre, "El nombre no puede ser nulo");
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
