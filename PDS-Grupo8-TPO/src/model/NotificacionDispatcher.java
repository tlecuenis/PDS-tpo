package model;

public class NotificacionDispatcher {
    private EnvioStrategy estrategia;

    public NotificacionDispatcher(EnvioStrategy estrategiaInicial) {
        this.estrategia = estrategiaInicial;
    }

    public void cambiarEstrategia(EnvioStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void enviar(Notificacion notificacion, Usuario usuario) {
        System.out.println("Preparando notificación para: " + usuario.getNombre());
        estrategia.enviarNotificacion(notificacion);
    }
}


