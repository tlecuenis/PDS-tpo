package view;

import controller.UsuarioController;
import model.Usuario;
import model.notificaciones.PreferenciaNotificacion;
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
        // temp.setNotificaciones(userRepo.getNotificaciones(temp.getIdUsuario()));

        List<String> mensajes = UsuarioController.getInstancia().getMensaje(temp.getIdUsuario());

        if (mensajes == null || mensajes.isEmpty()) {
            mensajes = java.util.Arrays.asList("No tenés notificaciones");
        }

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

        // Panel inferior con selector de estrategia y botón Volver
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel lblPreferencia = new JLabel("Preferencia de notificación: ");

        String[] estrategias = {"Firebase", "Email"};
        JComboBox<String> comboPreferencia = new JComboBox<>(estrategias);

        // Setear la estrategia actual en el combo
        if (temp.getPreferenciaNotificacion() != null) {
            if (temp.getPreferenciaNotificacion() == PreferenciaNotificacion.FIREBASE_PREFERENCE) {
                comboPreferencia.setSelectedItem("Firebase");
            } else if (temp.getPreferenciaNotificacion() == PreferenciaNotificacion.EMAIL_PREFERENCE) {
                comboPreferencia.setSelectedItem("Email");
            }
        }

        // Listener para cambiar preferencia
        comboPreferencia.addActionListener(e -> {
            String seleccion = (String) comboPreferencia.getSelectedItem();

            if (seleccion.equalsIgnoreCase("Firebase")) {
                temp.setPreferenciaNotificacion(PreferenciaNotificacion.FIREBASE_PREFERENCE);
            } else if (seleccion.equalsIgnoreCase("Email")) {
                temp.setPreferenciaNotificacion(PreferenciaNotificacion.EMAIL_PREFERENCE);
            }

            // Actualizar en la base de datos
            userRepo.update(temp);

            JOptionPane.showMessageDialog(this,
                    "Preferencia actualizada a: " + seleccion,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));

        panelInferior.add(lblPreferencia);
        panelInferior.add(comboPreferencia);
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }
}
