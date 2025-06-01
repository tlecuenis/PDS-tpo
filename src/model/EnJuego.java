package model;

public class EnJuego implements EstadoPartido {
    public void confirmar(Partido contexto) {
        System.out.println("Ya está en juego, no se puede confirmar nuevamente.");
    }

    public void cancelar(Partido contexto) {
        System.out.println("Cancelando partido en juego...");
        contexto.setEstado(new Cancelado());
    }

    public void iniciar(Partido contexto) {
        System.out.println("Ya está en juego.");
    }

    public void finalizar(Partido contexto) {
        System.out.println("Finalizando partido...");
        contexto.setEstado(new Finalizado());
    }

    public void agregarComentario(Partido contexto) {
        System.out.println("Comentario agregado en estado: EnJuego.");
    }

    public void agregarEstadistica(Partido contexto) {
        System.out.println("Estadística registrada.");
    }
}
