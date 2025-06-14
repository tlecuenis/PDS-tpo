package model;

import java.util.List;

public class Deporte {

	private String nombre;
	private Nivel nivelJuego;
	private int cantPartidos;
	private int score;
	
	public Deporte(String nombre, Nivel nivelJuego, int score, int cantPartidos) {
		this.nombre = nombre;
		this.nivelJuego = nivelJuego;
		this.score = score;
		this.cantPartidos = cantPartidos;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getCantPartidos() {
		return cantPartidos;
	}
	public void setCantPartidos(int cantPartidos) {
	 	this.cantPartidos = cantPartidos;
	}
}
