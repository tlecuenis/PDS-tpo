package model;

import java.util.ArrayList;
import java.util.List;

import repository.UserRepository;

public class NivelStrategy implements IEmparejamientoStrategy {
	@Override
	public void emparejar(List<Equipo> equipos, Partido partido) {
		int nivelMinimo = partido.getNivelJugadorMinimo();
		int nivelMaximo = partido.getNivelJugadorMaximo();

		List<Usuario> jugadoresBBDD = new ArrayList<>();
		try {
			// despues de probar que funcione la base de datos, en reemplazar el findAll por findByField y buscar por deporte
			// jugadoresBBDD = new UserRepository().findByField("deporte", partido.getDeporte().getNombre());
			jugadoresBBDD =  new UserRepository().findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al obtener los jugadores de la base de datos: " + e.getMessage());
		}
		if (jugadoresBBDD.isEmpty()) {
			System.out.println("No hay jugadores disponibles en la base de datos.");
			return;
		}
		//probar que se imprima correctamente
		for (Usuario jugador : jugadoresBBDD) {
			System.out.println(jugador.getNombre());
			for (Deporte deporte : jugador.getDeportes()) {
				System.out.println("Deporte: " + deporte.getNombre() + " - Nivel: " + deporte.getNivelJuego());
			}
		}
		for (Usuario jugador : jugadoresBBDD) {
			for (Deporte deporte : jugador.getDeportes()) {
				// if(jugador.getDeportes().equals(partido.getDeporte())) {
				if(deporte == partido.getDeporte()) {
					if(deporte.getNivelJuego().getValor() >= nivelMinimo && deporte.getNivelJuego().getValor() <= nivelMaximo){
						// notificar al jugador
					}
				}
			}
		}
	}
}

