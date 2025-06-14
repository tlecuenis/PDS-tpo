package controller;

import repository.PartidoDAO;
import repository.mongoRepository.MongoPartidoRepository;
import java.util.List;
import java.util.stream.Collectors;

import DTO.PartidoDTO;
import model.IEstrategiaPartido;
import model.Partido;
import model.Usuario;

public class PartidoController {

    private static PartidoController instancia;
    private final PartidoDAO partidoRepository;

    private Usuario usuarioLogueado;

    private PartidoController() {
        partidoRepository = new MongoPartidoRepository();
    }

    public static PartidoController getInstancia() {
        if (instancia == null) {
            instancia = new PartidoController();
        }
        return instancia;
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public List<Partido> obtenerPartidosDelUsuario() {
        List<Partido> todos = partidoRepository.findAll();
        return todos.stream()
                .filter(p -> p.getEquipos().stream()
                    .flatMap(e -> e.getJugadores().stream())
                    .anyMatch(j -> j.getIdUsuario().equals(usuarioLogueado.getIdUsuario())))
                .collect(Collectors.toList());
    }

    public List<Partido> obtenerPartidosCreadosPorUsuario() {
        List<Partido> todos = partidoRepository.findAll();
        return todos.stream()
                .filter(p -> p.getCreador().getIdUsuario().equals(usuarioLogueado.getIdUsuario()))
                .collect(Collectors.toList());
    }

    public void actualizarPartido(Partido partido) {
        partidoRepository.save(partido); // o update si tenés
    }

    public void validarTodosEnJuego() {
        List<Partido> todos = partidoRepository.findAll();
        for (Partido p : todos) {
            if (p.validarEnJuego()) {
                partidoRepository.save(p);
            }
        }
    }
    
	public void crearPartido(PartidoDTO partido) {
		
	}
	
	public void buscarPartido(PartidoDTO partido) {
		
	}
	
	public void confirmar() {
		
	}
	
	public void cancelar() {
		
	}
	
	public void finalizar() {
		
	}
	
	public void iniciar() {
		
	}
	
	public void elegirEstrategia(IEstrategiaPartido estrategia) {
		
	}

	public List<Partido> obtenerTodosLosPartidos() {
		// TODO Auto-generated method stub
		return null;
	}

	public void guardarPartido(Partido p) {
		// TODO Auto-generated method stub
		
	}
	
}
