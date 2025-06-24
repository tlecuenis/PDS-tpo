package model;

import model.notificaciones.Notificacion;

public class Armado implements IEstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("¡Partido confirmado!");
        contexto.cambiarEstado(new Confirmado());
    }

    public void cancelar(Partido contexto) {
        System.out.println("¡Partido cancelado!");
        contexto.cambiarEstado(new Cancelado());
    }

    public void armar(Partido contexto) {
    }

    public void iniciar(Partido contexto) {
    }

    public void finalizar(Partido contexto, Equipo equipo) {
    }

    public void necesitamosJugadores(Partido contexto) {
        System.out.println("¡Partido en necesitamos jugadores!");
        contexto.cambiarEstado(new NecesitamosJugadores());
    }

    public void agregarComentario(Partido contexto, String comentario) {
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
    }

    @Override
    public String getNombreEstado() {
        return "Armado";
    }

    public void declararGanador(Partido contexto, Equipo ganador) {
    }
	public boolean añadirAlEquipo(Partido contexto, Usuario jugador, String nombreEquipo) {
		return false;
	}
}