package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.config;
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

		MenuView menu = new MenuView(this);

		
		
		
		menu.getBtnSalir().addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int opcion = JOptionPane.showConfirmDialog(
		            ventana,
		            "¿Seguro que quieres salir?",
		            "Salir",
		            JOptionPane.YES_NO_OPTION
		        );
		        if (opcion == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
		    }
		});
		
		
		
		

		cambiarPantalla(menu);

	}

	public void lanzarMenuJugador() {

		cambiarPantalla(new PlayersView(this));
	}

	public void lanzarPartida() {

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
