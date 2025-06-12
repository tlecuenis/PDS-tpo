package model;

import java.util.List;
import java.util.ArrayList;

public class EstadoActualizado implements IEstrategiaNotificacion {
    @Override
    public List<Usuario> notificarDestinatarios() {
        // Lógica específica para destinatarios cuando el estado cambia
        System.out.println("Usando estrategia de notificación por Estado Actualizado");
        return new ArrayList<>(); // Se reemplaza con lógica real
    }
}

