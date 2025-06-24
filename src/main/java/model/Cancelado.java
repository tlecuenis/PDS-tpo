package model;

public class Cancelado implements IEstadoPartido {
    public void confirmar(Partido contexto) {
    }

    public void cancelar(Partido contexto) {
    }

    public void iniciar(Partido contexto) {
    }
    public void armar(Partido contexto) {
    }

    public void finalizar(Partido contexto, Equipo equipo) {
    }

    public void necesitamosJugadores(Partido contexto) {
    }

    public void agregarComentario(Partido contexto, String comentario) {
    }

    public void agregarEstadistica(Partido contexto, String comentario) {
    }

    @Override
    public String getNombreEstado() {
        return "Cancelado";
    }
    public void declararGanador(Partido contexto, Equipo ganador) {
    }
	public boolean añadirAlEquipo(Partido contexto, Usuario jugador, String nombreEquipo) {
		return false;
	}
}