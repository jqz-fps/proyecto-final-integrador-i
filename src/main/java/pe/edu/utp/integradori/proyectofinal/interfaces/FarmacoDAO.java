package pe.edu.utp.integradori.proyectofinal.interfaces;

import pe.edu.utp.integradori.proyectofinal.model.Farmaco;

import java.util.List;

public interface FarmacoDAO extends DataAccessObject<Farmaco, Integer> {
    public List<Farmaco> readAll();
}
