package model;

import java.util.List;
import java.util.ArrayList;

public class Invitacion implements IEstrategiaNotificacion {
    @Override
    public List<Usuario> notificarDestinatarios() {
        // Lógica específica para destinatarios de invitación
        System.out.println("Usando estrategia de notificación por Invitación");
        return new ArrayList<>(); // Se reemplaza con lógica real
    }
}

