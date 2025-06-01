package model;

import java.sql.Date;
import java.util.List;

import DTO.PartidoDTO;

public class Partido {
	private String idPartido;
	private Deporte deporte;
	private int cantJugadores;
	private double duracion;
	private Barrio ubicacion;
	private Date horario;
	private List<Equipo> equipos;
	private EstadoPartido estado;
	private String estadistica;
	private String comentario;
	private ObserverPartido observador;
	
	public void cambiarEstado(EstadoPartido estado) {
		
	}
	
	public void crearEquipo(Equipo equipo) {
		
	}
	
	public void elegirEstrategia(IEmparejamientoStrategy estrategia) {
		
	}
	
	public void crearPartido(PartidoDTO partido) {
		
	}
	
	public void buscarPartido(PartidoDTO partido) {
		
	}
	
    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }
	
    public void confirmar() {
        estado.confirmar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void iniciar() {
        estado.iniciar(this);
    }

    public void finalizar() {
        estado.finalizar(this);
    }

    public void agregarComentario() {
        estado.agregarComentario(this);
    }

    public void agregarEstadistica() {
        estado.agregarEstadistica(this);
    }
}
