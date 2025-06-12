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

        comboDeporte = new JComboBox<>(new String[] {"Fútbol", "Básquet", "Tenis", "Padel", "Otro" });
        comboDeporte.addActionListener(e -> {
            if ("Otro".equals(comboDeporte.getSelectedItem())) {
                String nuevoDeporte = JOptionPane.showInputDialog(RegisterPanel.this, "Ingrese el deporte favorito:");
                if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                    comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                    comboDeporte.setSelectedItem(nuevoDeporte.trim());
                } else {
                    comboDeporte.setSelectedIndex(0);
                }
            }
        });
        panelContenido.add(crearFila("Deporte favorito (opcional):", comboDeporte));

        comboNivel = new JComboBox<>(new String[] {"", "Principiante", "Intermedio", "Avanzado"});
        panelContenido.add(crearFila("Nivel (opcional):", comboNivel));

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
            String deporte = (String) comboDeporte.getSelectedItem();
            String nivel = (String) comboNivel.getSelectedItem();

            if (usuario.isEmpty() || email.isEmpty() || password.isEmpty() || ubicacion.isEmpty()) {
                lblMensaje.setText("Por favor complete todos los campos obligatorios.");
                return;
            }

            if (deporte == null) deporte = "";

            if (nivel == null || nivel.trim().isEmpty()) {
                nivel = "";
            } else {
                nivel = nivel.trim().toUpperCase();
                if (!nivel.equals("PRINCIPIANTE") && !nivel.equals("INTERMEDIO") && !nivel.equals("AVANZADO")) {
                    nivel = "";
                }
            }

            UsuarioDTO dto = new UsuarioDTO(usuario, usuario, email, password, ubicacion, deporte, nivel);
            boolean exito = UsuarioController.getInstancia().registrarUsuario(dto);

            if (exito) {
                lblMensaje.setForeground(new Color(0, 128, 0)); // Verde
                lblMensaje.setText("Registro exitoso.");
                parent.setNicknameActual(usuario); // <-- AÑADIR ESTO

                // Espera 1 segundos y redirige
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        parent.showPanel("menuPrincipal");
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("No se pudo registrar el usuario (ya existe o error).");
            }
        });

        btnVolver.addActionListener(e -> parent.showPanel("login"));
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
        comboDeporte.setSelectedIndex(0);
        comboNivel.setSelectedIndex(0);
        lblMensaje.setText(" ");
    }
}





