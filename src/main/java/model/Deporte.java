package model;

public class Deporte {

	public Deporte(String nombre, Nivel nivelJuego) {
		this.nombre = nombre;
		this.nivelJuego = nivelJuego;
	}

	private String nombre;
	private Nivel nivelJuego;
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
}
