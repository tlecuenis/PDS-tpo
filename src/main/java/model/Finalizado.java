package model;

public class Finalizado implements IEstadoPartido {
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
        System.out.println("Comentario post partido agregado.");
        contexto.agregarComentario(comentario);
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
        System.out.println("Estadística post partido registrada.");
        contexto.agregarEstadistica(estadistica);
    }
    public void declararGanador(Partido contexto, Equipo ganador) {
        System.out.printf("¡Felicidades, el ganador es: ", ganador.getNombre());
        contexto.setGanador(ganador);
    }

    @Override
    public String getNombreEstado() {
        return "Finalizado";
    }
	public boolean añadirAlEquipo(Partido contexto, Usuario jugador, String nombreEquipo) {
		return false;
	}
}