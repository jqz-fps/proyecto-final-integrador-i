package pe.edu.utp.integradori.proyectofinal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;

public class Trabajador {
    private int id;
    private String dni;
    private String nombres;
    private String ap_paterno;
    private String ap_materno;
    private char genero;
    private LocalDate fecha_nacimiento;
    private LocalDate fecha_registro;
    private List<Rol> roles;
    private List<Venta> ventas;

    public Trabajador(int id, String dni, String nombres, String ap_paterno,
                      String ap_materno, char genero, LocalDate fecha_nacimiento,
                      LocalDate fecha_registro, List<Rol> roles, List<Venta> ventas) {
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

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public boolean hasRole(Rol rol) {
        return roles.contains(rol);
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public String getApellidos() {
        return ap_paterno + " " + ap_materno;
    }

}
