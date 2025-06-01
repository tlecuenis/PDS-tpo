package model;

public class EmailStrategy implements EnvioStrategy {
    private IAdapterEmail adapter;

    public EmailStrategy(IAdapterEmail adapter) {
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        System.out.println("Usando estrategia de envío por email...");
        adapter.enviarNotificacion(notificacion);
    }
}

