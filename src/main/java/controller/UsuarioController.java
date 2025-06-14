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

	public UsuarioDTO getUsuario(String nickname) {
	    Usuario usuario = userDAO.findById(nickname);
	    if (usuario == null) return null;

	    // Convertir Usuario → UsuarioDTO
	    String deporte = "";
	    String nivel = "";

	    if (!usuario.getDeportes().isEmpty()) {
	        Deporte d = usuario.getDeportes().get(0);
	        deporte = d.getNombre();
	        nivel = d.getNivelJuego().toString();
	    }

	    return new UsuarioDTO(
	        usuario.getIdUsuario(),
	        usuario.getNombre(),
	        usuario.getEmail(),
	        usuario.getContraseña(),
	        usuario.getUbicacion().getCiudad(),
	        deporte,
	        nivel
	    );
	}

	public boolean registrarUsuario(UsuarioDTO dto) {
		if (userDAO.findById(dto.getNickname()) != null) {
			System.out.println("Ya existe un usuario con ese ID.");
			return false;
		}

		// Construir el usuario
		List<Deporte> deportes = new ArrayList<>();
		if (dto.getDeporte() != null && !dto.getDeporte().isEmpty()
				&& dto.getNivel() != null && !dto.getNivel().isEmpty()) {
			try {
				Nivel nivel = Nivel.valueOf(dto.getNivel().toUpperCase());
				deportes.add(new Deporte(dto.getDeporte(), nivel));
			} catch (IllegalArgumentException e) {
				System.out.println("Nivel inválido, se omite deporte.");
			}
		}

		// Ubicación dummy
		Geolocalizacion geo = new Geolocalizacion(0.0, 0.0, 0.0, dto.getCiudad());

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

	public void notificarUsuario(){

	}

	public boolean actualizarUsuario(UsuarioDTO dto) {
	    Usuario usuarioExistente = userDAO.findById(dto.getNombre());
	    if (usuarioExistente == null) {
	        System.out.println("Usuario no existe.");
	        return false;
	    }

	    // Actualizar datos básicos
	    usuarioExistente.setNombre(dto.getNombre());
	    usuarioExistente.setEmail(dto.getEmail());
	    
	    Geolocalizacion geo = new Geolocalizacion(0.0, 0.0, 0.0, dto.getCiudad());
	    usuarioExistente.setUbicacion(geo);
	    
	    // Actualizar deporte y nivel (simplificado para 1 deporte)
	    try {
	        Nivel nivel = Nivel.valueOf(dto.getNivel().toUpperCase());
	        List<Deporte> deportes = new ArrayList<>();
	        deportes.add(new Deporte(dto.getDeporte(), nivel));
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
	
	public boolean agregarDeporteAUsuario(String nickname, String deporte, String nivel) {
	    Usuario usuario = userDAO.findById(nickname);
	    if (usuario == null) return false;

	    List<Deporte> deportes = usuario.getDeportes();
	    for (Deporte d : deportes) {
	        if (d.getNombre().equalsIgnoreCase(deporte)) {
	            if (!d.getNivelJuego().toString().equalsIgnoreCase(nivel)) {
	                try {
	                    d.setNivelJuego(Nivel.valueOf(nivel.toUpperCase()));
	                    userDAO.update(usuario);
	                    return true;
	                } catch (IllegalArgumentException e) {
	                    return false;
	                }
	            }
	            return false; // mismo nivel
	        }
	    }

	    try {
	        deportes.add(new Deporte(deporte, Nivel.valueOf(nivel.toUpperCase())));
	        usuario.setDeportes(deportes);
	        userDAO.update(usuario);
	        return true;
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	}
	
	public boolean existeDeporte(String nickname, String deporte) {
		UsuarioDTO usuario = UsuarioController.getInstancia().getUsuario(nickname);
	    if (usuario == null) return false;

	    return usuario.getDeporte() != null &&
	           usuario.getDeporte().equalsIgnoreCase(deporte);
	}
	
	public String getNivelDeporte(String nickname, String deporte) {
		UsuarioDTO usuario = UsuarioController.getInstancia().getUsuario(nickname);
	    if (usuario == null) return "";

	    if (usuario.getDeporte() != null &&
	        usuario.getDeporte().equalsIgnoreCase(deporte)) {
	        return usuario.getNivel();
	    }
	    return "";
	}

	public boolean modificarNivelDeporte(String nickname, String deporte, String nuevoNivel) {
	    Usuario usuario = userDAO.findById(nickname);
	    if (usuario == null) return false;

	    List<Deporte> deportes = usuario.getDeportes();

	    for (Deporte d : deportes) {
	        if (d.getNombre().equalsIgnoreCase(deporte)) {
	            try {
	                Nivel nivelEnum = Nivel.valueOf(nuevoNivel.toUpperCase());
	                d.setNivelJuego(nivelEnum);
	                userDAO.update(usuario);  // <-- ¡GUARDAMOS!
	                return true;
	            } catch (IllegalArgumentException e) {
	                System.out.println("Nivel inválido: " + nuevoNivel);
	                return false;
	            }
	        }
	    }

	    return false;
	}
	
	public List<String> obtenerDeportesConNivel(String nickname) {
	    Usuario usuario = userDAO.findById(nickname);
	    List<String> resultados = new ArrayList<>();

	    if (usuario != null) {
	        for (Deporte deporte : usuario.getDeportes()) {
	            resultados.add(deporte.getNombre() + " - " + deporte.getNivelJuego().name());
	        }
	    }

	    return resultados;
	}
	
}


