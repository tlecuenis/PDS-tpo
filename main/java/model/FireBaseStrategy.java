package model;

public class FireBaseStrategy implements EnvioStrategy {
    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        System.out.println("Enviando notificación por Firebase: " + notificacion.getMensaje());
        // Acá iría la lógica real usando Firebase SDK/API
    }
}

