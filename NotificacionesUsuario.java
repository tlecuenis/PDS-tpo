package view;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NotificacionesUsuario extends JPanel {

	public NotificacionesUsuario(Ejecucion parent) {
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(null);

		JLabel lbl = new JLabel("Notificaciones recientes (simulado)");
		lbl.setBounds(80, 100, 250, 30);
		add(lbl);
		
        // Boton volver
        gbc.gridx = 1;
        JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(250, 220, 100, 30);
		add(btnVolver, gbc);
        
    	btnVolver.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			parent.showPanel("menuPrincipal");
    		}
    	});
	}
}
