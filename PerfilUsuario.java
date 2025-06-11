package view;

import javax.swing.*;
import controller.UsuarioController;
import model.Nivel;
import DTO.UsuarioDTO;

import java.awt.*;

public class PerfilUsuario extends JPanel {

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtCiudad;
    private JComboBox<String> comboDeporte;
    private JComboBox<String> comboNivel;
    private JButton btnEditar;
    private JButton btnGuardar;
    private JButton btnVolver;
    private JLabel lblMensaje;

    private String nickname;
    private UsuarioController usuarioController;

    public PerfilUsuario(Ejecucion parent, String nickname) {
        this.nickname = nickname;
        this.usuarioController = UsuarioController.getInstancia();

        setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);

        JLabel lblTitulo = new JLabel("Perfil del Usuario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelContenido.add(lblTitulo);

        txtNombre = new JTextField();
        txtNombre.setEditable(false);
        panelContenido.add(crearFila("Nombre:", txtNombre));

        txtEmail = new JTextField();
        txtEmail.setEditable(false);
        panelContenido.add(crearFila("Email:", txtEmail));
        
        txtCiudad = new JTextField();
        txtCiudad.setEditable(false);
        panelContenido.add(crearFila("Ciudad:", txtCiudad));

        comboDeporte = new JComboBox<>(new String[]{"Fútbol", "Básquet", "Tenis", "Padel", "Otro"});
        comboDeporte.setEnabled(false);
        panelContenido.add(crearFila("Deporte favorito:", comboDeporte));

        comboDeporte.addActionListener(e -> {
            if ("Otro".equals(comboDeporte.getSelectedItem()) && comboDeporte.isEnabled()) {
                String nuevoDeporte = JOptionPane.showInputDialog(
                        PerfilUsuario.this, "Ingrese su deporte favorito:");
                if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                    comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                    comboDeporte.setSelectedItem(nuevoDeporte.trim());
                } else {
                    comboDeporte.setSelectedIndex(0);
                }
            }
        });

        comboNivel = new JComboBox<>(new String[]{"Principiante", "Intermedio", "Avanzado"});
        comboNivel.setEnabled(false);
        panelContenido.add(crearFila("Nivel:", comboNivel));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnEditar = new JButton("Editar");
        btnGuardar = new JButton("Guardar");
        btnGuardar.setEnabled(false);
        btnVolver = new JButton("Volver");
        panelBotones.add(btnEditar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        panelContenido.add(panelBotones);

        // Label de mensajes
        lblMensaje = new JLabel("");
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelContenido.add(lblMensaje);

        add(panelContenido); 

        btnEditar.addActionListener(e -> {
            txtNombre.setEditable(true);
            txtEmail.setEditable(true);
            txtCiudad.setEditable(true);
            comboDeporte.setEnabled(true);
            comboNivel.setEnabled(true);
            btnGuardar.setEnabled(true);
            lblMensaje.setText("");
        });

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String deporte = (String) comboDeporte.getSelectedItem();
            String nivel = (String) comboNivel.getSelectedItem();

            UsuarioDTO dto = new UsuarioDTO(nickname, nombre, email, "", ciudad, deporte, nivel);

            boolean exito = usuarioController.actualizarUsuario(dto);

            if (exito) {
                txtNombre.setEditable(false);
                txtEmail.setEditable(false);
                txtCiudad.setEditable(false);
                comboDeporte.setEnabled(false);
                comboNivel.setEnabled(false);
                btnGuardar.setEnabled(false);

                lblMensaje.setForeground(new Color(0, 128, 0));
                lblMensaje.setText("Datos guardados exitosamente.");
            } else {
                lblMensaje.setForeground(Color.RED);
                lblMensaje.setText("Error al guardar los datos.");
            }
        });

        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));

        cargarDatosUsuario();
    }

    private JPanel crearFila(String etiqueta, JComponent campo) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setMaximumSize(new Dimension(450, 40));
        JLabel lbl = new JLabel(etiqueta);
        lbl.setPreferredSize(new Dimension(140, 25));
        campo.setPreferredSize(new Dimension(200, 25));
        fila.add(lbl);
        fila.add(campo);
        return fila;
    }

    private void cargarDatosUsuario() {
        model.Usuario usuario = usuarioController.getUserById(nickname);
        if (usuario != null) {
            txtNombre.setText(usuario.getNombre());
            txtEmail.setText(usuario.getEmail());
            txtCiudad.setText(usuario.getUbicacion().getCiudad());

            if (!usuario.getDeportes().isEmpty()) {
                Nivel nivelEnum = usuario.getDeportes().get(0).getNivelJuego();
                String nivelNormalizado = nivelEnum.name().toLowerCase();

                switch (nivelNormalizado) {
                    case "principiante":
                        comboNivel.setSelectedItem("Principiante");
                        break;
                    case "intermedio":
                        comboNivel.setSelectedItem("Intermedio");
                        break;
                    case "avanzado":
                        comboNivel.setSelectedItem("Avanzado");
                        break;
                    default:
                        comboNivel.setSelectedIndex(0);
                        break;
                }
            }
        } else {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("Usuario no encontrado.");
        }
    }
}
