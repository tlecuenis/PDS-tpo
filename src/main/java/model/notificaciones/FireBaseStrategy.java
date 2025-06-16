package model.notificaciones;

public class FireBaseStrategy implements INotificacionStrategy {
    @Override
    public void enviarNotificacion(IObserver observer, Notificacion notificacion) {
        Notificacion nuevaNoti = new Notificacion(notificacion.getPartido(), "[Envíada vía FireBase] "+notificacion.getMensaje());
        observer.serNotificado(nuevaNoti);
        // Acá iría la lógica real usando Firebase SDK/API
    }
}

