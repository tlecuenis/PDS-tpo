package model;

import java.util.List;

public class Emparejador {
    private IEmparejamientoStrategy estrategia;

    public void setEstrategia(IEmparejamientoStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public List<Equipo> emparejarEquipos(List<Equipo> equipos) {
        if (estrategia == null)
            throw new IllegalStateException("Estrategia no definida");
        return estrategia.emparejar(equipos);
    }
}

