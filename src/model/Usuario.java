package model;

import java.util.List;

public class Usuario {
	private String idUsuario;
	private String nombre;
	private String email;
	private String contrasenia;
	private List<Deporte> deportes;
	private Barrio ubicacion;
	
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public Barrio getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Barrio ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	
}
