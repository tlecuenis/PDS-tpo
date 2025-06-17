package view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Ejecucion extends JFrame {

    private JPanel contentPane;
    private CardLayout cardLayout;
    private String nicknameActual;
    private LoginPanel loginPanel;
    private OpcionesMenu dashboardPanel;

    public Ejecucion() {
        setTitle("Uno Más - Plataforma de Encuentros Deportivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Setup tema inicial
        FlatDarkLaf.setup();

        // Panel principal con CardLayout
        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Menu para cambiar tema con animación
        setJMenuBar(crearMenuBar());

        // Inicializar y agregar paneles
        loginPanel = new LoginPanel(this);
        dashboardPanel = new OpcionesMenu(this);
        RegisterPanel registerPanel = new RegisterPanel(this);
        OpcionesMenu dashboardPanel = new OpcionesMenu(this);
        //PartidosDisponibles listaPartidosPanel = new PartidosDisponibles(this);
        //NotificacionesUsuario notificacionesPanel = new NotificacionesUsuario(this);

        contentPane.add(loginPanel, "login");
        contentPane.add(registerPanel, "register");
        contentPane.add(dashboardPanel, "menuPrincipal");
        //contentPane.add(listaPartidosPanel, "listaPartidos");
        //contentPane.add(notificacionesPanel, "notificaciones");
        
        showPanel("login");
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuTema = new JMenu("Tema");
        JMenuItem itemClaro = new JMenuItem("Modo Claro");
        JMenuItem itemOscuro = new JMenuItem("Modo Oscuro");

        itemClaro.addActionListener(e -> cambiarTemaConAnimacion(new FlatLightLaf()));
        itemOscuro.addActionListener(e -> cambiarTemaConAnimacion(new FlatDarkLaf()));

        menuTema.add(itemClaro);
        menuTema.add(itemOscuro);
        menuBar.add(menuTema);

        return menuBar;
    }

    private void cambiarTemaConAnimacion(LookAndFeel laf) {
        try {
            FlatAnimatedLafChange.showSnapshot();
            UIManager.setLookAndFeel(laf);
            SwingUtilities.updateComponentTreeUI(this);
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void setNicknameActual(String nickname) {
        this.nicknameActual = nickname;

        for (int i = 0; i < contentPane.getComponentCount(); i++) {
            if ("perfil".equals(contentPane.getComponent(i).getName())) {
                contentPane.remove(i);
                break;
            }
        }

        PerfilUsuario perfilPanel = new PerfilUsuario(this, nicknameActual);
        perfilPanel.setName("perfil");
        contentPane.add(perfilPanel, "perfil");
    }


    public void showPanel(String name) {
        if ("login".equals(name)) {
            loginPanel.limpiarCampos();
        }

        if ("Deporte".equals(name)) {
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                if ("Deporte".equals(contentPane.getComponent(i).getName())) {
                    contentPane.remove(i);
                    break;
                }
            }
            MisDeportes registrarDeporte = new MisDeportes(this, nicknameActual);
            registrarDeporte.setName("Deporte");
            contentPane.add(registrarDeporte, "Deporte");
        }

        if ("misPartidos".equals(name)) {
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                if ("misPartidos".equals(contentPane.getComponent(i).getName())) {
                    contentPane.remove(i);
                    break;
                }
            }
            MisPartidosPanel misPartidos = new MisPartidosPanel(this, nicknameActual); 
            misPartidos.setName("misPartidos");
            contentPane.add(misPartidos, "misPartidos");
        }
        
        if ("listaPartidos".equals(name)) {
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                if ("listaPartidos".equals(contentPane.getComponent(i).getName())) {
                    contentPane.remove(i);
                    break;
                }
            }
            PartidosDisponibles partidosDisponibles = new PartidosDisponibles(this, nicknameActual);
            partidosDisponibles.setName("listaPartidos");
            contentPane.add(partidosDisponibles, "listaPartidos");
        }
        
        if ("notificaciones".equals(name)) {
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                if ("notificaciones".equals(contentPane.getComponent(i).getName())) {
                    contentPane.remove(i);
                    break;
                }
            }
            NotificacionesUsuario notificacionesUser = new NotificacionesUsuario(this, nicknameActual);
            notificacionesUser.setName("notificaciones");
            contentPane.add(notificacionesUser, "notificaciones");
        }

        if ("menuPrincipal".equals(name)) {
            for (Component comp : contentPane.getComponents()) {
                if (comp instanceof OpcionesMenu) {
                    ((OpcionesMenu) comp).actualizarUsuario(nicknameActual);
                    break;
                }
            }
        }
        
        if ("crearPartido".equals(name)) {
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                if ("crearPartido".equals(contentPane.getComponent(i).getName())) {
                    contentPane.remove(i);
                    break;
                }
            }
            CrearPartido crearPartidoPanel = new CrearPartido(this, nicknameActual);
            crearPartidoPanel.setName("crearPartido");
            contentPane.add(crearPartidoPanel, "crearPartido");
        }

        cardLayout.show(contentPane, name);
    }


    public String getNicknameActual() {
        return nicknameActual;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ejecucion().setVisible(true);
        });
    }
}
