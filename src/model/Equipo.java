package model;

import java.util.List;
import model.Partido;

public class Equipo {
    private String nombre;
    private String ubicacion;
    private int nivel;
    private String tipoNivel;

    public Equipo(String nombre, String ubicacion, int nivel, String tipoNivel) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.nivel = nivel;
        this.tipoNivel = tipoNivel;
    }

    public String getUbicacion() { return ubicacion; }
    public int getNivel() { return nivel; }
    public String getTipoNivel() { return tipoNivel; }

    // Simulación de verificación de historial compatible
    public boolean tieneHistorialCompatible() {
        return true; // Esto lo podés reemplazar por lógica real
    }
}
