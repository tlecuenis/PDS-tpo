package view;

import controller.UsuarioController;
import controller.PartidoController;
import model.Equipo;
import model.Partido;
import model.Usuario;
import repository.UserRepository;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class PartidosDisponibles extends JPanel {

    private DefaultTableModel modeloTabla;
    private JTable tablaPartidos;
    private JScrollPane scrollTabla;
    private JPanel panelUnirse;

    public PartidosDisponibles(Ejecucion parent, String nickname) {
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Partidos Disponibles", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));
        add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Deporte", "Ubicación", "Fecha", "Unirse"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo columna "Unirse"
            }
        };

        tablaPartidos = new JTable(modeloTabla);
        tablaPartidos.getColumn("Unirse").setCellRenderer(new BotonRenderer());
        tablaPartidos.getColumn("Unirse").setCellEditor(new BotonEditor(new JCheckBox(), parent, nickname));

        scrollTabla = new JScrollPane(tablaPartidos);

        panelUnirse = new JPanel();
        panelUnirse.setLayout(new BoxLayout(panelUnirse, BoxLayout.Y_AXIS));
        panelUnirse.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelUnirse.setVisible(false);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(scrollTabla, BorderLayout.CENTER);
        panelCentral.add(panelUnirse, BorderLayout.SOUTH);
        add(panelCentral, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPartidos(nickname);
    }

    private void cargarPartidos(String nickname) {
        modeloTabla.setRowCount(0);

        // Debug: inicio de carga de partidos
        System.out.println("Cargando partidos para nickname: " + nickname);

        SwingWorker<Void, Partido> worker = new SwingWorker<Void, Partido>() {
            @Override
            protected Void doInBackground() {
                // Debug: dentro de doInBackground
                System.out.println("Dentro de doInBackground() para nickname: " + nickname);
                UserRepository userRepo = new UserRepository();
                Usuario temp = userRepo.findByField("_id", nickname);
                // Debug: usuario obtenido
                System.out.println("Usuario obtenido: " + (temp != null ? temp.getNombre() : "null"));
                if (temp == null) {
                    System.out.println("No se encontró el usuario con nickname: " + nickname);
                    return null;
                }

                List<Partido> partidos = UsuarioController.getInstancia().getInvitaciones(temp.getIdUsuario());
                // Debug: id de usuario y cantidad de partidos invitados
                System.out.println("ID de usuario: " + temp.getIdUsuario());
                System.out.println("Cantidad de partidos invitados: " + (partidos != null ? partidos.size() : "null"));
                for (Partido p : partidos) {
                    System.out.println("Partido recibido: " + p.getDeporte() + " en " +
                        (p.getUbicacion() != null ? p.getUbicacion().getCiudad() : "null"));
                    publish(p);
                }
                return null;
            }

            @Override
            protected void process(List<Partido> partidos) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy - H:mm");
                for (Partido p : partidos) {
                    String fechaFormateada = p.getFecha().format(formatter);
                    modeloTabla.addRow(new Object[]{
                        p.getDeporte(),
                        p.getUbicacion() != null ? p.getUbicacion().getCiudad() : "Desconocida",
                        fechaFormateada,
                        "Unirse"
                    });
                }
            }
        };
        worker.execute();
    }

    // ---------- Renderer del botón ----------
    class BotonRenderer extends JButton implements TableCellRenderer {
        public BotonRenderer() {
            setText("Unirse");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    // ---------- Editor del botón ----------
    class BotonEditor extends DefaultCellEditor {
        private JButton button;
        private String nickname;
        private boolean clicked;
        private int row;
        private Ejecucion parent;

        public BotonEditor(JCheckBox checkBox, Ejecucion parent, String nickname) {
            super(checkBox);
            this.parent = parent;
            this.nickname = nickname;
            button = new JButton("Unirse");
            button.setOpaque(true);

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                String deporte = (String) modeloTabla.getValueAt(row, 0);
                String ciudad = (String) modeloTabla.getValueAt(row, 1);
                String fecha = modeloTabla.getValueAt(row, 2).toString();

                // Obtener partido desde los datos de la fila
                UserRepository userRepo = new UserRepository();
                Usuario temp = userRepo.findByField("_id", nickname);
                List<Partido> partidos = UsuarioController.getInstancia().getInvitaciones(temp.getIdUsuario());
                System.out.println("Invitaciones recibidas: " + (partidos != null ? partidos.size() : "null"));
                Partido partidoSeleccionado = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy - H:mm");
                for (Partido p : partidos) {
                    String fechaPartidoFormateada = p.getFecha().format(formatter);
                    if (p.getDeporte().equals(deporte)
                            && (p.getUbicacion() != null && ciudad.equals(p.getUbicacion().getCiudad()))
                            && fecha.equals(fechaPartidoFormateada)) {
                        partidoSeleccionado = p;
                        break;
                    }
                }

                if (partidoSeleccionado != null) {
                    Equipo equipo1 = partidoSeleccionado.getEquipo("Equipo 1");
                    Equipo equipo2 = partidoSeleccionado.getEquipo("Equipo 2");

                    panelUnirse.removeAll();
                    panelUnirse.setVisible(true);

                    JTextArea info = new JTextArea();
                    info.setEditable(false);
                    StringBuilder mensaje = new StringBuilder();
                    mensaje.append("Equipo 1:\n");

                    if (equipo1 != null && equipo1.getJugadores() != null && !equipo1.getJugadores().isEmpty()) {
                        for (Usuario jugador : equipo1.getJugadores()) {
                            mensaje.append("- ").append(jugador.getNombre()).append("\n");
                        }
                    } else {
                        mensaje.append("(sin jugadores)\n");
                    }

                    mensaje.append("\nEquipo 2:\n");

                    if (equipo2 != null && equipo2.getJugadores() != null && !equipo2.getJugadores().isEmpty()) {
                        for (Usuario jugador : equipo2.getJugadores()) {
                            mensaje.append("- ").append(jugador.getNombre()).append("\n");
                        }
                    } else {
                        mensaje.append("(sin jugadores)\n");
                    }

                    info.setText(mensaje.toString());
                    info.setAlignmentX(Component.CENTER_ALIGNMENT);
                    info.setMaximumSize(new Dimension(400, 100));
                    info.setForeground(Color.WHITE);
                    JPanel infoPanel = new JPanel();
                    infoPanel.setOpaque(false);
                    infoPanel.add(info);
                    panelUnirse.add(infoPanel);

                    JPanel botones = new JPanel();
                    botones.setAlignmentX(Component.CENTER_ALIGNMENT);
                    botones.setLayout(new FlowLayout(FlowLayout.CENTER));
                    botones.setOpaque(false);
                    JButton btnEq1 = new JButton("Unirse al Equipo 1");
                    JButton btnEq2 = new JButton("Unirse al Equipo 2");

                    Partido finalPartidoSeleccionado = partidoSeleccionado;
                    btnEq1.addActionListener(e -> {
                        boolean agregado = finalPartidoSeleccionado.gestionarIngresoAEquipo(temp, equipo1, equipo2, "Equipo 1");
                        if (agregado) {
                            PartidoController.getInstancia().guardarPartido(finalPartidoSeleccionado);
                            JOptionPane.showMessageDialog(button,
                                "Te has unido al Equipo 1 del partido de " + deporte,
                                "Confirmación",
                                JOptionPane.INFORMATION_MESSAGE);
                            panelUnirse.setVisible(false);
                        }
                    });

                    Partido finalPartidoSeleccionado1 = partidoSeleccionado;
                    btnEq2.addActionListener(e -> {
                        boolean agregado = finalPartidoSeleccionado1.gestionarIngresoAEquipo(temp, equipo1, equipo2, "Equipo 2");
                        if (agregado) {
                            PartidoController.getInstancia().guardarPartido(finalPartidoSeleccionado1);
                            JOptionPane.showMessageDialog(button,
                                "Te has unido al Equipo 2 del partido de " + deporte,
                                "Confirmación",
                                JOptionPane.INFORMATION_MESSAGE);
                            panelUnirse.setVisible(false);
                        }
                    });

                    botones.add(btnEq1);
                    botones.add(btnEq2);
                    panelUnirse.add(botones, BorderLayout.SOUTH);

                    PartidosDisponibles.this.revalidate();
                    PartidosDisponibles.this.repaint();
                }
            }
            clicked = false;
            return "Unirse";
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}
