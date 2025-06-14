package model.notificaciones;

public class EmailStrategy implements INotificacionStrategy {
    private IAdapterEmail adapter = new AdapterJavaEmail();

    public EmailStrategy() {

    }

    @Override
    public void enviarNotificacion(IObserver observer, Notificacion notificacion) {
        adapter.enviarNotificacion(observer, notificacion);
    }
}

