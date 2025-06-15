package model;

import java.util.List;
import DTO.UsuarioDTO;
import model.notificaciones.IObserver;
import model.notificaciones.Notificacion;
import model.notificaciones.PreferenciaNotificacion;


public class Usuario implements IObserver {
	private String idUsuario;
	private String nombre;
	private String email;
	private String contraseña;
	private List<Deporte> deportes;
	private Geolocalizacion ubicacion;
	private PreferenciaNotificacion preferenciaNotificacion;


	public Usuario(String idUsuario, String nombre, String email, String contraseña, List<Deporte> deportes, Geolocalizacion ubicacion) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.email = email;
		this.contraseña = contraseña;
		this.deportes = deportes;
		this.ubicacion = ubicacion;
		this.preferenciaNotificacion = PreferenciaNotificacion.FIREBASE_PREFERENCE;
	}

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
	public void setPreferenciaNotificacion(PreferenciaNotificacion preferenciaNotificacion) {
		this.preferenciaNotificacion = preferenciaNotificacion;
	}
	public PreferenciaNotificacion getPreferenciaNotificacion() {
		return preferenciaNotificacion;
	}

	@Override
	public void serNotificado(Notificacion notificacion) {
		System.out.println("["+nombre+"]" + " siendo notificado: Partido id:"+notificacion.getPartido().getIdPartido() +" -> " + notificacion.getMensaje());
		//Implementar lógica para que por ej le salga un mensaje en la UI si está conectado
	}

	//Estos métodos sirven para que un mismo Usuario no pueda ser agregado 2 veces a la lista de observers del mismo Partido
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return this.getEmail().equals(usuario.getEmail()); // O el atributo que los identifica
	}

	@Override
	public int hashCode() {
		return this.getEmail().hashCode(); // Igual que en equals
	}
}


