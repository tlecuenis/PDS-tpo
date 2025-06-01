package model;

import java.util.List;
import java.util.stream.Collectors;

public class CercaniaStrategy implements IEmparejamientoStrategy {
    private String ubicacion;

    public CercaniaStrategy(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public List<Equipo> emparejar(List<Equipo> equipos) {
        return equipos.stream()
                .filter(e -> e.getUbicacion().equalsIgnoreCase(ubicacion))
                .collect(Collectors.toList());
    }
}

