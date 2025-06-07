package view;

import javax.swing.*;

import DTO.UsuarioDTO;
import controller.UsuarioController;

import java.awt.event.*;

public class LoginPanel extends JPanel {

	private JTextField txtUsuario;
	private JPasswordField txtPassword;

	public LoginPanel(Ejecucion parent) {
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

		        UsuarioDTO dto = new UsuarioDTO(usuario, "", "", password, "", "");
		        boolean loginExitoso = UsuarioController.getInstancia().loginUsuario(dto);

		        if (loginExitoso) {
		            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");

		            // Le avisamos a Ejecucion quién es el usuario actual
		            parent.setNicknameActual(usuario);

		            // Mostrar menú principal
		            parent.showPanel("menuPrincipal");
		        } else {
		            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
		        }
		    }
		});

		
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.showPanel("register");
			}
		});
	}
}
