package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PartidosDisponibles extends JPanel {

    private JComboBox<String> comboFormaFiltrado;
    private JComboBox<String> comboDeporte;
    private JTextField txtUbicacion;
    private JComboBox<String> comboNivel;
    private DefaultTableModel modeloTabla;
    private JTable tablaPartidos;
    private JPanel panelFiltros;
    private JScrollPane scrollTabla;

    public PartidosDisponibles(Ejecucion parent) {
        setLayout(new BorderLayout());

        // Título
        JLabel lblTitulo = new JLabel("Partidos Disponibles", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de tabla
        String[] columnas = {"Deporte", "Ubicación", "Fecha", "Nivel"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPartidos = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaPartidos);
        add(scrollTabla, BorderLayout.CENTER);

        // Panel de filtros (inicialmente oculto)
        panelFiltros = new JPanel();
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        panelFiltros.setVisible(false);

        comboFormaFiltrado = new JComboBox<>(new String[]{"Por ubicación", "Por nivel", "Por deporte", "Todos"});
        comboDeporte = new JComboBox<>(new String[]{"Fútbol", "Básquet", "Tenis", "Padel", "Otro"});
        txtUbicacion = new JTextField();
        comboNivel = new JComboBox<>(new String[]{"Principiante", "Intermedio", "Avanzado"});

        panelFiltros.add(crearFila("Forma de filtrado:", comboFormaFiltrado));
        panelFiltros.add(crearFila("Deporte:", comboDeporte));
        panelFiltros.add(crearFila("Ubicación:", txtUbicacion));
        panelFiltros.add(crearFila("Nivel:", comboNivel));

        // Botón de aplicar filtros
        JButton btnAplicarFiltros = new JButton("Aplicar Filtros");
        btnAplicarFiltros.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFiltros.add(Box.createVerticalStrut(10));
        panelFiltros.add(btnAplicarFiltros);

        add(panelFiltros, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnFiltrar = new JButton("🔍 Filtrar");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnVolver);
        panelBotones.add(btnFiltrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción de "Filtrar" - muestra el panel de filtros y oculta la tabla
        btnFiltrar.addActionListener(e -> {
            scrollTabla.setVisible(false);
            panelFiltros.setVisible(true);
            revalidate();
            repaint();
        });

        // Acción de "Aplicar filtros" - simula filtrado y vuelve a la tabla
        btnAplicarFiltros.addActionListener(e -> {
            String forma = (String) comboFormaFiltrado.getSelectedItem();
            String deporte = (String) comboDeporte.getSelectedItem();
            String ubicacion = txtUbicacion.getText().trim();
            String nivel = (String) comboNivel.getSelectedItem();

            // Limpiar y simular resultado
            modeloTabla.setRowCount(0);
            modeloTabla.addRow(new Object[]{
                    deporte,
                    ubicacion.isEmpty() ? "Córdoba" : ubicacion,
                    "12/06/2025",
                    nivel
            });

            // Ocultar filtros, mostrar tabla
            panelFiltros.setVisible(false);
            scrollTabla.setVisible(true);
            revalidate();
            repaint();
        });

        // Acción de "Otro" en deporte
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

        // Acción de "Volver"
        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));
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
}
