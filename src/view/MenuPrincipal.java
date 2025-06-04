package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

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
		MenuPrincipal frame = new MenuPrincipal();
		frame.setVisible(true);
	}
}


