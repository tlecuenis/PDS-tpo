package controller;

import model.IEstrategiaPartido;
import DTO.PartidoDTO;

public class PartidoController {
	
	private static PartidoController instancia;
	
	public static PartidoController getInstancia() {
		if (instancia == null) {
			instancia = new PartidoController();
		}
		return instancia;
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
	
}
