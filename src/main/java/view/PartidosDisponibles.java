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
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        String[] columnas = {"Deporte", "Ubicación", "Fecha", "PartidoObj", "Unirse"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Solo columna "Unirse"
            }
        };

        tablaPartidos = new JTable(modeloTabla);
        tablaPartidos.getColumn("Unirse").setCellRenderer(new BotonRenderer());
        tablaPartidos.getColumn("Unirse").setCellEditor(new BotonEditor(new JCheckBox(), parent, nickname));

        // Ocultar la columna del objeto Partido (columna 3)
        tablaPartidos.getColumnModel().getColumn(3).setMinWidth(0);
        tablaPartidos.getColumnModel().getColumn(3).setMaxWidth(0);
        tablaPartidos.getColumnModel().getColumn(3).setWidth(0);

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
        System.out.println("Cargando partidos para nickname: " + nickname);

        SwingWorker<Void, Partido> worker = new SwingWorker<Void, Partido>() {
            @Override
            protected Void doInBackground() {
                System.out.println("Dentro de doInBackground() para nickname: " + nickname);
                UserRepository userRepo = new UserRepository();
                Usuario temp = userRepo.findByField("_id", nickname);
                System.out.println("Usuario obtenido: " + (temp != null ? temp.getNombre() : "null"));
                if (temp == null) return null;

                List<Partido> partidos = UsuarioController.getInstancia().getInvitaciones(temp.getIdUsuario());
                System.out.println("Cantidad de partidos invitados: " + (partidos != null ? partidos.size() : "null"));

                Set<String> partidosUnicosID = new HashSet<>();
                List<String> estadosProhibidos = Arrays.asList(
                        "Cancelado", "Armado", "EnJuego", "Finalizado", "Confirmado"
                );

                for (Partido p : partidos) {
                    if (!partidosUnicosID.contains(p.getIdPartido()) &&
                            !estadosProhibidos.contains(p.getEstadoActual().getNombreEstado())) {
                        partidosUnicosID.add(p.getIdPartido());
                        publish(p);
                    }
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
                            p,
                            "Unirse"
                    });
                }
            }
        };
        worker.execute();
    }

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
                Partido partidoSeleccionado = (Partido) modeloTabla.getValueAt(row, 3);
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
                    info.setForeground(Color.WHITE); // más legible
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

                    btnEq1.addActionListener(e -> {
                        UserRepository userRepo = new UserRepository();
                        Usuario temp = userRepo.findByField("_id", nickname);

                        boolean agregado = partidoSeleccionado.gestionarIngresoAEquipo(temp, equipo1, equipo2, "Equipo 1");
                        if (agregado) {
                            PartidoController.getInstancia().guardarPartido(partidoSeleccionado);
                            JOptionPane.showMessageDialog(button,
                                    "Te has unido al Equipo 1 del partido de " + partidoSeleccionado.getDeporte(),
                                    "Confirmación",
                                    JOptionPane.INFORMATION_MESSAGE);
                            panelUnirse.setVisible(false);
                        }
                    });

                    btnEq2.addActionListener(e -> {
                        UserRepository userRepo = new UserRepository();
                        Usuario temp = userRepo.findByField("_id", nickname);

                        boolean agregado = partidoSeleccionado.gestionarIngresoAEquipo(temp, equipo1, equipo2, "Equipo 2");
                        if (agregado) {
                            PartidoController.getInstancia().guardarPartido(partidoSeleccionado);
                            JOptionPane.showMessageDialog(button,
                                    "Te has unido al Equipo 2 del partido de " + partidoSeleccionado.getDeporte(),
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
