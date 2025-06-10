package DTO;

public class UsuarioDTO {
	private String nickname;
	private String nombre;
	private String email;
	private String contrasena;
	private String ciudad;
	private String deporteFavorito;
	private String nivelDeJuego;

	public UsuarioDTO(String nickname, String nombre, String email, String contrasena, String ciudad,
					  String deporteFavorito, String nivelDeJuego) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
		this.ciudad = ciudad;
		this.deporteFavorito = deporteFavorito;
		this.nivelDeJuego = nivelDeJuego;
	}

	public String getNickname() { return nickname; }
	public void setNickname(String nickname) { this.nickname = nickname; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
	

	public String getCiudad() {return ciudad;}
	public void setCiudad(String ciudad) {this.ciudad = ciudad;}

	public String getDeporteFavorito() { return deporteFavorito; }
	public void setDeporteFavorito(String deporteFavorito) { this.deporteFavorito = deporteFavorito; }

	public String getNivelDeJuego() { return nivelDeJuego; }
	public void setNivelDeJuego(String nivelDeJuego) { this.nivelDeJuego = nivelDeJuego; }
}

