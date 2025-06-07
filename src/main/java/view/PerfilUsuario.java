package view;

import javax.swing.*;
import controller.UsuarioController;
import DTO.UsuarioDTO;
import model.Nivel;

public class PerfilUsuario extends JPanel {

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JComboBox<String> comboDeporte;
    private JComboBox<String> comboNivel;
    private JButton btnEditar;
    private JButton btnGuardar;
    private JButton btnVolver;

    private String nickname;  // Identificador del usuario
    private UsuarioController usuarioController;

    public PerfilUsuario(Ejecucion parent, String nickname) {
        this.nickname = nickname;
        this.usuarioController = UsuarioController.getInstancia();

        setLayout(null);

        JLabel lblTitulo = new JLabel("Perfil del Usuario");
        lblTitulo.setBounds(130, 10, 200, 30);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 60, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 60, 200, 25);
        txtNombre.setEditable(false);
        add(txtNombre);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 100, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(140, 100, 200, 25);
        txtEmail.setEditable(false);
        add(txtEmail);

        JLabel lblDeporte = new JLabel("Deporte favorito:");
        lblDeporte.setBounds(30, 140, 120, 25);
        add(lblDeporte);

        comboDeporte = new JComboBox<>(new String[]{"Fútbol", "Básquet", "Tenis", "Padel", "Otro"});
        comboDeporte.setBounds(160, 140, 180, 25);
        comboDeporte.setEnabled(false);
        add(comboDeporte);

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

        JLabel lblNivel = new JLabel("Nivel:");
        lblNivel.setBounds(30, 180, 100, 25);
        add(lblNivel);

        comboNivel = new JComboBox<>(new String[]{"Principiante", "Intermedio", "Avanzado"});
        comboNivel.setBounds(140, 180, 200, 25);
        comboNivel.setEnabled(false);
        add(comboNivel);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(60, 230, 90, 30);
        add(btnEditar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(160, 230, 90, 30);
        btnGuardar.setEnabled(false);
        add(btnGuardar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(260, 230, 90, 30);
        add(btnVolver);

        // Cargar datos del usuario al iniciar
        cargarDatosUsuario();

        // Acciones
        btnEditar.addActionListener(e -> {
            txtNombre.setEditable(true);
            txtEmail.setEditable(true);
            comboDeporte.setEnabled(true);
            comboNivel.setEnabled(true);
            btnGuardar.setEnabled(true);
        });

        btnGuardar.addActionListener(e -> {
            // Crear DTO con datos editados
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String deporte = (String) comboDeporte.getSelectedItem();
            String nivel = (String) comboNivel.getSelectedItem();

            UsuarioDTO dto = new UsuarioDTO(nickname, nombre, email, "", deporte, nivel);

            // Llamar al controlador para actualizar
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

        btnVolver.addActionListener(e -> {
            parent.showPanel("menuPrincipal");
        });
    }

    private void cargarDatosUsuario() {
        // Pedimos al controlador el usuario por nickname
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

