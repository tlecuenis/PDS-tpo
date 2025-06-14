package model;

import java.util.ArrayList;
import java.util.List;

import repository.UserRepository;

public class CercaniaStrategy implements IEmparejamientoStrategy {
	@Override
	public void emparejar(List<Equipo> equipos, Partido partido) {
		String ubicacionPartido = partido.getUbicacion().getCiudad();

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
				System.out.println("Deporte: " + deporte.getNombre() + " - Geolocalización: " + jugador.getUbicacion().getCiudad());
			}
		}
		for (Usuario jugador : jugadoresBBDD) {
			for (Deporte deporte : jugador.getDeportes()) {
				// if(jugador.getDeportes().equals(partido.getDeporte())) {
				if(deporte.getNombre() == partido.getDeporte()) {
					//if(jugador.getUbicacion().getCiudad().equals(ubicacionPartido)){
					if(jugador.getUbicacion().getCiudad() == ubicacionPartido){
						// notificar al jugador
					}
				}
			}
		}
	}
}