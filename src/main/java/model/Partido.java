package model;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import DTO.PartidoDTO;
import controller.UsuarioController;

import java.time.LocalDateTime;
import model.notificaciones.IObserver;
import model.notificaciones.NotificacionDispatcher;
import model.notificaciones.ObserverPartido;

public class Partido extends ObserverPartido {
	private String idPartido;
	private String deporte;
	private int cantJugadores;
	private double duracion;
	private Geolocalizacion ubicacion;
	private LocalDateTime fecha;
	private List<Equipo> equipos;
	private IEstadoPartido estadoActual;
	private IEmparejamientoStrategy estrategiaActual;
	private String estadistica = "";
	private String comentario = "";
	private int nivelMaximo;
	private int nivelMinimo;
	private Usuario Creador;
	private Equipo ganador;
	
	public Partido() {
        super(new NotificacionDispatcher());
        this.equipos = new ArrayList<>();
	}

	public void cambiarEstado(IEstadoPartido estado) {
		this.estadoActual = estado;
	}

	public void crearEquipo(Equipo equipo) {
		this.equipos.add(equipo);
	}

	public boolean añadirAlEquipo(Usuario jugador, String nombreEquipo) {
	    for (Equipo equipo : equipos) {
	        if (equipo.getNombre().equals(nombreEquipo)) {
	            if (this.validarEntrada(jugador, equipo)) {
	                equipo.agregarJugador(jugador);
	                this.validarArmado();
	                return true;
	            } else {
	                return false;
	            }
	        }
	    }
	    return false;
	}

	public boolean gestionarIngresoAEquipo(Usuario jugador, Equipo equipo1, Equipo equipo2, String nombreEquipo) {
		boolean enEquipo1 = equipo1.getJugadores().stream()
				.anyMatch(j -> j.equals(jugador));
		boolean enEquipo2 = equipo2.getJugadores().stream()
				.anyMatch(j -> j.equals(jugador));

		if (enEquipo1 || enEquipo2) {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(null,
						"Ya estás anotado en otro equipo del partido",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			});
			return false;
		}

		int maxJugadoresPorEquipo = this.getCantJugadores() / 2;
		Equipo equipoDestino = nombreEquipo.equals("Equipo 1") ? equipo1 : equipo2;

		if (equipoDestino.getJugadores().size() >= maxJugadoresPorEquipo) {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(null,
						"El equipo ya tiene la cantidad máxima de jugadores",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			});
			return false;
		}

		boolean agregado = this.añadirAlEquipo(jugador, nombreEquipo);
		if (!agregado) {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(null,
						"No se pudo agregar al equipo. Ya estás anotado o hubo un error.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			});
		}
		return agregado;
	}
	
	public void eliminarDelEquipo(Usuario jugador, String nombreEquipo) {
		for(Equipo equipo : equipos) {
			if (equipo.getNombre().equals(nombreEquipo)){
				equipo.eliminarJugador(jugador);
				eliminarDestinatario(jugador);
				this.validarNecesitamosJugadores();
				return;
			}
		}
		System.out.println("No se encontró el equipo");
	}

	public void elegirEstrategia(IEmparejamientoStrategy estrategia) {
		this.estrategiaActual = estrategia;
	}
	
	public void emparejar(Partido p) {
		this.estrategiaActual.emparejar(this);
	}

	public void crearPartido(PartidoDTO partido) {
		this.idPartido = partido.getIdPartido();
		this.deporte = partido.getDeporte();
		this.cantJugadores = partido.getCantJugadores();
		this.duracion = partido.getDuracion();
		this.ubicacion = partido.getUbicacion();
		this.fecha = partido.getFecha();
		this.estadoActual = new NecesitamosJugadores();
		this.nivelMaximo = partido.getNivelJugadorMaximo();
		this.nivelMinimo = partido.getNivelJugadorMinimo();
		
		Usuario creadorUsuario = UsuarioController.getInstancia().getUserById(partido.getCreador());
        this.setCreador(creadorUsuario);
	}

	//Constructor necesario para la db
	//IMPORTANTE --> PASAR A DTO
	public Partido(String id, String deporte, double duracion, int cantJugadores, Geolocalizacion geolocalizacion, LocalDateTime horario, IEstadoPartido estado, String estadistica, String comentario, List<IObserver> observers, int nivelMinimo, int nivelMaximo, Usuario creador) {
        super(new NotificacionDispatcher());
		this.idPartido = id;
		this.deporte = deporte;
		this.cantJugadores = cantJugadores;
		this.duracion = duracion;
		this.ubicacion = geolocalizacion;
		this.fecha = horario;
		this.estadoActual = estado;
		this.estadistica = estadistica;
		this.comentario = comentario;
		this.observers = new HashSet<>(observers);
		this.nivelMaximo = nivelMaximo;
		this.nivelMinimo = nivelMinimo;
		this.equipos = new ArrayList<>();
		this.Creador = creador;
    }

	public void buscarPartido(PartidoDTO partido) {
		// Implementación a definir según lógica del sistema.
	}

	public void confirmar() {
		this.estadoActual.confirmar(this);
	}

	public void cancelar() {
		this.estadoActual.cancelar(this);
	}

	public void finalizar() {
		this.estadoActual.finalizar(this);
	}

	public void iniciar() {
		this.estadoActual.iniciar(this);
	}

	public void armar() {
		this.estadoActual.armar(this);
	}
	
	public void necesitamosJugadores() {
		this.estadoActual.necesitamosJugadores(this);
	}
	
	public void agregarComentario(String comentario) {
		this.comentario = comentario;
	}

	public void agregarEstadistica(String estadistica) {
		this.estadistica = estadistica;
	}

	public String getIdPartido() {
		return idPartido;
	}
	public void setIdPartido(String idPartido) {
		this.idPartido = idPartido;
	}
	
	public String getDeporte() {
		return deporte;
	}

	public Equipo getGanador() {
		return this.ganador;
	}

	public void declararGanador(Equipo ganador) {
		this.estadoActual.declararGanador(this, ganador);
	}

	public void setGanador(Equipo ganador) {
		this.ganador = ganador;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public void setObservers(List<IObserver> observers) {
		this.observers = new HashSet<>(observers);
	}


	
	// CAMBIOS DE ESTADO AUTOMÁTICOS
	
	public void validarArmado() { 		// Se ejecuta cada vez que un usuario se une al partido
		if (this.getCantJugadores() == this.cantidadJugadoresActual()) {
			this.armar();
		}
	}
	
	public void validarNecesitamosJugadores() { 			// Se ejecuta cuando un usuario abandona el partido
		if(this.getCantJugadores() > this.cantidadJugadoresActual()) {
			this.necesitamosJugadores();
		}
	}
	
	public boolean validarEnJuego() {
	    if (LocalDateTime.now().isAfter(fecha) || LocalDateTime.now().isEqual(fecha)) {
	        this.iniciar();
	        return true;
	    }
	    return false;
	}
	public Equipo getEquipo(String nombreEquipo) {
		for (Equipo equipo : equipos) {
			if (equipo.getNombre().toLowerCase().equals(nombreEquipo.toLowerCase())) {
				return equipo;
			}
		}
		return null;
	}

	public boolean validarEntrada(Usuario jugadorNuevo, Equipo equipo) {
		for (Usuario jugador : equipo.getJugadores()) {
			if (jugadorNuevo.equals(jugador)) {
				return false;
			}
		} return true;
	}



	
	
	// Los cambios a Cancelado, Confirmado y Finalizado se deben de hacer
	// manualmente desde la vista, solo validando si el creador del Partido es el mismo al
	// usuario logeado, para mostrarle los botones respectivos dentro la vista de ese partido.
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int cantidadJugadoresActual() {
	    return equipos.stream()
	                  .mapToInt(equipo -> equipo.getJugadores().size())
	                  .sum();
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
	public List<Equipo> getEquipos() {
		return equipos;
	}
	public List<IObserver> getObservador(){
		return new ArrayList<>(observers);
	}
	public IEstadoPartido getEstado() {
		return estadoActual;
	}
	public String getEstadistica() {
		return estadistica;
	}
	public String getComentario() {
		return comentario;
	}
	public void setNivelJugadorMinimo(int nivelMinimo) {
		this.nivelMinimo = nivelMinimo;
	}
	public int getNivelJugadorMinimo() {
		return nivelMinimo;
	}
	public void setNivelJugadorMaximo(int nivelMaximo) {
		this.nivelMaximo = nivelMaximo;
	}
	public int getNivelJugadorMaximo() {
		return nivelMaximo;
	}

	public IEstadoPartido getEstadoActual() {
		return estadoActual;
	}
	public void setEstadoActual(IEstadoPartido estadoActual) {
		this.estadoActual = estadoActual;
	}
	public IEmparejamientoStrategy getEstrategiaActual() {
		return estrategiaActual;
	}
	public void setEstrategiaActual(IEmparejamientoStrategy estrategiaActual) {
		this.estrategiaActual = estrategiaActual;
	}
	public int getNivelMaximo() {
		return nivelMaximo;
	}
	public void setNivelMaximo(int nivelMaximo) {
		this.nivelMaximo = nivelMaximo;
	}
	public int getNivelMinimo() {
		return nivelMinimo;
	}
	public void setNivelMinimo(int nivelMinimo) {
		this.nivelMinimo = nivelMinimo;
	}
	public Usuario getCreador() {
		return Creador;
	}
	public void setCreador(Usuario creador) {
		Creador = creador;
	}

}
