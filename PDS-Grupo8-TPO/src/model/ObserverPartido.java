package model;

import java.util.ArrayList;
import java.util.List;

public class ObserverPartido {
    private List<Usuario> destinatarios;
    private NotificacionDispatcher dispatcher;
    private IEstrategiaNotificacion estrategiaNotificacion;

    public ObserverPartido(NotificacionDispatcher dispatcher, IEstrategiaNotificacion estrategia) {
        this.destinatarios = new ArrayList<>();
        this.dispatcher = dispatcher;
        this.estrategiaNotificacion = estrategia;
    }

    public void agregarDestinatario(Usuario usuario) {
        destinatarios.add(usuario);
    }

    public void eliminarDestinatario(Usuario usuario) {
        destinatarios.remove(usuario);
    }

    public void cambiarEstrategiaNotificacion(IEstrategiaNotificacion estrategia) {
        this.estrategiaNotificacion = estrategia;
    }

    public void notificar(Notificacion notificacion, List<Usuario> destinatariosExclusivos) {
        List<Usuario> usuariosANotificar = (destinatariosExclusivos != null)
            ? destinatariosExclusivos
            : estrategiaNotificacion.notificarDestinatarios();

        for (Usuario u : usuariosANotificar) {
            dispatcher.enviar(notificacion, u);
        }
    }
}
