package model;

import java.util.List;
import java.util.stream.Collectors;

public class HistorialStrategy implements IEmparejamientoStrategy {
    @Override
    public List<Equipo> emparejar(List<Equipo> equipos) {
        return equipos.stream()
                .filter(Equipo::tieneHistorialCompatible)
                .collect(Collectors.toList());
    }
}

