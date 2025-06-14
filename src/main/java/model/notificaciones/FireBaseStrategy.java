package model.notificaciones;

public class FireBaseStrategy implements INotificacionStrategy {
    @Override
    public void enviarNotificacion(IObserver observer, Notificacion notificacion) {
        observer.serNotificado(notificacion);
        // Acá iría la lógica real usando Firebase SDK/API
    }
}

