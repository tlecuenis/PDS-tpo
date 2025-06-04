package view;

import javax.swing.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {

	private JTextField txtUsuario;
	private JPasswordField txtPassword;

	public LoginPanel(MenuPrincipal parent) {
		setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(30, 30, 80, 25);
		add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(120, 30, 200, 25);
		add(txtUsuario);

		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(30, 70, 80, 25);
		add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(120, 70, 200, 25);
		add(txtPassword);

		JButton btnIniciar = new JButton("Iniciar Sesión");
		btnIniciar.setBounds(30, 120, 130, 30);
		add(btnIniciar);

		JButton btnRegistrar = new JButton("Registrarse");
		btnRegistrar.setBounds(180, 120, 130, 30);
		add(btnRegistrar);
		
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = txtUsuario.getText().trim();
				String password = txtPassword.getText().trim();

				if (usuario.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
					return;
				}

				// Acá iría la lógica de guardar usuario, etc.

				JOptionPane.showMessageDialog(null, "Inicio Sesion exitoso.");
				parent.showPanel("menuPrincipal");
			}
		});
		
		
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.showPanel("register");
			}
		});
	}
}
