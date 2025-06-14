package model.notificaciones;

import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public abstract class ObserverPartido {
    public List<IObserver> observers;
    private NotificacionDispatcher dispatcher;

    public ObserverPartido(NotificacionDispatcher dispatcher) {
        this.observers = new ArrayList<>();
        this.dispatcher = dispatcher;
    }

    public List<IObserver> getDestinatarios() {
        return observers;
    }

    public void agregarDestinatario(IObserver newObserver) {
        observers.add(newObserver);
    }

    public void eliminarDestinatario(IObserver observer) {
        observers.remove(observer);
    }

    public void notificar(Notificacion notificacion) {
        for(IObserver observer : observers) {
            dispatcher.enviar(notificacion, observer);
        }
    }
}
