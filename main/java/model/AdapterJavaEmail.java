package model;

public class AdapterJavaEmail implements IAdapterEmail {
    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        System.out.println("Enviando email vía JavaMail: " + notificacion.getMensaje());
    }
}

