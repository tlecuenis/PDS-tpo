package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PartidosDisponibles extends JPanel {

    private JComboBox<String> comboDeporte;
    private JTextField txtUbicacion;
    private JTextField txtFecha;
    private JComboBox<String> comboNivel;

    public PartidosDisponibles(Ejecucion parent) {
        setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);

        JLabel lblTitulo = new JLabel("Buscar Partido");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelContenido.add(lblTitulo);

        // Campo Ubicación
        txtUbicacion = new JTextField();
        txtUbicacion.setPreferredSize(new Dimension(200, 25));
        panelContenido.add(crearFila("Ubicación:", txtUbicacion));

        // Campo Fecha
        txtFecha = new JTextField();
        txtFecha.setPreferredSize(new Dimension(200, 25));
        panelContenido.add(crearFila("Fecha (dd/mm/yyyy):", txtFecha));

        // Combo Deporte
        comboDeporte = new JComboBox<>(new String[]{"Fútbol", "Básquet", "Tenis", "Padel", "Otro"});
        panelContenido.add(crearFila("Deporte favorito (opcional):", comboDeporte));

        // Combo Nivel
        comboNivel = new JComboBox<>(new String[]{"Principiante", "Intermedio", "Avanzado"});
        panelContenido.add(crearFila("Nivel:", comboNivel));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnBuscar = new JButton("Buscar");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnBuscar);
        panelBotones.add(btnVolver);
        panelContenido.add(panelBotones);

        // Agregar panel contenido al centro
        add(panelContenido);

        // Listener para permitir ingresar un deporte personalizado
        comboDeporte.addActionListener(e -> {
            String seleccionado = (String) comboDeporte.getSelectedItem();
            if ("Otro".equals(seleccionado)) {
                String nuevoDeporte = JOptionPane.showInputDialog(this, "Ingrese el deporte favorito:");
                if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                    comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                    comboDeporte.setSelectedItem(nuevoDeporte.trim());
                } else {
                    comboDeporte.setSelectedIndex(0);
                }
            }
        });

        btnBuscar.addActionListener(e -> {
            String deporte = (String) comboDeporte.getSelectedItem();
            String ubicacion = txtUbicacion.getText().trim();
            String fecha = txtFecha.getText().trim();
            String nivel = (String) comboNivel.getSelectedItem();

            JOptionPane.showMessageDialog(this, "Buscando partidos de " + deporte +
                    " en " + ubicacion + " el " + fecha + " (Nivel: " + nivel + ")");
        });

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

