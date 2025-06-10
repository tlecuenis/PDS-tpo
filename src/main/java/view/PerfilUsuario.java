package view;

import javax.swing.*;
import controller.UsuarioController;
import DTO.UsuarioDTO;

import java.awt.*;

public class PerfilUsuario extends JPanel {

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JComboBox<String> comboDeporte;
    private JComboBox<String> comboNivel;
    private JButton btnEditar;
    private JButton btnGuardar;
    private JButton btnVolver;

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

        // Campo Nombre
        txtNombre = new JTextField();
        txtNombre.setEditable(false);
        panelContenido.add(crearFila("Nombre:", txtNombre));

        // Campo Email
        txtEmail = new JTextField();
        txtEmail.setEditable(false);
        panelContenido.add(crearFila("Email:", txtEmail));

        // Combo Deporte
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

        // Combo Nivel
        comboNivel = new JComboBox<>(new String[]{"Principiante", "Intermedio", "Avanzado"});
        comboNivel.setEnabled(false);
        panelContenido.add(crearFila("Nivel:", comboNivel));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnEditar = new JButton("Editar");
        btnGuardar = new JButton("Guardar");
        btnGuardar.setEnabled(false);
        btnVolver = new JButton("Volver");
        panelBotones.add(btnEditar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        panelContenido.add(panelBotones);

        add(panelContenido); // lo agregás centrado

        // Eventos
        btnEditar.addActionListener(e -> {
            txtNombre.setEditable(true);
            txtEmail.setEditable(true);
            comboDeporte.setEnabled(true);
            comboNivel.setEnabled(true);
            btnGuardar.setEnabled(true);
        });

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String deporte = (String) comboDeporte.getSelectedItem();
            String nivel = (String) comboNivel.getSelectedItem();

            UsuarioDTO dto = new UsuarioDTO(nickname, nombre, email, "", deporte, nivel);

            boolean exito = usuarioController.actualizarUsuario(dto);

            if (exito) {
                txtNombre.setEditable(false);
                txtEmail.setEditable(false);
                comboDeporte.setEnabled(false);
                comboNivel.setEnabled(false);
                btnGuardar.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Datos guardados exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar los datos.");
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
            if (!usuario.getDeportes().isEmpty()) {
                comboDeporte.setSelectedItem(usuario.getDeportes().get(0).getNombre());
                comboNivel.setSelectedItem(usuario.getDeportes().get(0).getNivelJuego().toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
        }
    }
}

