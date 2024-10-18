package pe.edu.utp.integradori.proyectofinal.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.*;

public class Trabajador {
    private int id;
    private String dni;
    private String nombres;
    private String ap_paterno;
    private String ap_materno;
    private char genero;
    private LocalDateTime fecha_nacimiento;
    private LocalDateTime fecha_registro;
    private ArrayList<Rol> roles;
    private ArrayList<Venta> ventas;

    public Trabajador(int id, String dni, String nombres, String ap_paterno,
                      String ap_materno, char genero, LocalDateTime fecha_nacimiento,
                      LocalDateTime fecha_registro, ArrayList<Rol> roles, ArrayList<Venta> ventas) {
        checkNotNull(dni, "El dni no puede ser nulo");
        checkNotNull(nombres, "El nombre no puede ser nulo");
        checkNotNull(ap_paterno, "El apellido paterno no puede ser nulo");
        checkNotNull(ap_materno, "El apellido materno no puede ser nulo");
        checkNotNull(roles, "El arreglo de roles no puede ser nulo");
        checkNotNull(ventas, "El arreglo de ventas no puede ser nulo");
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.genero = genero;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_registro = fecha_registro;
        this.roles = roles;
        this.ventas = ventas;
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombres() {
        return nombres;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public char getGenero() {
        return genero;
    }

    public LocalDateTime getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public ArrayList<Rol> getRoles() {
        return roles;
    }

    public boolean hasRole(Rol rol) {
        return roles.contains(rol);
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

}
