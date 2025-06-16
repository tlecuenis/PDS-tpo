package view;

import controller.PartidoController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PartidosDisponiblesPanel extends JPanel {

    public PartidosDisponiblesPanel(Ejecucion parent, PartidoController partidoController) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("Partidos Disponibles");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, gbc);

        List<Partido> partidosDisponibles = partidoController.obtenerPartidosDisponibles();
        for (Partido partido : partidosDisponibles) {
            add(new JLabel(partido.toString()));
        }

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton btnVolver = new JButton("Volver");
        add(btnVolver, gbc);

        btnVolver.addActionListener(e -> parent.showPanel("menuPrincipal"));
    }
}