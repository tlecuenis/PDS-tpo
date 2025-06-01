package model;

public class NecesitamosJugadores implements EstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("Pasando a estado ARMADO");
        contexto.setEstado(new Armado());
    }

    public void cancelar(Partido contexto) {
        System.out.println("Partido cancelado");
        contexto.setEstado(new Cancelado());
    }

    public void iniciar(Partido contexto) {
        System.out.println("No se puede iniciar, faltan jugadores.");
    }

    public void finalizar(Partido contexto) {
        System.out.println("No se puede finalizar, ni empezó.");
    }

    public void agregarComentario(Partido contexto) {
        System.out.println("Comentario agregado en estado: NecesitamosJugadores.");
    }

    public void agregarEstadistica(Partido contexto) {
        System.out.println("No se pueden agregar estadísticas todavía.");
    }
}

