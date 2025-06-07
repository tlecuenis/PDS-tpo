package view;

import javax.swing.*;
import javax.swing.*;
import java.awt.event.*;

public class PartidosDisponibles extends JPanel {

	private JComboBox<String> comboDeporte;
	private JTextField txtUbicacion;
	private JTextField txtFecha;
	private JComboBox<String> comboNivel;

	public PartidosDisponibles(Ejecucion parent) {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Buscar Partido");
		lblTitulo.setBounds(130, 10, 150, 30);
		add(lblTitulo);

		JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setBounds(30, 60, 100, 20);
        add(lblUbicacion);

        txtUbicacion = new JTextField();
        txtUbicacion.setBounds(140, 60, 200, 25);
        add(txtUbicacion);

        JLabel lblFecha = new JLabel("Fecha (dd/mm/yyyy):");
        lblFecha.setBounds(30, 100, 140, 20);
        add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(180, 100, 160, 25);
        add(txtFecha);
        
        JLabel lblDeporte = new JLabel("Deporte favorito (Opcional):");
        lblDeporte.setBounds(30, 140, 180, 20);
        add(lblDeporte);

        comboDeporte = new JComboBox<>(new String[] {"Fútbol", "Básquet", "Tenis", "Padel", "Otro"});
        comboDeporte.setBounds(210, 140, 180, 25); 
        add(comboDeporte);

        // Listener para permitir ingresar un deporte personalizado
        comboDeporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboDeporte.getSelectedItem();
                if ("Otro".equals(seleccionado)) {
                    String nuevoDeporte = JOptionPane.showInputDialog(PartidosDisponibles.this, "Ingrese el deporte favorito:");
                    if (nuevoDeporte != null && !nuevoDeporte.trim().isEmpty()) {
                        comboDeporte.insertItemAt(nuevoDeporte.trim(), comboDeporte.getItemCount() - 1);
                        comboDeporte.setSelectedItem(nuevoDeporte.trim());
                    } else {
                        comboDeporte.setSelectedIndex(0); // vuelve al valor vacío
                    }
                }
            }
        });

		JLabel lblNivel = new JLabel("Nivel:");
		lblNivel.setBounds(30, 170, 100, 20);
		add(lblNivel);

		comboNivel = new JComboBox<>();
		comboNivel.setBounds(140, 170, 200, 25);
		comboNivel.addItem("Principiante");
		comboNivel.addItem("Intermedio");
		comboNivel.addItem("Avanzado");
		add(comboNivel);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(140, 220, 100, 30);
		add(btnBuscar);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(250, 220, 100, 30);
		add(btnVolver);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deporte = comboDeporte.getSelectedItem().toString();
				String ubicacion = txtUbicacion.getText().trim();
				String fecha = txtFecha.getText().trim();
				String nivel = comboNivel.getSelectedItem().toString();

				// Acá podrías filtrar desde una lista de partidos
				JOptionPane.showMessageDialog(null, "Buscando partidos de " + deporte +
					" en " + ubicacion + " el " + fecha + " (Nivel: " + nivel + ")");
			}
		});

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.showPanel("menuPrincipal");
			}
		});
	}
}


