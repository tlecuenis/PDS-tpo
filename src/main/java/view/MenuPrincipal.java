package view;

import model.*;
import repository.PartidoRepository;
import repository.UserRepository;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private CardLayout cardLayout;

	public MenuPrincipal() {
		setTitle("Sistema Deportivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 500, 400);

		cardLayout = new CardLayout();
		contentPane = new JPanel(cardLayout);
		setContentPane(contentPane);

		// Instanciación de vistas básicas
		LoginPanel loginPanel = new LoginPanel(this);
		RegisterPanel registerPanel = new RegisterPanel(this);

		contentPane.add(loginPanel, "login");
		contentPane.add(registerPanel, "register");

		// NUEVAS VISTAS del menú principal
		OpcionesMenu dashboardPanel = new OpcionesMenu(this);
		PartidosDisponibles listaPartidosPanel = new PartidosDisponibles(this);
		CrearPartido crearPartidoPanel = new CrearPartido();
		PerfilUsuario perfilPanel = new PerfilUsuario(this);
		NotificacionesUsuario notificacionesPanel = new NotificacionesUsuario();

		contentPane.add(dashboardPanel, "menuPrincipal");
		contentPane.add(listaPartidosPanel, "listaPartidos");
		contentPane.add(crearPartidoPanel, "crearPartido");
		contentPane.add(perfilPanel, "perfil");
		contentPane.add(notificacionesPanel, "notificaciones");

		// Mostrar vista inicial
		showPanel("login");
	}

	public void showPanel(String name) {
		cardLayout.show(contentPane, name);
	}
	

	public static void main(String[] args) {
		//MenuPrincipal frame = new MenuPrincipal();
		//frame.setVisible(true);

		//test db
		List<Deporte> deportes = new ArrayList();
		Deporte d1 = new Deporte("fútbol", Nivel.INTERMEDIO);
		Deporte d2 = new Deporte("tenis", Nivel.PRINCIPIANTE);
		deportes.add(d1);
		deportes.add(d2);
		Geolocalizacion g = new Geolocalizacion(0.1, 0.1, 0.1, "ciudadela");
		Usuario u = new Usuario("5", "assd13", "asda@gmail.com", "123123123", deportes, g);

		UserRepository userRepository = new UserRepository();
		//userRepository.deleteById("5");
		//userRepository.save(u);
		//System.out.println(userRepository.findById("5").getNombre());
		/*
		List<Usuario> usuarios = userRepository.findAll();
		for (Usuario usuario : usuarios) {
			System.out.println(usuario.getIdUsuario());
		}
		*/
		 //System.out.println(userRepository.findByField("email", "asda@gmail.com").getContraseña());
		Partido p = new Partido();
		p.setIdPartido("2");
		p.setDeporte(d1);
		p.setCantJugadores(22);
		p.setDuracion(90);
		p.setUbicacion(g);
		Date d = new Date(2025, 6, 6);
		p.setHorario(d);
		Equipo e = new Equipo();
		e.setNombre("Equipo1");
		p.crearEquipo(e);
		p.añadirAlEquipo(u, "Equipo1");
		p.añadirAlEquipo(u, "Equipo1");
		p.añadirAlEquipo(u, "Equipo1");
		Equipo e2 = new Equipo();
		e2.setNombre("Equipo2");
		p.crearEquipo(e2);
		p.añadirAlEquipo(u, "Equipo2");
		p.añadirAlEquipo(u, "Equipo2");
		NotificacionDispatcher nd = new NotificacionDispatcher(new EmailStrategy(new AdapterJavaEmail()));
		ObserverPartido o = new ObserverPartido(nd, new EstadoActualizado());
		o.agregarDestinatario(u);
		p.setObservador(o);
		PartidoRepository partidoRepository = new PartidoRepository();
		partidoRepository.save(p);
	}
}


