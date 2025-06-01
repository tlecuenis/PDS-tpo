package model;

public class Cancelado implements EstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("No se puede confirmar, partido cancelado.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("Ya está cancelado.");
    }

    public void iniciar(Partido contexto) {
        System.out.println("No se puede iniciar, partido cancelado.");
    }

    public void finalizar(Partido contexto) {
        System.out.println("No se puede finalizar, partido cancelado.");
    }

    public void agregarComentario(Partido contexto) {
        System.out.println("Comentario en partido cancelado agregado.");
    }

    public void agregarEstadistica(Partido contexto) {
        System.out.println("No se pueden agregar estadísticas a un partido cancelado.");
    }
}

