package model;

import model.notificaciones.Notificacion;

public class NecesitamosJugadores implements IEstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("¡Partido confirmado!");
        contexto.cambiarEstado(new Confirmado());
    }

    public void cancelar(Partido contexto) {
        System.out.println("Partido cancelado");
        contexto.cambiarEstado(new Cancelado());
    }

    public void iniciar(Partido contexto) {
    }

    public void armar(Partido contexto) {
        System.out.println("¡Partido armado!.");
        contexto.cambiarEstado(new Armado());
    }

    public void finalizar(Partido contexto, Equipo equipo) {
    }

    public void necesitamosJugadores(Partido contexto) {
    }

    public void agregarComentario(Partido contexto, String comentario) {
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
    }

    @Override
    public String getNombreEstado() {
        return "NecesitamosJugadores";
    }
    public void declararGanador(Partido contexto, Equipo ganador) {
    }
	public boolean añadirAlEquipo(Partido contexto, Usuario jugador, String nombreEquipo) {
	    for (Equipo equipo : contexto.getEquipos()) {
	        if (equipo.getNombre().equals(nombreEquipo)) {
	            if (contexto.validarEntrada(jugador, equipo)) {
	                equipo.agregarJugador(jugador);
					contexto.agregarDestinatario(jugador);
	                contexto.validarArmado();
	                return true;
	            } else {
	                return false;
	            }
	        }
	    }
	    return false;
	}
}