package model;


public interface IEstadoPartido {
	   void confirmar(Partido contexto);
	   void cancelar(Partido contexto);
	   void iniciar(Partido contexto);
	   void finalizar(Partido contexto);
	   void necesitamosJugadores(Partido contexto);
	   void agregarComentario(Partido contexto, String comentario);
	   void agregarEstadistica(Partido contexto, String estadistica);
}


