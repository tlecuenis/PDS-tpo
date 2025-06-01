package model;

public class Armado implements EstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("Pasando a estado CONFIRMADO");
        contexto.setEstado(new Confirmado());
    }

    public void cancelar(Partido contexto) {
        System.out.println("Partido cancelado");
        contexto.setEstado(new Cancelado());
    }

    public void iniciar(Partido contexto) {
        System.out.println("Iniciando partido...");
        contexto.setEstado(new EnJuego());
    }

    public void finalizar(Partido contexto) {
        System.out.println("No se puede finalizar, aún no empezó.");
    }

    public void agregarComentario(Partido contexto) {
        System.out.println("Comentario agregado en estado: Armado.");
    }

    public void agregarEstadistica(Partido contexto) {
        System.out.println("No se pueden agregar estadísticas todavía.");
    }
}

