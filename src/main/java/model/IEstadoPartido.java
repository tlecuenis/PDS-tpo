package model;


public interface IEstadoPartido {
	void confirmar(Partido contexto);
	void cancelar(Partido contexto);
	void armar(Partido contexto);
	void iniciar(Partido contexto);
	void finalizar(Partido contexto, Equipo equipo);
	void necesitamosJugadores(Partido contexto);
	void agregarComentario(Partido contexto, String comentario);
	void agregarEstadistica(Partido contexto, String estadistica);
	String getNombreEstado();
	void declararGanador(Partido contexto, Equipo ganador);
	boolean añadirAlEquipo(Partido contexto, Usuario jugador, String nombreEquipo);
}