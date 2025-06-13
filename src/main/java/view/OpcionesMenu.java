package view;

import javax.swing.*;
import controller.UsuarioController;
import model.Usuario;
import java.awt.*;

public class OpcionesMenu extends JPanel {

    private JLabel lblTitulo;
    private String nicknameActual;

    public OpcionesMenu(Ejecucion parent) {
        setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);

        // ✅ Título con nombre dinámico (lo agregaste pero lo habías quitado)
        lblTitulo = new JLabel("Bienvenido");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelContenido.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Menú Principal");
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panelContenido.add(lblSubtitulo);

        // Botones
        JButton btnVerPartidos = new JButton("Ver Partidos Disponibles");
        JButton btnCrearPartido = new JButton("Crear Partido");
        JButton btnMisDeportes = new JButton("Mis Deportes");
        JButton btnPerfil = new JButton("Ver Perfil");
        JButton btnMisPartidos = new JButton("Ver mis partidos");
        JButton btnNotificaciones = new JButton("Notificaciones");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        for (JButton btn : new JButton[]{btnVerPartidos, btnCrearPartido, btnMisDeportes, btnPerfil,btnMisPartidos, btnNotificaciones, btnCerrarSesion}) {
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
        btnMisDeportes.addActionListener(e -> parent.showPanel("Deporte"));
        btnPerfil.addActionListener(e -> parent.showPanel("perfil"));
        
        btnMisPartidos.addActionListener(e -> parent.showPanel("misPartidos"));

        
        btnNotificaciones.addActionListener(e -> parent.showPanel("notificaciones"));
        btnCerrarSesion.addActionListener(e -> {
            LoginPanel loginPanel = parent.getLoginPanel();
            loginPanel.limpiarCampos();
            parent.showPanel("login");
        });

        add(panelContenido);
    }

    // ✅ Este método debe llamarse cada vez que se entra al menú
    public void actualizarUsuario(String nickname) {
        this.nicknameActual = nickname;
        Usuario user = UsuarioController.getInstancia().getUserById(nickname);
        String nombre = (user != null) ? user.getNombre() : nickname;
        lblTitulo.setText("Bienvenido, " + nombre);
    }
}

