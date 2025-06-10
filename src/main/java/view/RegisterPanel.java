package view;

import javax.swing.*;

import DTO.UsuarioDTO;
import controller.UsuarioController;

import java.awt.event.*;

public class RegisterPanel extends JPanel {

    private JTextField txtUsuario;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JTextField txtUbicacion;
    private JComboBox<String> comboDeporte;
    private JComboBox<String> comboNivel;

    public RegisterPanel(Ejecucion parent) {
        setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 20, 100, 20);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 20, 200, 25);
        add(txtUsuario);

        JLabel lblEmail = new JLabel("Correo:");
        lblEmail.setBounds(30, 60, 100, 20);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(140, 60, 200, 25);
        add(txtEmail);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(30, 100, 100, 20);
        add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setBounds(140, 100, 200, 25);
        add(txtPassword);

        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setBounds(30, 140, 100, 20);
        add(lblUbicacion);

        txtUbicacion = new JTextField();
        txtUbicacion.setBounds(140, 140, 200, 25);
        add(txtUbicacion);

        JLabel lblDeporte = new JLabel("Deporte favorito (Opcional):");
        lblDeporte.setBounds(30, 180, 180, 20);
        add(lblDeporte);

        comboDeporte = new JComboBox<>(new String[] {"Fútbol", "Básquet", "Tenis", "Padel", "Otro" });
        comboDeporte.setBounds(210, 180, 130, 25);
        add(comboDeporte);

        comboDeporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboDeporte.getSelectedItem();
                if ("Otro".equals(seleccionado)) {
                    String nuevoDeporte = JOptionPane.showInputDialog(RegisterPanel.this, "Ingrese el deporte favorito:");
                    if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                        comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                        comboDeporte.setSelectedItem(nuevoDeporte.trim());
                    } else {
                        comboDeporte.setSelectedIndex(0);
                    }
                }
            }
        });

        JLabel lblNivel = new JLabel("Nivel (opcional):");
        lblNivel.setBounds(30, 220, 120, 20);
        add(lblNivel);

        comboNivel = new JComboBox<>();
        comboNivel.setBounds(160, 220, 180, 25);
        comboNivel.addItem("");
        comboNivel.addItem("Principiante");
        comboNivel.addItem("Intermedio");
        comboNivel.addItem("Avanzado");
        add(comboNivel);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(140, 270, 100, 30);
        add(btnRegistrar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(250, 270, 100, 30);
        add(btnVolver);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String email = txtEmail.getText().trim();
                String password = txtPassword.getText().trim();
                String ubicacion = txtUbicacion.getText().trim(); // <-- Asegurate de capturar esto también
                String deporte = (String) comboDeporte.getSelectedItem();
                String nivel = (String) comboNivel.getSelectedItem();

                if (usuario.isEmpty() || email.isEmpty() || password.isEmpty() || ubicacion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor complete todos los campos obligatorios.");
                    return;
                }

                if (nivel == null) nivel = "";
                if (deporte == null) deporte = "";

                
                UsuarioDTO dto = new UsuarioDTO(usuario, usuario, email, password, ubicacion, deporte, nivel);

                boolean exito = UsuarioController.getInstancia().registrarUsuario(dto);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Registro exitoso.");
                    parent.showPanel("menuPrincipal");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el usuario (ya existe o error).");
                }
            }
        });


        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showPanel("login");
            }
        });
    }
}





