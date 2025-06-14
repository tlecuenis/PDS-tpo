package model;

import java.util.ArrayList;
import java.util.List;

import model.notificaciones.Notificacion;
import repository.UserRepository;

public class NivelStrategy implements IEmparejamientoStrategy {
	@Override
	public void emparejar(List<Equipo> equipos, Partido partido) {
		int nivelMinimo = partido.getNivelJugadorMinimo();
		int nivelMaximo = partido.getNivelJugadorMaximo();

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
		for (Usuario jugador : jugadoresBBDD) {
			for (Deporte deporte : jugador.getDeportes()) {
				if(deporte.getNombre().toLowerCase().contains(partido.getDeporte().toLowerCase())) {
//				if(deporte.getNombre().toLowerCase() == partido.getDeporte().toLowerCase()) {
					if(deporte.getNivelJuego().getValor() >= nivelMinimo && deporte.getNivelJuego().getValor() <= nivelMaximo){
						// si ya está en el partido no notificar
						boolean estaEnPartido = false;
						for (Equipo equipo : partido.getEquipos()) {
							for (Usuario integrante : equipo.getJugadores()) {
//								if (integrante.equals(jugador)) {
								if (integrante == jugador) {
									estaEnPartido = true;
								}
							}
						}
						if(estaEnPartido == false) {
							jugador.serNotificado(notificacion);
						}
					}
				}
			}	
		}
	}
}