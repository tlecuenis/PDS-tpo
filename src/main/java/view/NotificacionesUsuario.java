package view;

import controller.UsuarioController;
import model.Usuario;
import repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotificacionesUsuario extends JPanel {

    public NotificacionesUsuario(Ejecucion parent, String nickname) {
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Notificaciones recientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Obtener mensajes desde controlador
        UserRepository userRepo = new UserRepository();
        Usuario temp = userRepo.findByField("_id", parent.getNicknameActual());

        List<String> mensajes = UsuarioController.getInstancia().getMensaje(temp.getIdUsuario());

        // Crear lista visual de mensajes
        DefaultListModel<String> listaModel = new DefaultListModel<>();
        for (String m : mensajes) {
            listaModel.addElement(m);
        }

        JList<String> listaMensajes = new JList<>(listaModel);
        listaMensajes.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(listaMensajes);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Mensajes"));
        add(scrollPane, BorderLayout.CENTER);

        // Botón Volver
        JPanel panelInferior = new JPanel();
        JButton btnVolver = new JButton("Volver");
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));
    }
}
