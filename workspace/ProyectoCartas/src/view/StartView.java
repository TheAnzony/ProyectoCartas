package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import controller.MainController;
import dao.CartaDAO;
import modulos.Carta;
import utils.ImageUtils;
import utils.config;

public class StartView extends JPanel implements config {

	private MainController controller;
	private boolean cargado = false;

	public StartView(MainController c) {
		this.controller = c;

		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		ImageIcon iconoOriginal = new ImageIcon(START_IMAGE);
		Image imgEscalada = iconoOriginal.getImage().getScaledInstance(ANCHO, ALTO, Image.SCALE_SMOOTH);

		JLabel background = new JLabel(new ImageIcon(imgEscalada));
		background.setBounds(0, 0, ANCHO, ALTO);
		background.setLayout(null);

		JLabel labelEstado = new JLabel("0%", SwingConstants.CENTER);
		labelEstado.setBounds(0, ALTO - 60, ANCHO, 40);
		labelEstado.setFont(new Font("Arial", Font.BOLD, 18));
		labelEstado.setForeground(Color.WHITE);
		background.add(labelEstado);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (cargado) {
					controller.lanzarMenuPrincipal();
				}
			}
		});

		setFocusable(true);
		requestFocusInWindow();

		add(background);

		new SwingWorker<Void, Integer>() {

			@Override
			protected Void doInBackground() {
				CartaDAO dao = new CartaDAO();
				List<Carta> lista = dao.listar();
				for (int i = 0; i < lista.size(); i++) {
					ImageUtils.cargarImagen(CARTAS_DIR + lista.get(i).getImagen(), 200, 280);
					int porcentaje = (int) ((i + 1) * 100.0 / lista.size());
					publish(porcentaje);
				}
				return null;
			}

			@Override
			protected void process(List<Integer> valores) {
				labelEstado.setText(valores.get(valores.size() - 1) + "%");
			}

			@Override
			protected void done() {
				cargado = true;
				labelEstado.setText("Pulsa cualquier tecla para continuar");
			}

		}.execute();
	}

}
