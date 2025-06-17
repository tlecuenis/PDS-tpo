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

public class PartidosDisponibles extends JPanel {

    private DefaultTableModel modeloTabla;
    private JTable tablaPartidos;
    private JScrollPane scrollTabla;

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
        add(scrollTabla, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPartidos(nickname);
    }

    private void cargarPartidos(String nickname) {
        modeloTabla.setRowCount(0);

        SwingWorker<Void, Partido> worker = new SwingWorker<Void, Partido>() {
            @Override
            protected Void doInBackground() {
                UserRepository userRepo = new UserRepository();
                Usuario temp = userRepo.findByField("_id", nickname);
                List<Partido> partidos = UsuarioController.getInstancia().getInvitaciones(temp.getIdUsuario());

                for (Partido p : partidos) {
                    modeloTabla.addRow(new Object[]{
                        p.getDeporte(),
                        p.getUbicacion() != null ? p.getUbicacion().getCiudad() : "Desconocida",
                        p.getFecha(),
                        "Unirse" // botón
                    });
                }
                return null;
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

                Partido partidoSeleccionado = null;
                for (Partido p : partidos) {
                    if (p.getDeporte().equals(deporte)
                            && (p.getUbicacion() != null && ciudad.equals(p.getUbicacion().getCiudad()))
                            && fecha.equals(p.getFecha().toString())) {
                        partidoSeleccionado = p;
                        break;
                    }
                }

                if (partidoSeleccionado != null) {
                    Equipo equipo1 = partidoSeleccionado.getEquipo("Equipo 1");
                    Equipo equipo2 = partidoSeleccionado.getEquipo("Equipo 2");

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

                    String[] opciones = {"Unirse al Equipo 1", "Unirse al Equipo 2"};
                    int seleccion = JOptionPane.showOptionDialog(
                            button,
                            mensaje.toString(),
                            "Unirse al Partido",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[0]
                    );

                    if (seleccion == 0 || seleccion == 1) {
                        String nombreEquipo = seleccion == 0 ? "Equipo 1" : "Equipo 2";
                        Usuario jugador = temp; // El usuario logueado

                        partidoSeleccionado.añadirAlEquipo(jugador, nombreEquipo);
                        PartidoController.getInstancia().guardarPartido(partidoSeleccionado);

                        JOptionPane.showMessageDialog(button,
                            "Te has unido al " + nombreEquipo + " del partido de " + deporte,
                            "Confirmación",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
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
