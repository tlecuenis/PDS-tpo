package model;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import DTO.PartidoDTO;

public class Partido {
	private String idPartido;
	private Deporte deporte;
	private int cantJugadores;
	private double duracion;
	private Geolocalizacion ubicacion;
	private java.util.Date horario;
	private List<Equipo> equipos;
	private IEstadoPartido estadoActual;
	private IEstrategiaPartido estrategiaActual;
	private String estadistica;
	private String comentario;
	private ObserverPartido observador;

	public Partido() {
		this.equipos = new ArrayList<>();
	}

	public void cambiarEstado(IEstadoPartido estado) {
		this.estadoActual = estado;
	}

	public void crearEquipo(Equipo equipo) {
		this.equipos.add(equipo);
	}
	
	public void añadirAlEquipo(Usuario jugador, String nombreEquipo) {
		for(Equipo equipo : equipos) {
			if (equipo.getNombre().equals(nombreEquipo)){
				equipo.agregarJugador(jugador);
				return;
			}
		}
		System.out.println("No se encontró el equipo");
	}
	
	public void eliminarDelEquipo(Usuario jugador, String nombreEquipo) {
		for(Equipo equipo : equipos) {
			if (equipo.getNombre().equals(nombreEquipo)){
				equipo.eliminarJugador(jugador);
				return;
			}
		}
		System.out.println("No se encontró el equipo");
	}

	public void elegirEstrategia(IEstrategiaPartido estrategia) {
		this.estrategiaActual = estrategia;
	}
	
	public void emparejar() {
		this.estrategiaActual.emparejar();
	}

	public void crearPartido(PartidoDTO partido) {
		this.idPartido = partido.getIdPartido();
		this.deporte = partido.getDeporte();
		this.cantJugadores = partido.getCantJugadores();
		this.duracion = partido.getDuracion();
		this.ubicacion = partido.getUbicacion();
		this.horario = partido.getHorario();
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
	public Deporte getDeporte() {
		return deporte;
	}
	public void setDeporte(Deporte deporte) {
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
	public java.util.Date getHorario() {
		return horario;
	}
	public void setHorario(Date horario) {
		this.horario = horario;
	}
	public List<Equipo> getEquipos() {
		return equipos;
	}

	public IEstadoPartido getEstado() {
		return estadoActual;
	}
	public IEstrategiaPartido getEstrategia() {
		return estrategiaActual;
	}
	public String getEstadistica() {
		return estadistica;
	}
	public String getComentario() {
		return comentario;
	}
	public ObserverPartido getObservador() {
		return observador;
	}
	public void setObservador(ObserverPartido observador) {
		this.observador = observador;
	}
	
}
