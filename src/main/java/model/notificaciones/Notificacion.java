package model.notificaciones;

import java.util.List;

import model.Partido;
import model.Usuario;

public class Notificacion {
    private String mensaje;
    private Partido partido;

    public Notificacion(Partido partido, String mensaje) {
        this.mensaje = mensaje;
        this.partido = partido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Partido getPartido() {
        return partido;
    }
}

