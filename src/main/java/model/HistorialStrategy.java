package model;

import java.util.ArrayList;
import java.util.List;

import model.notificaciones.Notificacion;
import model.notificaciones.NotificacionDispatcher;
import repository.UserRepository;

public class HistorialStrategy implements IEmparejamientoStrategy {
	@Override
	public void emparejar(List<Equipo> equipos, Partido partido) {
		// Historial del creador, se deja en int para truncar decimales
		List<Deporte> deportesCreador = partido.getCreador().getDeportes();
		int historialRequerido;
		int cantPartidos = 0;
		int score = 0;
		for (Deporte deporte : deportesCreador) {
			if (deporte.getNombre().toLowerCase().contains(partido.getDeporte().toLowerCase())) {
//			if (deporte.getNombre().toLowerCase() == partido.getDeporte().toLowerCase()) {
				score = deporte.getScore();
				cantPartidos = deporte.getCantPartidos();
			}
		}
		historialRequerido = score / cantPartidos;
		
		List<Usuario> jugadoresBBDD = new ArrayList<>();
		try {
			jugadoresBBDD =  new UserRepository().findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al obtener los jugadores de la base de datos: " + e.getMessage());
		}
		if (jugadoresBBDD.isEmpty()) {
			System.out.println("No hay jugadores disponibles en la base de datos.");
			return;
		}
		Notificacion notificacion = new Notificacion(partido, "Te estamos buscando, unite al partido!");
		NotificacionDispatcher notificacionDispatcher = new NotificacionDispatcher();
		// Historial del jugador
		int historialJugador;
		int cantPartidosJugador = 0;
		int scoreJugador = 0;
		for (Usuario jugador : jugadoresBBDD) {
			for (Deporte deporte : jugador.getDeportes()) {
				if (deporte.getNombre().toLowerCase().contains(partido.getDeporte().toLowerCase())) {
//				if (deporte.getNombre().toLowerCase() == partido.getDeporte().toLowerCase()) {
					scoreJugador = deporte.getScore();
					cantPartidosJugador = deporte.getCantPartidos();
				}
			}
			historialJugador = scoreJugador / cantPartidosJugador;
			if (historialJugador == historialRequerido) {
				// si ya está en el partido no notificar
				boolean estaEnPartido = false;
				for (Equipo equipo : partido.getEquipos()) {
					for (Usuario integrante : equipo.getJugadores()) {
//						if (integrante.equals(jugador)) {
						if (integrante == jugador) {
							estaEnPartido = true;
						}
					}
				}
				if(estaEnPartido == false) {
					notificacionDispatcher.enviar(notificacion, jugador);
				}
			}
		}
	}
}

