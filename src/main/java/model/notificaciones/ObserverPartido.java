package model.notificaciones;

import model.Usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ObserverPartido {
    public Set<IObserver> observers;
    private NotificacionDispatcher dispatcher;

    public ObserverPartido(NotificacionDispatcher dispatcher) {
        this.observers = new HashSet<>();
        this.dispatcher = dispatcher;
    }

    public List<IObserver> getDestinatarios() {
        return new ArrayList<>(observers);
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
