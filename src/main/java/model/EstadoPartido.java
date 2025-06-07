package model;


public interface EstadoPartido {
	   void confirmar(Partido contexto);
	   void cancelar(Partido contexto);
	   void iniciar(Partido contexto);
	   void finalizar(Partido contexto);
	   void agregarComentario(Partido contexto);
	   void agregarEstadistica(Partido contexto);
}


