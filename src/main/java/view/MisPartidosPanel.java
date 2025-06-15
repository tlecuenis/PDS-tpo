package view;

import controller.UsuarioController;
import controller.PartidoController;
import model.Partido;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MisPartidosPanel extends JPanel {
    private Usuario usuarioLogeado;
    private JPanel listaPanel;

    public MisPartidosPanel(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
        inicializarVista();
        cargarMisPartidos();
    }

    public MisPartidosPanel(Ejecucion ejecucion, String nicknameActual) {
        this.usuarioLogeado = UsuarioController.getInstancia().getUserById(nicknameActual);
        inicializarVista();
        cargarMisPartidos();
    }

    private void inicializarVista() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Mis Partidos creados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listaPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarMisPartidos() {
        listaPanel.removeAll();

        List<Partido> partidos = PartidoController.getInstancia().obtenerTodosLosPartidos();

        if (partidos == null || partidos.isEmpty()) {
            listaPanel.add(new JLabel("No has creado ningún partido."));
            revalidate();
            repaint();
            return;
        }

        boolean hayPartidos = false;

        for (Partido p : partidos) {
            if (estaUsuarioEnPartido(p, usuarioLogeado)) {
                hayPartidos = true;

                JPanel partidoPanel = new JPanel(new BorderLayout());
                partidoPanel.setBorder(BorderFactory.createTitledBorder(p.getDeporte() + " - " + p.getFecha()));

                JLabel info = new JLabel("ID: " + p.getIdPartido() + " | Jugadores: " + p.cantidadJugadoresActual());
                partidoPanel.add(info, BorderLayout.CENTER);

                if (p.getCreador() != null && p.getCreador().getIdUsuario().equals(usuarioLogeado.getIdUsuario())) {
                    JPanel botones = new JPanel();
                    JButton btnConfirmar = new JButton("Confirmar");
                    JButton btnCancelar = new JButton("Cancelar");
                    JButton btnFinalizar = new JButton("Finalizar");

                    btnConfirmar.addActionListener(e -> {
                        p.confirmar();
                        PartidoController.getInstancia().guardarPartido(p);
                        JOptionPane.showMessageDialog(this, "Partido confirmado.");
                    });

                    btnCancelar.addActionListener(e -> {
                        p.cancelar();
                        PartidoController.getInstancia().guardarPartido(p);
                        JOptionPane.showMessageDialog(this, "Partido cancelado.");
                    });

                    btnFinalizar.addActionListener(e -> {
                        p.finalizar();
                        PartidoController.getInstancia().guardarPartido(p);
                        JOptionPane.showMessageDialog(this, "Partido finalizado.");
                    });

                    botones.add(btnConfirmar);
                    botones.add(btnCancelar);
                    botones.add(btnFinalizar);

                    partidoPanel.add(botones, BorderLayout.SOUTH);
                }

                listaPanel.add(partidoPanel);
            }
        }

        if (!hayPartidos) {
            listaPanel.add(new JLabel("No estás inscripto en ningún partido."));
        }

        revalidate();
        repaint();
    }

    private boolean estaUsuarioEnPartido(Partido partido, Usuario usuario) {
        return partido.getEquipos().stream()
                .flatMap(eq -> eq.getJugadores().stream())
                .anyMatch(j -> j.getIdUsuario().equals(usuario.getIdUsuario()));
    }
}


