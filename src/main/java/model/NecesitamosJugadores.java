package model;

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
        System.out.println("No puede iniciarse. El partido no está confirmado.");
    }

    public void finalizar(Partido contexto) {
        System.out.println("No se puede finalizar. El partido no está en juego.");
    }

    public void necesitamosJugadores(Partido contexto) {
    	System.out.println("No puede cambiar a necesitamos jugadores. El partido ya está en ese estado");
    }
    
    public void agregarComentario(Partido contexto, String comentario) {
        System.out.println("No se puede agregar comentarios, el partido no ha finalizado.");
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
        System.out.println("No se pueden agregar estadísticas, el partido no ha finalizado.");
    }
}

