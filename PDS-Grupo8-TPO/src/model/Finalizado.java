package model;

public class Finalizado implements EstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("El partido ya terminó.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("No se puede cancelar, ya finalizó.");
    }

    public void iniciar(Partido contexto) {
        System.out.println("El partido ya terminó.");
    }

    public void finalizar(Partido contexto) {
        System.out.println("Ya está finalizado.");
    }

    public void agregarComentario(Partido contexto) {
        System.out.println("Comentario post partido agregado.");
    }

    public void agregarEstadistica(Partido contexto) {
        System.out.println("Estadística post partido registrada.");
    }
}
