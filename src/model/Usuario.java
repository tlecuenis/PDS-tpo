package model;

import java.util.List;
import DTO.UsuarioDTO;


public class Usuario {
	private String idUsuario;
	private String nombre;
	private String email;
	private String contraseña;
	private List<Deporte> deportes;
	private Geolocalizacion ubicacion;
	
	
	public void registrarse(UsuarioDTO usuario) {
		// COMPLETAR CON LA BASE DE DATOS
	}
	
	public void iniciarSesion(UsuarioDTO usuario) {
		// COMPLETAR CON LA BASE DE DATOS
	}
	
	
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
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public List<Deporte> getDeportes() {
		return deportes;
	}
	public void setDeportes(List<Deporte> deportes) {
		this.deportes = deportes;
	}
	public Geolocalizacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Geolocalizacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
}
