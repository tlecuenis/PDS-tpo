package model.notificaciones;

public enum PreferenciaNotificacion{
    EMAIL_PREFERENCE(new EmailStrategy()),
    FIREBASE_PREFERENCE(new FireBaseStrategy());

    private IAdapterEmail adapterEmail = new AdapterJavaEmail();
    private final INotificacionStrategy strategy;

    PreferenciaNotificacion(INotificacionStrategy strategy) {
        this.strategy = strategy;
    }

    public INotificacionStrategy getStrategy() {
        return strategy;
    }
}
