package view;

import javax.swing.*;

public class NotificacionesUsuario extends JPanel {

	public NotificacionesUsuario() {
		setLayout(null);

		JLabel lbl = new JLabel("Notificaciones recientes (simulado)");
		lbl.setBounds(80, 100, 250, 30);
		add(lbl);
	}
}
