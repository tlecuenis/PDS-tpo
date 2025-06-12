package model;

import java.util.List;

public class Deporte {

	private String nombre;
	private Nivel nivelJuego;
	private List<Partido> historial;

	public Deporte(String nombre, Nivel nivelJuego) {
		this.nombre = nombre;
		this.nivelJuego = nivelJuego;
		// this.historial = historial;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Nivel getNivelJuego() {
		return nivelJuego;
	}
	public void setNivelJuego(Nivel nivelJuego) {
		this.nivelJuego = nivelJuego;
	}
	// public List<Partido> getHistorial() {
	// 	return historial;
	// }
	// public void setHistorial(List<Partido> historial) {
	// 	this.historial = historial;
	// }
}
