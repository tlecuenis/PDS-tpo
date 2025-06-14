package model.notificaciones;

public interface IAdapterEmail {
    void enviarNotificacion(IObserver observer, Notificacion notificacion);
}

