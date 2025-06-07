package controller;

import DTO.UsuarioDTO;
import model.*;
import repository.UserDAO;
import repository.mongoRepository.MongoUserRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
	private static UsuarioController instancia;
	private final UserDAO userDAO;

	private UsuarioController() {
		this.userDAO = new MongoUserRepository();
	}

	public static UsuarioController getInstancia() {
		if (instancia == null) {
			instancia = new UsuarioController();
		}
		return instancia;
	}

	public boolean registrarUsuario(UsuarioDTO dto) {
		if (userDAO.findById(dto.getNickname()) != null) {
			System.out.println("Ya existe un usuario con ese ID.");
			return false;
		}

		// Construir el usuario
		List<Deporte> deportes = new ArrayList<>();
		if (dto.getDeporteFavorito() != null && !dto.getDeporteFavorito().isEmpty()
				&& dto.getNivelDeJuego() != null && !dto.getNivelDeJuego().isEmpty()) {
			try {
				Nivel nivel = Nivel.valueOf(dto.getNivelDeJuego().toUpperCase());
				deportes.add(new Deporte(dto.getDeporteFavorito(), nivel));
			} catch (IllegalArgumentException e) {
				System.out.println("Nivel inválido, se omite deporte.");
			}
		}

		// Ubicación dummy
		Geolocalizacion geo = new Geolocalizacion(0.0, 0.0, 0.0, "CiudadDesconocida");

		Usuario nuevoUsuario = new Usuario(dto.getNickname(), dto.getNombre(), dto.getEmail(),
				dto.getContrasena(), deportes, geo);

		userDAO.save(nuevoUsuario);
		return true;
	}

	public boolean loginUsuario(UsuarioDTO dto) {
		Usuario user = userDAO.findById(dto.getNickname());
		if (user == null) return false;
		return user.getContraseña().equals(dto.getContrasena());
	}
	
	public Usuario getUserById(String id) {
	    return userDAO.findById(id);
	}

	
	public boolean actualizarUsuario(UsuarioDTO dto) {
	    Usuario usuarioExistente = userDAO.findById(dto.getNickname());
	    if (usuarioExistente == null) {
	        System.out.println("Usuario no existe.");
	        return false;
	    }

	    // Actualizar datos básicos
	    usuarioExistente.setNombre(dto.getNombre());
	    usuarioExistente.setEmail(dto.getEmail());

	    // Actualizar deporte y nivel (simplificado para 1 deporte)
	    try {
	        Nivel nivel = Nivel.valueOf(dto.getNivelDeJuego().toUpperCase());
	        List<Deporte> deportes = new ArrayList<>();
	        deportes.add(new Deporte(dto.getDeporteFavorito(), nivel));
	        usuarioExistente.setDeportes(deportes);
	    } catch (IllegalArgumentException e) {
	        System.out.println("Nivel inválido, no se actualiza deporte.");
	    }

	    // Guardar usuario actualizado (en mongo, mejor usar update)
	    try {
	        userDAO.update(usuarioExistente); // <-- este método debes implementar en tu DAO
	        return true;
	    } catch (Exception e) {
	        System.out.println("Error actualizando usuario: " + e.getMessage());
	        return false;
	    }
	}
}


