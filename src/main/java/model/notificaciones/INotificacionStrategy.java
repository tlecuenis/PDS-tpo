package model.notificaciones;

public interface INotificacionStrategy {
    void enviarNotificacion(IObserver observer, Notificacion notificacion);
}

