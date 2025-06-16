package model;

public class Cancelado implements IEstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("No se puede confirmar. El partido está cancelado.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("No se puede cancelar. El partido está cancelado.");
    }

    public void iniciar(Partido contexto) {
        System.out.println("No se puede iniciar. El partido está cancelado.");
    }
    public void armar(Partido contexto) {
        System.out.println("No se puede armar. El partido está cancelado.");
    }

    public void finalizar(Partido contexto) {
        System.out.println("No se puede finalizar. El partido está cancelado.");
    }

    public void necesitamosJugadores(Partido contexto) {
    	System.out.println("No puede cambiar a necesitamos jugadores. El partido está cancelado");
    }
    
    public void agregarComentario(Partido contexto, String comentario) {
        System.out.println("No se puede agregar comentarios, el partido está cancelado.");
    }

    public void agregarEstadistica(Partido contexto, String comentario) {
        System.out.println("No se puede agregar comentarios, el partido está cancelado.");
    }
}

