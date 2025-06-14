package model.notificaciones;

import model.Usuario;
import repository.UserDAO;
import repository.UserRepository;

public class NotificacionDispatcher {
    private UserDAO userRpository;
    private INotificacionStrategy estrategia;

    public NotificacionDispatcher() {
        this.userRpository = new UserRepository();
    }

    public void cambiarEstrategia(INotificacionStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void enviar(Notificacion notificacion, IObserver observer) {
        Usuario userToNotify = (Usuario) observer; //casteo porque se que todas las instancias de observer son Usuario, ya que es la unica implementacion de la ifaz IObserver
        INotificacionStrategy userPreference = userToNotify.getPreferenciaNotificacion().getStrategy();
        this.cambiarEstrategia(userPreference);
        //guardar la notificacion en la db
        String userID = userToNotify.getIdUsuario();
        String mensaje = "Partido "+notificacion.getPartido().getIdPartido()+" --> "+notificacion.getMensaje();
        userRpository.notificarUsuario(userID, mensaje);
        estrategia.enviarNotificacion(observer, notificacion);
    }
}


