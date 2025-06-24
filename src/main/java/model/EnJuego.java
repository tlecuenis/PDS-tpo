package model;

import model.notificaciones.Notificacion;

public class EnJuego implements IEstadoPartido {

    public void confirmar(Partido contexto) {
    }

    public void cancelar(Partido contexto) {
    }

    public void iniciar(Partido contexto) {
    }

    public void finalizar(Partido contexto, Equipo equipo) {
        System.out.println("¡Partido finalizado!");
        contexto.cambiarEstado(new Finalizado());
        contexto.declararGanador(equipo);
    }

    public void armar(Partido contexto) {
    }


    public void necesitamosJugadores(Partido contexto) {
    }

    public void agregarComentario(Partido contexto, String comentario) {
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
    }

    @Override
    public String getNombreEstado() {
        return "EnJuego";
    }

    public void declararGanador(Partido contexto, Equipo ganador) {
    }
	public boolean añadirAlEquipo(Partido contexto, Usuario jugador, String nombreEquipo) {
		return false;
	}

}