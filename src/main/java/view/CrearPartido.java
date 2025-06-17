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
    private JComboBox<String> comboNivelMin;
    private JComboBox<String> comboNivelMax;
    private JButton btnCrear;
    private JButton btnVolver;
    private String nicknameActual; 

    public CrearPartido(Ejecucion parent, String nicknameActual) {
        this.nicknameActual = nicknameActual; 

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

        // Nivel Mínimo
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Nivel Mínimo:"), gbc);
        gbc.gridx = 1;
        comboNivelMin = new JComboBox<>(new String[] { "", "Principiante", "Intermedio", "Avanzado" });
        add(comboNivelMin, gbc);
        
        // Nivel Maximo
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Nivel Maximo:"), gbc);
        gbc.gridx = 1;
        comboNivelMax = new JComboBox<>(new String[] { "", "Principiante", "Intermedio", "Avanzado" });
        add(comboNivelMax, gbc);

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

        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));
        
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deporte = getDeporte();
                int cantidad = getCantidadJugadores();
                int duracion = getDuracion();
                String ubicacionTexto = getUbicacion();
                String horarioTexto = getHorario();
                String nivelMin = getNivelMin();
                String nivelMax = getNivelMax();

                if (deporte.isEmpty() || ubicacionTexto.isEmpty() || horarioTexto.isEmpty() || nivelMin.isEmpty() || nivelMax.isEmpty()) {
                    JOptionPane.showMessageDialog(CrearPartido.this, "Por favor, completá todos los campos.");
                    return;
                }

                try {
                    // Parsear fecha y hora
                    LocalDateTime fechaHora = LocalDateTime.parse(horarioTexto.replace(" ", "T"));

                    // Parsear ubicación
                    String ciudad = ubicacionTexto;
 
                    Geolocalizacion geo = new Geolocalizacion(1.3, 2.5, 7.6, ciudad); //mas trucho esto

                    // Nivel
                    int min = 0;
                    int max = 0;

                    switch (nivelMin.toLowerCase()) {
                        case "principiante":
                            min = 1;
                            break;
                        case "intermedio":
                            min = 2;
                            break;
                        case "avanzado":
                            min = 3;
                            break;
                    }

                    switch (nivelMax.toLowerCase()) {
                        case "principiante":
                            max = 1;
                            break;
                        case "intermedio":
                            max = 2;
                            break;
                        case "avanzado":
                            max = 3;
                            break;
                    }

                    if (min > max) {
                        JOptionPane.showMessageDialog(CrearPartido.this, "Error: El nivel máximo no puede ser menor que el mínimo.");
                        return;
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
                    dto.setNivelJugadorMinimo(min);
                    dto.setNivelJugadorMaximo(max);
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

    public String getNivelMin() {
        return (String) comboNivelMin.getSelectedItem();
    }

    public String getNivelMax() {
        return (String) comboNivelMax.getSelectedItem();
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
}


