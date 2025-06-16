package model.notificaciones;

public class EmailStrategy implements INotificacionStrategy {
    private IAdapterEmail adapter = new AdapterJavaEmail();

    public EmailStrategy() {

    }

    @Override
    public void enviarNotificacion(IObserver observer, Notificacion notificacion) {
        Notificacion nuevaNoti = new Notificacion(notificacion.getPartido(), "[Envíada vía Email] "+notificacion.getMensaje());
        adapter.enviarNotificacion(observer, nuevaNoti);
    }
}

