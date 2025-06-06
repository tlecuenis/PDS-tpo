package DTO;

import java.util.Date;
import model.Geolocalizacion;
import model.Deporte;

public class PartidoDTO {
	private String idPartido;
	private Deporte deporte;
	private int cantJugadores;
	private double duracion;
	private Geolocalizacion ubicacion;
	private Date horario;
	
	public String getIdPartido() {
		return idPartido;
	}
	public void setIdPartido(String idPartido) {
		this.idPartido = idPartido;
	}
	public Deporte getDeporte() {
		return deporte;
	}
	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}
	public int getCantJugadores() {
		return cantJugadores;
	}
	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}
	public double getDuracion() {
		return duracion;
	}
	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}
	public Geolocalizacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Geolocalizacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	public java.util.Date getHorario() {
		return horario;
	}
	public void setHorario(Date horario) {
		this.horario = horario;
	}
}


