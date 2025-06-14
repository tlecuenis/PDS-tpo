import DTO.PartidoDTO;
import model.*;
import model.notificaciones.Notificacion;
import model.notificaciones.NotificacionDispatcher;
import repository.PartidoRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

//        UserRepository ur = new UserRepository();
//        List<Usuario> usuarios = ur.findAll();
//        for (Usuario usuario: usuarios) {
//        	System.out.println(usuario.getNombre());
//        }
//        
//        PartidoRepository pr = new PartidoRepository();
//        Partido p2 = pr.findById("1");
//        System.out.println(p2.getFecha().toString());
//        p2.setEstadoActual(new NecesitamosJugadores());
        	
        
//        PartidoRepository pr = new PartidoRepository();
//        Partido p2 = pr.findById("1");
//        System.out.println(p2.getFecha().toString());
//        p2.setEstadoActual(new Armado());
//        p2.confirmar();
//        p2.iniciar();

        PartidoRepository pr = new PartidoRepository();
        Partido p2 = pr.findById("1");
        UserRepository ur = new UserRepository();
        List<Usuario> usuarios = ur.findAll();
        Notificacion notificacion = new Notificacion(p2, "Te estamos buscando! Queremos jugadores que jueguen al fútbol y vivan en avellaneda!");
        for (Usuario usuario: usuarios) {
        	for (Deporte deporte : usuario.getDeportes()) {
        		if(deporte.getNombre().toLowerCase().contains("fútbol") && usuario.getUbicacion().getCiudad().contains("Avellaneda")) {
        			System.out.println(usuario.getNombre());
                	usuario.serNotificado(notificacion);
        		}
        	}
        }
        p2.setEstadoActual(new Armado());
        p2.confirmar();
        p2.iniciar();
        
        
    }
}