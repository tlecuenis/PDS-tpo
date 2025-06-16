package model;

import model.notificaciones.Notificacion;

public class EnJuego implements IEstadoPartido {
	
    public void confirmar(Partido contexto) {
        System.out.println("No puede confirmarse. El partido ya está en juego.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("No puede cancelarse. El partido ya está en juego.");
    }

    public void iniciar(Partido contexto) {
        System.out.println("No puede iniciarse. El partido ya está en juego.");
    }
    
    public void armar(Partido contexto) {
        System.out.println("No se puede armar. El partido ya está en juego.");
    }

    public void finalizar(Partido contexto) {
        Notificacion notificacion = new Notificacion(contexto, "El partido ha finalizado");
        contexto.notificar(notificacion);
        System.out.println("¡Partido finalizado!");
        contexto.cambiarEstado(new Finalizado());
    }

    public void necesitamosJugadores(Partido contexto) {
    	System.out.println("No puede cambiar a necesitamos jugadores. El partido ya está en juego");
    }

    public void agregarComentario(Partido contexto, String comentario) {
        System.out.println("No se puede agregar comentarios, el partido no ha finalizado.");
    }

    public void agregarEstadistica(Partido contexto, String estadistica) {
        System.out.println("No se pueden agregar estadísticas, el partido no ha finalizado.");
    }

    @Override
    public String getNombreEstado() {
        return "EnJuego";
    }
}
