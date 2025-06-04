package view;

import javax.swing.*;
import java.awt.event.*;

public class RegisterPanel extends JPanel {

    private JTextField txtUsuario;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JComboBox<String> comboDeporte;
    private JComboBox<String> comboNivel;

    public RegisterPanel(MenuPrincipal parent) {
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

        JLabel lblDeporte = new JLabel("Deporte favorito (Opcional):");
        lblDeporte.setBounds(30, 140, 180, 20);
        add(lblDeporte);

        comboDeporte = new JComboBox<>(new String[] {"Fútbol", "Básquet", "Tenis", "Padel", "Otro" });
        comboDeporte.setBounds(210, 140, 130, 25);
        add(comboDeporte);

        // Listener para permitir ingresar un deporte personalizado
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
                        comboDeporte.setSelectedIndex(0); // vuelve al valor vacío
                    }
                }
            }
        });

        JLabel lblNivel = new JLabel("Nivel (opcional):");
        lblNivel.setBounds(30, 180, 120, 20);
        add(lblNivel);

        comboNivel = new JComboBox<>();
        comboNivel.setBounds(160, 180, 180, 25);
        comboNivel.addItem("");
        comboNivel.addItem("Principiante");
        comboNivel.addItem("Intermedio");
        comboNivel.addItem("Avanzado");
        add(comboNivel);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(140, 230, 100, 30);
        add(btnRegistrar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(250, 230, 100, 30);
        add(btnVolver);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String email = txtEmail.getText().trim();
                String password = txtPassword.getText().trim();
                String deporte = (String) comboDeporte.getSelectedItem();
                String nivel = (String) comboNivel.getSelectedItem();

                if (usuario.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor complete todos los campos obligatorios.");
                    return;
                }

                // Guardar los datos (simulado con impresión)
                System.out.println("Usuario: " + usuario);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
                System.out.println("Deporte favorito: " + deporte);
                if (!nivel.isEmpty()) {
                    System.out.println("Nivel: " + nivel);
                }

                JOptionPane.showMessageDialog(null, "Registro exitoso.");
                parent.showPanel("menuPrincipal");
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showPanel("login");
            }
        });
    }
}




