package model;

import model.notificaciones.Notificacion;

public class Confirmado implements IEstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("No puede confirmarse. El partido ya está en ese estado.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("No puede cancelarse. El partido ya está confirmado");
    }

    public void iniciar(Partido contexto) {
        Notificacion notificacion = new Notificacion(contexto, "El partido está en juego");
        contexto.notificar(notificacion);
        System.out.println("¡Partido en juego!");
        contexto.cambiarEstado(new EnJuego());
    }
    
    public void armar(Partido contexto) {
        System.out.println("No se puede armar. El partido ya está confirmado.");
    }
    
    public void finalizar(Partido contexto) {
        System.out.println("No puede finalizarse. El partido no ha iniciado.");
    }
    
    public void necesitamosJugadores(Partido contexto) {
    	System.out.println("No puede cambiar a necesitamos jugadores. El partido ya está confirmado");
    }

    public void agregarComentario(Partido contexto, String comentario) {
        System.out.println("No se puede agregar comentarios, el partido no ha finalizado.");
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
        System.out.println("No se pueden agregar estadísticas, el partido no ha finalizado.");
    }
}
