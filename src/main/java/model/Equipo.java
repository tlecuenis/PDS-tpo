package model;

import java.util.ArrayList;
import java.util.List;
import model.Partido;

public class Equipo {
    private String nombre;
    private List<Usuario> jugadores =  new ArrayList<Usuario>();
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Usuario> getJugadores() {
		return jugadores;
	}
	public void agregarJugador(Usuario jugador) {
		this.jugadores.add(jugador);
	}
	public void eliminarJugador(Usuario jugador) {
		this.jugadores.remove(jugador);
	}
}