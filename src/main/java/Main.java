import DTO.PartidoDTO;
import model.*;
import model.notificaciones.Notificacion;
import model.notificaciones.NotificacionDispatcher;
import model.notificaciones.PreferenciaNotificacion;
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
        /*
        Partido p2 = pr.findById("1");
        UserRepository ur = new UserRepository();
        List<Usuario> usuarios = ur.findAll();
        Notificacion notificacion = new Notificacion(p2, "Te estamos buscando! Queremos jugadores que jueguen al fútbol y vivan en avellaneda!");
		NotificacionDispatcher notificacionDispatcher = new NotificacionDispatcher();

        for (Usuario usuario: usuarios) {
        	for (Deporte deporte : usuario.getDeportes()) {
        		if(deporte.getNombre().toLowerCase().contains("fútbol") && usuario.getUbicacion().getCiudad().contains("Avellaneda")) {
        			System.out.println(usuario.getNombre());
                	notificacionDispatcher.enviar(notificacion, usuario);
        		}
        	}
        }
        p2.setEstadoActual(new Armado());
        p2.confirmar();
        p2.iniciar();
         */
        UserRepository ur = new UserRepository();
        /*
        Deporte d = new Deporte("Fútbol", Nivel.INTERMEDIO, 0, 0);
        Deporte d2 = new Deporte("Tenis", Nivel.PRINCIPIANTE, 0, 0);
        List<Deporte> deportes = new ArrayList<>();
        deportes.add(d);
        deportes.add(d2);
        Geolocalizacion g = new Geolocalizacion(19.5, 1.5, 8.2, "San Telmo");

        for(int i=1; i<=30; i++){
            String id = "user"+String.valueOf(i);
            String nombre = "Usuario  "+String.valueOf(i);
            String email = "user"+String.valueOf(i) + "@email.com";
            String contrasenia = "user"+String.valueOf(i) + "contraseña";
            Usuario usuario = new Usuario(id, nombre, email, contrasenia, deportes, g);
            user.save(usuario);
         */

        

    }
}