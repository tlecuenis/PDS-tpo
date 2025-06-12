package view;

import javax.swing.*;
import DTO.UsuarioDTO;
import controller.UsuarioController;
import java.awt.*;
import java.awt.event.*;

public class RegisterPanel extends JPanel {

    private JTextField txtUsuario;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JTextField txtUbicacion;
    private JComboBox<String> comboDeporte;
    private JComboBox<String> comboNivel;
    private JLabel lblMensaje;  // Mensaje dentro del panel

    public RegisterPanel(Ejecucion parent) {
        setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);

        JLabel lblTitulo = new JLabel("Registro de Usuario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelContenido.add(lblTitulo);

        txtUsuario = new JTextField();
        panelContenido.add(crearFila("Usuario:", txtUsuario));

        txtEmail = new JTextField();
        panelContenido.add(crearFila("Correo:", txtEmail));

        txtPassword = new JTextField();
        panelContenido.add(crearFila("Contraseña:", txtPassword));

        txtUbicacion = new JTextField();
        panelContenido.add(crearFila("Ubicación:", txtUbicacion));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);
        panelContenido.add(panelBotones);

        // Mensaje dentro del panel (como en Login)
        lblMensaje = new JLabel(" ");
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelContenido.add(lblMensaje);

        add(panelContenido);

        btnRegistrar.addActionListener(e -> {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText(" "); // Limpiar mensaje previo

            String usuario = txtUsuario.getText().trim();
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();
            String deporte = null;
            String nivel = null;

            if (usuario.isEmpty() || email.isEmpty() || password.isEmpty() || ubicacion.isEmpty()) {
                lblMensaje.setText("Por favor complete todos los campos obligatorios.");
                return;
            }

            if (deporte == null) deporte = "";

            nivel = "";

            UsuarioDTO dto = new UsuarioDTO(usuario, usuario, email, password, ubicacion, deporte, nivel);
            boolean exito = UsuarioController.getInstancia().registrarUsuario(dto);

            if (exito) {
                lblMensaje.setForeground(new Color(0, 128, 0)); // Verde
                lblMensaje.setText("Registro exitoso.");
                parent.setNicknameActual(usuario); // <-- AÑADIR ESTO

            } else {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("No se pudo registrar el usuario (ya existe o error).");
            }
        });

        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));
    }

    private JPanel crearFila(String etiqueta, JComponent campo) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setMaximumSize(new Dimension(450, 40));
        JLabel lbl = new JLabel(etiqueta);
        lbl.setPreferredSize(new Dimension(180, 25));
        campo.setPreferredSize(new Dimension(200, 25));
        fila.add(lbl);
        fila.add(campo);
        return fila;
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtUbicacion.setText("");
        lblMensaje.setText(" ");
    }
}





