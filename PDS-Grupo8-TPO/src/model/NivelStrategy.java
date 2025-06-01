package model;

import java.util.List;
import java.util.stream.Collectors;

public class NivelStrategy implements IEmparejamientoStrategy {
    private int minimo;
    private int maximo;
    private String nivelJuego;

    public NivelStrategy(int minimo, int maximo, String nivelJuego) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nivelJuego = nivelJuego;
    }

    @Override
    public List<Equipo> emparejar(List<Equipo> equipos) {
        return equipos.stream()
                .filter(e -> e.getNivel() >= minimo && e.getNivel() <= maximo
                        && e.getTipoNivel().equalsIgnoreCase(nivelJuego))
                .collect(Collectors.toList());
    }
}

