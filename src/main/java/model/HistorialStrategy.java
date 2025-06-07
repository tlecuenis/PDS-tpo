package model;

import java.util.ArrayList;
import java.util.List;

import repository.UserRepository;

public class HistorialStrategy implements IEmparejamientoStrategy {
	@Override
	public void emparejar(List<Equipo> equipos, Partido partido) {
		int historialRequerido = 2; //falta definir el valor de historial en Partido

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
		// DESCOMENTAR CUANDO SE TENGA EL HISTORIAL EN DEPORTE
		// List<Integer> historialJugador = new ArrayList<Integer>();
		// for (Usuario jugador : jugadoresBBDD) {
		// 	for (Deporte deporte : jugador.getDeportes()) {
		// 		// if(jugador.getDeportes().equals(partido.getDeporte())) {
		// 		if(deporte == partido.getDeporte()) {
		// 			for (Partido partidoHistorico : deporte.getHistorial()) {
		// 				// obtener el equipo del que formó parte el jugador
		// 				partidoHistorico.getEquipos().forEach(equipo -> {
		// 					if (equipo.getJugadores().contains(jugador)) {
								// DESCOMENTAR CUANDO SE TENGA EL GANADOR EN PARTIDO

								// if (partidoHistorico.getEstadistica().getGanador().equals(equipo.getNombre())) {
								// 	historialJugador.add(3);
								// } else if (partidoHistorico.getEstadistica().getGanador() == null) {
								// 	historialJugador.add(1);
								// } else {
								// 	historialJugador.add(0);
								// }

								// Promedio de los resultados del historial
								// int suma = historialJugador.stream().mapToInt(Integer::intValue).sum();
								// int promedio = suma / historialJugador.size();
								// if (promedio >= historialRequerido) {
								// 	// notificar al jugador
								// }
		// 					}
		// 				});
		// 			}
		// 		}
		// 	}
		// }
	}

}

