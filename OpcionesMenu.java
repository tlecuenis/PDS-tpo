package view;

import javax.swing.*;
import java.awt.*;

public class OpcionesMenu extends JPanel {

    public OpcionesMenu(Ejecucion parent) {
        setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);

        JLabel lblTitulo = new JLabel("Menú Principal");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panelContenido.add(lblTitulo);

        JButton btnVerPartidos = new JButton("Ver Partidos Disponibles");
        JButton btnCrearPartido = new JButton("Crear Partido");
        JButton btnPerfil = new JButton("Ver Perfil");
        JButton btnNotificaciones = new JButton("Notificaciones");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        for (JButton btn : new JButton[]{btnVerPartidos, btnCrearPartido, btnPerfil, btnNotificaciones, btnCerrarSesion}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(220, 30));
            btn.setPreferredSize(new Dimension(220, 30));
            btn.setMinimumSize(new Dimension(220, 30));
            panelContenido.add(Box.createVerticalStrut(10));
            panelContenido.add(btn);
        }

        // Acciones
        btnVerPartidos.addActionListener(e -> parent.showPanel("listaPartidos"));
        btnCrearPartido.addActionListener(e -> parent.showPanel("crearPartido"));
        btnPerfil.addActionListener(e -> parent.showPanel("perfil"));
        btnNotificaciones.addActionListener(e -> parent.showPanel("notificaciones"));
        btnCerrarSesion.addActionListener(e -> {
            LoginPanel loginPanel = parent.getLoginPanel(); // Obtener la referencia
            loginPanel.limpiarCampos(); // Limpiar campos antes de mostrar el panel
            parent.showPanel("login");
        });

        // Agregar contenido centrado
        add(panelContenido);
    }
}

