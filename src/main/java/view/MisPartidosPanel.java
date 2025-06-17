package view;

import controller.PartidoController;
import controller.UsuarioController;
import model.Partido;
import model.Usuario;
import model.CercaniaStrategy;
import model.NivelStrategy;
import model.HistorialStrategy;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MisPartidosPanel extends JPanel {
    private Usuario usuarioLogeado;
    private JPanel listaPanel;

    public MisPartidosPanel(Ejecucion ejecucion, String nicknameUsuario) {
        this.usuarioLogeado = UsuarioController.getInstancia().getUserById(nicknameUsuario);

        if (this.usuarioLogeado == null) {
            System.err.println("No se encontró el usuario con nickname: " + nicknameUsuario);
            return;
        }

        PartidoController.getInstancia().setUsuarioLogueado(usuarioLogeado);

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Partidos creados por ti", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listaPanel);
        add(scrollPane, BorderLayout.CENTER);

        cargarMisPartidos();

        // --- Botón volver al menú ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnVolver);

        btnVolver.addActionListener(e -> ejecucion.showPanel("menuPrincipal"));

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarMisPartidos() {
        listaPanel.removeAll();

        List<Partido> partidos = PartidoController.getInstancia().obtenerPartidosCreadosPorUsuario();

        if (partidos.isEmpty()) {
            JLabel sinPartidos = new JLabel("No has creado ningún partido aún.");
            sinPartidos.setFont(new Font("Arial", Font.ITALIC, 14));
            sinPartidos.setForeground(Color.GRAY);
            sinPartidos.setAlignmentX(Component.CENTER_ALIGNMENT);
            listaPanel.add(Box.createVerticalStrut(20));
            listaPanel.add(sinPartidos);

            revalidate();
            repaint();
            return; 
        }
        
        for (Partido p : partidos) {
            JPanel partidoPanel = new JPanel();
            partidoPanel.setLayout(new BoxLayout(partidoPanel, BoxLayout.Y_AXIS));
            partidoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
            		p.getFecha().getDayOfMonth() + "/" + p.getFecha().getMonthValue() + "/" + p.getFecha().getYear() + " - " + p.getFecha().getHour() + ":" + p.getFecha().getMinute() + " - " + p.getEstado().getNombreEstado() + " - " +p.getUbicacion().getCiudad()));

            // Compactar el panel
            partidoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            partidoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel info = new JLabel(
            	    "Deporte: " + p.getDeporte() + 
            	    " | Jugadores: " + p.cantidadJugadoresActual() + 
            	    " | Duración: " + p.getDuracion(),
            	    SwingConstants.CENTER 
            	);
            	info.setFont(new Font("Arial", Font.PLAIN, 12));
            	info.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            	info.setAlignmentX(Component.CENTER_ALIGNMENT); 

            	partidoPanel.add(info); 

            JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            JButton btnEmparejar = new JButton("Emparejar");
            btnEmparejar.setFont(new Font("Arial", Font.PLAIN, 11));

            JPopupMenu menu = new JPopupMenu();
            JMenuItem opcionCercania = new JMenuItem("Por Cercanía");
            JMenuItem opcionNivel = new JMenuItem("Por Nivel");
            JMenuItem opcionHistorial = new JMenuItem("Por Historial");

            opcionCercania.addActionListener(e -> {
                p.setEstrategiaActual(new CercaniaStrategy());
                p.emparejar(p);
                PartidoController.getInstancia().guardarPartido(p);
                JOptionPane.showMessageDialog(this, "Estrategia elegida: Por Cercanía");
                cargarMisPartidos();
            });

            opcionNivel.addActionListener(e -> {
                p.setEstrategiaActual(new NivelStrategy());
                p.emparejar(p);
                PartidoController.getInstancia().guardarPartido(p);
                JOptionPane.showMessageDialog(this, "Estrategia elegida: Por Nivel");
                cargarMisPartidos();
            });

            opcionHistorial.addActionListener(e -> {
                p.setEstrategiaActual(new HistorialStrategy());
                p.emparejar(p);
                PartidoController.getInstancia().guardarPartido(p);
                JOptionPane.showMessageDialog(this, "Estrategia elegida: Por Historial");
                cargarMisPartidos();
            });

            menu.add(opcionCercania);
            menu.add(opcionNivel);
            menu.add(opcionHistorial);

            btnEmparejar.addActionListener(e -> menu.show(btnEmparejar, 0, btnEmparejar.getHeight()));
            botones.add(btnEmparejar);

            JButton btnConfirmar = new JButton("Confirmar");
            JButton btnCancelar = new JButton("Cancelar");
            JButton btnFinalizar = new JButton("Finalizar");

            btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 11));
            btnCancelar.setFont(new Font("Arial", Font.PLAIN, 11));
            btnFinalizar.setFont(new Font("Arial", Font.PLAIN, 11));

            btnConfirmar.addActionListener(e -> {
                p.confirmar();
                PartidoController.getInstancia().guardarPartido(p);
                JOptionPane.showMessageDialog(this, "Partido confirmado.");
                cargarMisPartidos();
            });

            btnCancelar.addActionListener(e -> {
                p.cancelar();
                PartidoController.getInstancia().guardarPartido(p);
                JOptionPane.showMessageDialog(this, "Partido cancelado.");
                cargarMisPartidos();
            });

            btnFinalizar.addActionListener(e -> {
                p.finalizar();
                PartidoController.getInstancia().guardarPartido(p);
                JOptionPane.showMessageDialog(this, "Partido finalizado.");
                cargarMisPartidos();
            });

            botones.add(btnConfirmar);
            botones.add(btnCancelar);
            botones.add(btnFinalizar);
            partidoPanel.add(botones);

            listaPanel.add(Box.createVerticalStrut(5));
            listaPanel.add(partidoPanel);
        }

        revalidate();
        repaint();
    }
}
