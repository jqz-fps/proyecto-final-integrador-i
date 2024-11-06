import org.junit.jupiter.api.Test;
import pe.edu.utp.integradori.proyectofinal.model.Rol;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrabajadorTest {

    @Test
    public void testA() {
        Trabajador trabajador = new Trabajador(2, "12345678", "Maria", "Gomez", "Diaz", 'F',
                LocalDate.of(1985, 3, 15), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());

        assertEquals("1985-03-15", trabajador.getFecha_nacimientoFormatoInput());
    }

    @Test
    public void testB() {
        Rol rolSupervisor = Rol.Supervisor;
        List<Rol> roles = new ArrayList<>();
        roles.add(rolSupervisor);

        Trabajador trabajador = new Trabajador(3, "87654321", "Carlos", "Martinez", "Suarez", 'M',
                LocalDate.of(1992, 11, 30), LocalDateTime.now(), roles, new ArrayList<>());

        assertTrue(trabajador.hasRole(rolSupervisor));
    }


}
