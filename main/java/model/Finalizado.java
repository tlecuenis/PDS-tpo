package model;

public class Finalizado implements IEstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("No se puede confirmar. El partido ya terminó.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("No se puede cancelar. El partido ya terminó");
    }

    public void iniciar(Partido contexto) {
        System.out.println("No se puede iniciar. El partido ya terminó.");
    }

    public void finalizar(Partido contexto) {
        System.out.println("No se puede finalizar. El partido ya está finalizado");
    }
    
    public void necesitamosJugadores(Partido contexto) {
    	System.out.println("No puede cambiar a necesitamos jugadores. El partido ya terminó");
    }

    public void agregarComentario(Partido contexto, String comentario) {
        System.out.println("Comentario post partido agregado.");
        contexto.agregarComentario(comentario);
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
        System.out.println("Estadística post partido registrada.");
        contexto.agregarEstadistica(estadistica);
    }
}
