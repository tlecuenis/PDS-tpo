package DTO;

import java.time.LocalDateTime;
import java.util.Date;
import model.Geolocalizacion;
import model.Deporte;

public class PartidoDTO {
	private String idPartido;
	private String deporte;
	private int cantJugadores;
	private double duracion;
	private Geolocalizacion ubicacion;
	private LocalDateTime fecha;
	private int nivelMaximo;
	private int nivelMinimo;
	private String creadorId;
	
	public String getIdPartido() {
		return idPartido;
	}
	public void setIdPartido(String idPartido) {
		this.idPartido = idPartido;
	}
	
	public String getDeporte() {
		return deporte;
	}
	public void setDeporte(String deporte) {
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
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public int getNivelJugadorMaximo(){
		return nivelMaximo;
	}
	public void setNivelJugadorMaximo(int nivelMaximo) {
		this.nivelMaximo = nivelMaximo;
	}
	public int getNivelJugadorMinimo() {
		return nivelMinimo;
	}
	public void setNivelJugadorMinimo(int nivelMinimo) {
		this.nivelMinimo = nivelMinimo;
	}
	public String getCreador() {
		return creadorId;
	}
	public void setCreador(String creadorId) {
		this.creadorId = creadorId;
	}
}


