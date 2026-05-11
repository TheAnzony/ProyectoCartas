package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.config;
import view.CartaView;
import view.MenuView;
import view.PlayersView;
import view.StartView;

public class MainController implements config {

	private JFrame ventana;

	public MainController() {
		// TODO Auto-generated constructor stub

		ventana = new JFrame("Cartas");
		ventana.setSize(ANCHO, ALTO);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);

		lanzarStart();

		ventana.setVisible(true);
	}

	public void lanzarStart() {
		cambiarPantalla(new StartView(this));
	}

	public void lanzarMenuPrincipal() {
		cambiarPantalla(new MenuView(this));
	}

	public void lanzarMenuJugador() {

		cambiarPantalla(new PlayersView(this));
	}

	public void lanzarPartida() {

	}

	public void lanzarMenuCartas() {

		cambiarPantalla(new CartaView(this));
	}

	private void cambiarPantalla(JPanel nuevaPantalla) {
		ventana.getContentPane().removeAll();
		ventana.getContentPane().add(nuevaPantalla);
		ventana.getContentPane().revalidate();
		ventana.getContentPane().repaint();

		SwingUtilities.invokeLater(() -> nuevaPantalla.requestFocusInWindow());
	}

	public JFrame getVentana() {
		return ventana;
	}

}
