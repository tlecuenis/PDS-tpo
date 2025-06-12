package view;

import javax.swing.*;

import DTO.UsuarioDTO;
import controller.UsuarioController;

import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JLabel lblMensaje;

    public LoginPanel(Ejecucion parent) {
        setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);
        
        JLabel lblTitulo = new JLabel("Bienvenido a Uno Más");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelContenido.add(lblTitulo);
        
        JPanel formPanel = new JPanel(null);
        formPanel.setPreferredSize(new Dimension(350, 210));

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 30, 80, 25);
        formPanel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 200, 25);
        formPanel.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(30, 70, 80, 25);
        formPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 200, 25);
        formPanel.add(txtPassword);

        JButton btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setBounds(30, 120, 130, 30);
        formPanel.add(btnIniciar);

        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(180, 120, 130, 30);
        formPanel.add(btnRegistrar);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setBounds(25, 160, 300, 25);
        lblMensaje.setForeground(Color.RED);
        formPanel.add(lblMensaje);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelContenido, gbc); // <- Esto falta

        gbc.gridy = 1;
        add(formPanel, gbc);

        // Listener para iniciar sesión
        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblMensaje.setText("");

                String usuario = txtUsuario.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (usuario.isEmpty() || password.isEmpty()) {
                    lblMensaje.setForeground(Color.RED);
                    lblMensaje.setText("Por favor complete todos los campos.");
                    return;
                }

                UsuarioDTO dto = new UsuarioDTO(usuario, "", "", password, "", "", "");
                boolean loginExitoso = UsuarioController.getInstancia().loginUsuario(dto);

                if (loginExitoso) {
                    lblMensaje.setForeground(new Color(0, 128, 0));
                    lblMensaje.setText("Inicio de sesión exitoso.");

                    Timer timer = new Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            parent.setNicknameActual(usuario);
                            parent.showPanel("menuPrincipal");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    lblMensaje.setForeground(Color.RED);
                    lblMensaje.setText("Usuario o contraseña incorrectos.");
                }
            }
        });

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showPanel("register");
            }
        });
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
        lblMensaje.setText("");
    }
}
