package view;

import controller.PartidoController;
import model.Partido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PartidosDisponibles extends JPanel {

    private JComboBox<String> comboFormaFiltrado;
    private JComboBox<String> comboDeporte;
    private JTextField txtUbicacion;
    private JComboBox<String> comboNivel;
    private DefaultTableModel modeloTabla;
    private JTable tablaPartidos;
    private JPanel panelFiltros;
    private JScrollPane scrollTabla;

    private JPanel panelCentro;
    private final static String PANTALLA_TABLA = "tabla";
    private final static String PANTALLA_FILTROS = "filtros";

    public PartidosDisponibles(Ejecucion parent) {
        setLayout(new BorderLayout());

        // Título con más espacio inferior
        JLabel lblTitulo = new JLabel("Partidos Disponibles", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla de partidos
        String[] columnas = {"Deporte", "Ubicación", "Fecha", "Nivel"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPartidos = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaPartidos);

        // Panel de filtros
        panelFiltros = new JPanel();
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        panelFiltros.setVisible(false);

        comboFormaFiltrado = new JComboBox<>(new String[]{"Por ubicación", "Por nivel", "Por deporte", "Todos"});
        comboDeporte = new JComboBox<>(new String[]{"Fútbol", "Básquet", "Tenis", "Padel", "Otro"});
        txtUbicacion = new JTextField();
        comboNivel = new JComboBox<>(new String[]{"Principiante", "Intermedio", "Avanzado"});

        panelFiltros.add(Box.createVerticalStrut(10));
        panelFiltros.add(crearFila("Forma de filtrado:", comboFormaFiltrado));
        panelFiltros.add(crearFila("Deporte:", comboDeporte));
        panelFiltros.add(crearFila("Ubicación:", txtUbicacion));
        panelFiltros.add(crearFila("Nivel:", comboNivel));
        panelFiltros.add(Box.createVerticalStrut(10));

        JButton btnAplicarFiltros = new JButton("Aplicar Filtros");
        btnAplicarFiltros.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFiltros.add(btnAplicarFiltros);
        panelFiltros.add(Box.createVerticalStrut(10));

        // Panel central con CardLayout
        panelCentro = new JPanel(new CardLayout());
        panelCentro.add(scrollTabla, PANTALLA_TABLA);
        panelCentro.add(panelFiltros, PANTALLA_FILTROS);
        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnFiltrar = new JButton("🔍 Filtrar");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnVolver);
        panelBotones.add(btnFiltrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Mostrar filtros
        btnFiltrar.addActionListener(e -> {
            CardLayout cl = (CardLayout) (panelCentro.getLayout());
            cl.show(panelCentro, PANTALLA_FILTROS);
        });

        // Aplicar filtros
        btnAplicarFiltros.addActionListener(e -> {
            String forma = (String) comboFormaFiltrado.getSelectedItem();
            String deporte = (String) comboDeporte.getSelectedItem();
            String ubicacion = txtUbicacion.getText().trim();
            String nivel = (String) comboNivel.getSelectedItem();

            cargarPartidosEnSegundoPlano(forma, deporte, ubicacion, nivel);

            CardLayout cl = (CardLayout) (panelCentro.getLayout());
            cl.show(panelCentro, PANTALLA_TABLA);
        });

        // Nuevo deporte
        comboDeporte.addActionListener(e -> {
            if ("Otro".equals(comboDeporte.getSelectedItem())) {
                String nuevoDeporte = JOptionPane.showInputDialog(this, "Ingrese el deporte:");
                if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                    comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                    comboDeporte.setSelectedItem(nuevoDeporte.trim());
                } else {
                    comboDeporte.setSelectedIndex(0);
                }
            }
        });

        // Volver
        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));

        // Carga inicial sin filtros
        cargarPartidosEnSegundoPlano("Todos", "", "", "");
    }

    private JPanel crearFila(String etiqueta, JComponent campo) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setMaximumSize(new Dimension(450, 40));
        JLabel lbl = new JLabel(etiqueta);
        lbl.setPreferredSize(new Dimension(180, 25));
        campo.setPreferredSize(new Dimension(200, 25));
        fila.add(lbl);
        fila.add(campo);
        return fila;
    }

    private void cargarPartidosEnSegundoPlano(String forma, String deporte, String ubicacion, String nivel) {
        modeloTabla.setRowCount(0);

        SwingWorker<Void, Partido> worker = new SwingWorker<Void, Partido>() {
            @Override
            protected Void doInBackground() {
                List<Partido> partidos = PartidoController.getInstancia().obtenerTodosLosPartidos();

                for (Partido p : partidos) {
                    boolean coincide = false;

                    if ("Por deporte".equals(forma)) {
                        coincide = p.getDeporte() != null && p.getDeporte().equalsIgnoreCase(deporte);
                    } else if ("Por ubicación".equals(forma)) {
                        coincide = p.getUbicacion() != null && p.getUbicacion().getCiudad() != null &&
                                   p.getUbicacion().getCiudad().toLowerCase().contains(ubicacion.toLowerCase());
                    } else if ("Por nivel".equals(forma)) {
                        //coincide = p.getNivel() != null && p.getNivel().equalsIgnoreCase(nivel);
                    } else if ("Todos".equals(forma)) {
                        coincide = true;
                    }

                    if (coincide) {
                        publish(p);
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Partido> chunks) {
                for (Partido p : chunks) {
                    modeloTabla.addRow(new Object[]{
                        p.getDeporte(),
                        p.getUbicacion() != null ? p.getUbicacion().getCiudad() : "Desconocida",
                        p.getFecha(),
                        //p.getNivel() != null ? p.getNivel() : "Desconocido"
                    });
                }
            }
        };

        worker.execute();
    }
}
