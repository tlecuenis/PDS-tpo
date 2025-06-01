package model;

public class Confirmado implements EstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("Ya está confirmado.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("Cancelando partido...");
        contexto.setEstado(new Cancelado());
    }

    public void iniciar(Partido contexto) {
        System.out.println("Partido en juego...");
        contexto.setEstado(new EnJuego());
    }

    public void finalizar(Partido contexto) {
        System.out.println("Finalizando partido directamente...");
        contexto.setEstado(new Finalizado());
    }

    public void agregarComentario(Partido contexto) {
        System.out.println("Comentario agregado en estado: Confirmado.");
    }

    public void agregarEstadistica(Partido contexto) {
        System.out.println("No se pueden agregar estadísticas todavía.");
    }
}

