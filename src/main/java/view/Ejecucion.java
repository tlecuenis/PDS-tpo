package view;

import javax.swing.*;
import java.awt.CardLayout;

public class Ejecucion extends JFrame {

    private JPanel contentPane;
    private CardLayout cardLayout;

    private String nicknameActual;

    public Ejecucion() {
        setTitle("Sistema Deportivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 500, 400);

        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        setContentPane(contentPane);

        // Instanciación de vistas básicas
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);

        contentPane.add(loginPanel, "login");
        contentPane.add(registerPanel, "register");

        // NUEVAS VISTAS del menú principal
        OpcionesMenu dashboardPanel = new OpcionesMenu(this);
        PartidosDisponibles listaPartidosPanel = new PartidosDisponibles(this);
        CrearPartido crearPartidoPanel = new CrearPartido();
        // No creamos perfilPanel fijo acá porque necesita nickname
        NotificacionesUsuario notificacionesPanel = new NotificacionesUsuario();

        contentPane.add(dashboardPanel, "menuPrincipal");
        contentPane.add(listaPartidosPanel, "listaPartidos");
        contentPane.add(crearPartidoPanel, "crearPartido");
        contentPane.add(notificacionesPanel, "notificaciones");

        // Mostrar vista inicial
        showPanel("login");
    }

    // Método para setear el usuario logueado y crear el perfil
    public void setNicknameActual(String nickname) {
        this.nicknameActual = nickname;

        // Remover el panel viejo "perfil" si ya existe para evitar duplicados
        for (int i = 0; i < contentPane.getComponentCount(); i++) {
            if ("perfil".equals(contentPane.getComponent(i).getName())) {
                contentPane.remove(i);
                break;
            }
        }

        // Crear panel PerfilUsuario con el nickname actualizado
        PerfilUsuario perfilPanel = new PerfilUsuario(this, nicknameActual);
        perfilPanel.setName("perfil"); // Importante para poder buscarlo y removerlo

        contentPane.add(perfilPanel, "perfil");
    }

    public void showPanel(String name) {
        cardLayout.show(contentPane, name);
    }

    public String getNicknameActual() {
        return nicknameActual;
    }

    public static void main(String[] args) {
        Ejecucion frame = new Ejecucion();
        frame.setVisible(true);
    }
}



