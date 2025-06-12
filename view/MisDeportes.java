package view;

import javax.swing.*;
import controller.UsuarioController;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MisDeportes extends JPanel {

    private JComboBox<String> comboDeporte;
    private JComboBox<String> comboNivel;
    private JLabel lblMensaje;
    private JTextArea areaDeportes;

    private String nickname;

    public MisDeportes(Ejecucion parent, String nickname) {
        this.nickname = nickname;
        setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);

        JLabel lblTitulo = new JLabel("Registrar o modificar Deporte");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelContenido.add(lblTitulo);

        comboDeporte = new JComboBox<>(new String[]{"Fútbol", "Básquet", "Tenis", "Padel", "Otro"});
        comboDeporte.addActionListener(e -> {
            if ("Otro".equals(comboDeporte.getSelectedItem())) {
                String nuevoDeporte = JOptionPane.showInputDialog(MisDeportes.this, "Ingrese el deporte:");
                if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                    comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                    comboDeporte.setSelectedItem(nuevoDeporte.trim());
                } else {
                    comboDeporte.setSelectedIndex(0);
                }
            }
        });
        panelContenido.add(crearFila("Deporte:", comboDeporte));

        comboNivel = new JComboBox<>(new String[]{"Principiante", "Intermedio", "Avanzado"});
        panelContenido.add(crearFila("Nivel de Juego:", comboNivel));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);
        panelContenido.add(panelBotones);

        lblMensaje = new JLabel(" ");
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelContenido.add(lblMensaje);

        areaDeportes = new JTextArea(6, 40);
        areaDeportes.setEditable(false);
        areaDeportes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaDeportes.setBorder(BorderFactory.createTitledBorder("Tus Deportes Registrados"));
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(areaDeportes);

        add(panelContenido);

        btnRegistrar.addActionListener(e -> {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText(" ");

            String deporte = (String) comboDeporte.getSelectedItem();
            String nivel = (String) comboNivel.getSelectedItem();

            if (deporte == null) deporte = "";

            if (nivel == null || nivel.trim().isEmpty()) {
                nivel = "";
            } else {
                nivel = nivel.trim().toUpperCase();
                if (!nivel.equals("PRINCIPIANTE") && !nivel.equals("INTERMEDIO") && !nivel.equals("AVANZADO")) {
                    nivel = "";
                }
            }

            UsuarioController controller = UsuarioController.getInstancia();

            if (controller.existeDeporte(nickname, deporte)) {
                String nivelActual = controller.getNivelDeporte(nickname, deporte);

                if (nivelActual.equalsIgnoreCase(nivel)) {
                    lblMensaje.setForeground(Color.BLUE);
                    lblMensaje.setText("Ese deporte ya está registrado con el mismo nivel.");
                } else {
                    boolean actualizado = controller.modificarNivelDeporte(nickname, deporte, nivel);
                    if (actualizado) {
                        lblMensaje.setForeground(new Color(0, 128, 0));
                        lblMensaje.setText("Nivel actualizado con éxito.");
                        mostrarDeportes();
                    } else {
                        lblMensaje.setForeground(Color.RED);
                        lblMensaje.setText("Error al actualizar el nivel.");
                    }
                }

            } else {
                boolean exito = controller.agregarDeporteAUsuario(nickname, deporte, nivel);
                if (exito) {
                    lblMensaje.setForeground(new Color(0, 128, 0));
                    lblMensaje.setText("Deporte modificado con éxito.");
                    mostrarDeportes();
                } else {
                    lblMensaje.setForeground(Color.RED);
                    lblMensaje.setText("No se pudo modificar el deporte.");
                }
            }
        });

        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));

        mostrarDeportes(); // Mostrar lista al iniciar
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

    private void mostrarDeportes() {
        UsuarioController controller = UsuarioController.getInstancia();
        List<String> deportes = controller.obtenerDeportesConNivel(nickname);

        StringBuilder sb = new StringBuilder();
        if (deportes == null || deportes.isEmpty()) {
            sb.append("No tienes deportes asignados.");
        } else {
            sb.append(String.format("%-20s %-15s%n", "Deporte", "Nivel"));
            sb.append("-------------------- ---------------\n");
            for (String d : deportes) {
                String[] partes = d.split(" - ");
                String deporte = partes[0];
                String nivel = partes.length > 1 ? partes[1] : "";
                sb.append(String.format("%-20s %-15s%n", deporte, nivel));
            }
        }
        areaDeportes.setText(sb.toString());
    }

    public void limpiarCampos() {
        comboDeporte.setSelectedIndex(0);
        comboNivel.setSelectedIndex(0);
        lblMensaje.setText(" ");
    }
}