package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearPartido extends JPanel {

    private JComboBox<String> comboDeporte;
    private JSpinner spinnerJugadores;
    private JSpinner spinnerDuracion;
    private JTextField txtUbicacion;
    private JTextField txtHorario;
    private JComboBox<String> comboNivel;
    private JButton btnCrear;
    private JButton btnVolver;

    public CrearPartido() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configuración general
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado interno
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("Crear nuevo partido");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, gbc);

        // Labels y campos (una fila por cada uno)
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        // Deporte
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Deporte:"), gbc);
        gbc.gridx = 1;
        
        // En el constructor CrearPartido()
        comboDeporte = new JComboBox<>(new String[] { "Fútbol", "Básquet", "Tenis", "Padel", "Otro" });
        add(comboDeporte, gbc);

        // Listener para permitir ingresar un deporte personalizado si elige "Otro"
        comboDeporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboDeporte.getSelectedItem();
                if ("Otro".equals(seleccionado)) {
                    String nuevoDeporte = JOptionPane.showInputDialog(CrearPartido.this, "Ingrese el nombre del deporte:");
                    if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                        comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                        comboDeporte.setSelectedItem(nuevoDeporte.trim());
                    } else {
                        // Si cancela o deja vacío, vuelve al primer ítem
                        comboDeporte.setSelectedIndex(0);
                    }
                }
            }
        });


        // Cantidad de jugadores
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Cantidad jugadores:"), gbc);
        gbc.gridx = 1;
        spinnerJugadores = new JSpinner(new SpinnerNumberModel(2, 1, 50, 1));
        add(spinnerJugadores, gbc);

        // Duración
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        spinnerDuracion = new JSpinner(new SpinnerNumberModel(60, 15, 300, 15));
        add(spinnerDuracion, gbc);

        // Ubicación
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        txtUbicacion = new JTextField();
        add(txtUbicacion, gbc);

        // Horario
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Horario:"), gbc);
        gbc.gridx = 1;
        txtHorario = new JTextField("Ej: 2025-06-04 18:30");
        add(txtHorario, gbc);

        // Nivel
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Nivel:"), gbc);
        gbc.gridx = 1;
        comboNivel = new JComboBox<>(new String[] { "", "Principiante", "Intermedio", "Avanzado" });
        add(comboNivel, gbc);

        // Botones
        gbc.gridy++;
        gbc.gridx = 0;
        btnCrear = new JButton("Crear Partido");
        add(btnCrear, gbc);

        gbc.gridx = 1;
        btnVolver = new JButton("Volver");
        add(btnVolver, gbc);
    }

    // Getters para el controlador
    public String getDeporte() {
        return (String) comboDeporte.getSelectedItem();
    }

    public int getCantidadJugadores() {
        return (Integer) spinnerJugadores.getValue();
    }

    public int getDuracion() {
        return (Integer) spinnerDuracion.getValue();
    }

    public String getUbicacion() {
        return txtUbicacion.getText();
    }

    public String getHorario() {
        return txtHorario.getText();
    }

    public String getNivel() {
        return (String) comboNivel.getSelectedItem();
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
}


