package model.notificaciones;

import model.Usuario;

public class AdapterJavaEmail implements IAdapterEmail {
    @Override
    public void enviarNotificacion(IObserver observer, Notificacion notificacion) {
        //casteo para obtener el mail del suario
        Usuario userToNotify = (Usuario) observer;
        String email = userToNotify.getEmail();
        //hacer algo usando la API de JavaMail (?
        Notificacion nuevaNoti = new Notificacion(notificacion.getPartido(), "["+email+"] "+notificacion.getMensaje());
        observer.serNotificado(nuevaNoti);
    }
}

