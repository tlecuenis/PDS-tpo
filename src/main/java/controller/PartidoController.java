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
    
	public void crearPartido(PartidoDTO partidoDTO) {
		Partido partido = new Partido();
		partido.crearPartido(partidoDTO);
		partido.setCreador(usuarioLogueado);
		partidoRepository.save(partido);
		System.out.println("Partido creado: " + partido.getIdPartido());
	}

	public void buscarPartido(PartidoDTO partidoDTO) {
		Partido partido = partidoRepository.findById(partidoDTO.getIdPartido());
		if (partido == null) {
			System.out.println("Partido no encontrado.");
		} else {
			System.out.println("Partido encontrado: " + partido.getIdPartido());
		}
	}

	public void confirmar(String idPartido) {
		Partido partido = partidoRepository.findById(idPartido);
		if (partido == null || !usuarioLogueado.getIdUsuario().equals(partido.getCreador().getIdUsuario())) {
			System.out.println("No tienes permiso para confirmar este partido.");
			return;
		}
		partido.confirmar();
		partidoRepository.save(partido);
		System.out.println("Partido confirmado.");
	}

	public void cancelar(String idPartido) {
		Partido partido = partidoRepository.findById(idPartido);
		if (partido == null || !usuarioLogueado.getIdUsuario().equals(partido.getCreador().getIdUsuario())) {
			System.out.println("No tienes permiso para cancelar este partido.");
			return;
		}
		partido.cancelar();
		partidoRepository.save(partido);
		System.out.println("Partido cancelado.");
	}

	public void finalizar(String idPartido) {
		Partido partido = partidoRepository.findById(idPartido);
		if (partido == null || !usuarioLogueado.getIdUsuario().equals(partido.getCreador().getIdUsuario())) {
			System.out.println("No tienes permiso para finalizar este partido.");
			return;
		}
		partido.finalizar();
		partidoRepository.save(partido);
		System.out.println("Partido finalizado.");
	}

	public void iniciar(String idPartido) {
		Partido partido = partidoRepository.findById(idPartido);
		if (partido == null || !usuarioLogueado.getIdUsuario().equals(partido.getCreador().getIdUsuario())) {
			System.out.println("No tienes permiso para iniciar este partido.");
			return;
		}
		partido.iniciar();
		partidoRepository.save(partido);
		System.out.println("Partido iniciado.");
	}

	public void elegirEstrategia(String idPartido, IEstrategiaPartido estrategia) {
		Partido partido = partidoRepository.findById(idPartido);
		if (partido == null || !usuarioLogueado.getIdUsuario().equals(partido.getCreador().getIdUsuario())) {
			System.out.println("No tienes permiso para elegir una estrategia para este partido.");
			return;
		}
		partido.elegirEstrategia(estrategia);
		partidoRepository.save(partido);
		System.out.println("Estrategia asignada al partido.");
	}

	public List<Partido> obtenerTodosLosPartidos() {
		return partidoRepository.findAll();
	}

	public void guardarPartido(Partido partido) {
		partidoRepository.save(partido);
		System.out.println("Partido guardado: " + partido.getIdPartido());
	}
	
}
