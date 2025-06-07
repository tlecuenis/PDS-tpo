package view;

import javax.swing.*;
import java.awt.event.*;

public class OpcionesMenu extends JPanel {

	public OpcionesMenu(Ejecucion parent) {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Menú Principal");
		lblTitulo.setBounds(140, 10, 200, 30);
		add(lblTitulo);

		// Botón para ver lista de partidos disponibles
		JButton btnVerPartidos = new JButton("Ver Partidos Disponibles");
		btnVerPartidos.setBounds(100, 50, 200, 30);
		add(btnVerPartidos);
		btnVerPartidos.addActionListener(e -> parent.showPanel("listaPartidos"));

		// Botón para crear un nuevo partido
		JButton btnCrearPartido = new JButton("Crear Partido");
		btnCrearPartido.setBounds(100, 90, 200, 30);
		add(btnCrearPartido);
		btnCrearPartido.addActionListener(e -> parent.showPanel("crearPartido"));

		// Botón para ver el perfil del usuario
		JButton btnPerfil = new JButton("Ver Perfil");
		btnPerfil.setBounds(100, 130, 200, 30);
		add(btnPerfil);
		btnPerfil.addActionListener(e -> parent.showPanel("perfil"));

		// Botón para ver notificaciones
		JButton btnNotificaciones = new JButton("Notificaciones");
		btnNotificaciones.setBounds(100, 170, 200, 30);
		add(btnNotificaciones);
		btnNotificaciones.addActionListener(e -> parent.showPanel("notificaciones"));

		// Botón para cerrar sesión (volver al login)
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.setBounds(100, 210, 200, 30);
		add(btnCerrarSesion);
		btnCerrarSesion.addActionListener(e -> parent.showPanel("login"));
	}
}


