package view;

import javax.swing.*;
import java.awt.*;
import DTO.PartidoDTO;
import DTO.UsuarioDTO;

import controller.PartidoController;
import controller.UsuarioController;
import model.Geolocalizacion;
import model.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.UUID;

public class CrearPartido extends JPanel {

    private JComboBox<String> comboDeporte;
    private JSpinner spinnerJugadores;
    private JSpinner spinnerDuracion;
    private JTextField txtUbicacion;
    private JTextField txtHorario;
    private JComboBox<String> comboNivel;
    private JButton btnCrear;
    private JButton btnVolver;
    private String nicknameActual; // <- agregado

    public CrearPartido(Ejecucion parent, String nicknameActual) {
        this.nicknameActual = nicknameActual; // <- guardamos el nickname

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("Crear nuevo partido");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        // Deporte
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Deporte:"), gbc);
        gbc.gridx = 1;

        comboDeporte = new JComboBox<>(new String[] { "Fútbol", "Básquet", "Tenis", "Padel", "Otro" });
        add(comboDeporte, gbc);

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
                        comboDeporte.setSelectedIndex(0);
                    }
                }
            }
        });

        // Cantidad jugadores
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

        // Fecha y Horario
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Fecha y Horario:"), gbc);
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

        // Botón Crear
        gbc.gridy++;
        gbc.gridx = 0;
        btnCrear = new JButton("Crear Partido");
        add(btnCrear, gbc);

        // Botón Volver
        gbc.gridx = 1;
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(250, 220, 100, 30);
        add(btnVolver, gbc);

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deporte = getDeporte();
                int cantidad = getCantidadJugadores();
                int duracion = getDuracion();
                String ubicacionTexto = getUbicacion();
                String horarioTexto = getHorario();
                String nivelStr = getNivel();

                if (deporte.isEmpty() || ubicacionTexto.isEmpty() || horarioTexto.isEmpty() || nivelStr.isEmpty()) {
                    JOptionPane.showMessageDialog(CrearPartido.this, "Por favor, completá todos los campos.");
                    return;
                }

                try {
                    // Parsear fecha y hora
                    LocalDateTime fechaHora = LocalDateTime.parse(horarioTexto.replace(" ", "T"));

                    // Parsear ubicación
                    String[] partesUbicacion = ubicacionTexto.split(",");
                    if (partesUbicacion.length != 4) {
                        JOptionPane.showMessageDialog(CrearPartido.this, "Ubicación mal ingresada. Usá: latitud, longitud, varianza, ciudad");
                        return;
                    }

                    double lat = Double.parseDouble(partesUbicacion[0].trim());
                    double lng = Double.parseDouble(partesUbicacion[1].trim());
                    double var = Double.parseDouble(partesUbicacion[2].trim());
                    String ciudad = partesUbicacion[3].trim();

                    Geolocalizacion geo = new Geolocalizacion(lat, lng, var, ciudad);

                    // Nivel
                    int nivelMin = 1, nivelMax = 10;
                    switch (nivelStr.toLowerCase()) {
                        case "principiante":
                            nivelMax = 3;
                            break;
                        case "intermedio":
                            nivelMin = 4;
                            nivelMax = 7;
                            break;
                        case "avanzado":
                            nivelMin = 8;
                            break;
                    }

                    UsuarioDTO usuarioDTO = UsuarioController.getInstancia().getUsuario(nicknameActual);
                    if (usuarioDTO == null) {
                        JOptionPane.showMessageDialog(CrearPartido.this, "Error: usuario no encontrado.");
                        return;
                    }

                    // Crear instancia del modelo Usuario a partir del DTO (para poder setearlo en el controlador)
                    Usuario usuario = new Usuario(
                    	    usuarioDTO.getNickname(), // usamos el nickname como id temporal
                    	    usuarioDTO.getNombre(),
                    	    usuarioDTO.getEmail(),
                    	    usuarioDTO.getContrasena(),
                    	    null, 
                    	    null, 
                    	    null
                    	);

                    // Setear usuario logueado en el PartidoController
                    PartidoController.getInstancia().setUsuarioLogueado(usuario);

                    // Crear DTO
                    PartidoDTO dto = new PartidoDTO();
                    dto.setIdPartido(UUID.randomUUID().toString());
                    dto.setDeporte(deporte);
                    dto.setCantJugadores(cantidad);
                    dto.setDuracion(duracion);
                    dto.setFecha(fechaHora);
                    dto.setUbicacion(geo);
                    dto.setNivelJugadorMinimo(nivelMin);
                    dto.setNivelJugadorMaximo(nivelMax);
                    dto.setCreador(usuarioDTO.getNickname());               

                    PartidoController.getInstancia().crearPartido(dto);

                    JOptionPane.showMessageDialog(CrearPartido.this, "¡Partido creado con éxito!");
                    parent.showPanel("menuPrincipal");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CrearPartido.this, "Error al crear el partido: " + ex.getMessage());
                }
            }
        });
    }

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


