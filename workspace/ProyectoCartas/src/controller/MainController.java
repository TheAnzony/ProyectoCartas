package controller;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modulos.Jugador;
import utils.config;
import modulos.Estadio;
import view.CartaView;
import view.HistorialView;
import view.MazoView;
import view.MenuView;
import view.PartidaView;
import view.PlayersView;
import view.SeleccionJugadoresView;
import view.StartView;

/**
 * Controlador principal de la aplicación.
 * Gestiona el {@link JFrame} y centraliza los cambios de pantalla entre las distintas vistas.
 * Sigue el patrón MVC: las vistas llaman a los métodos {@code lanzar*()} para navegar.
 */
public class MainController implements config {

	/** Ventana principal de la aplicación. */
	private JFrame ventana;

	/**
	 * Crea la ventana principal, configura sus propiedades y muestra la pantalla de inicio.
	 */
	public MainController() {
		ventana = new JFrame("Cartas");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.getContentPane().setPreferredSize(new Dimension(ANCHO, ALTO));
		ventana.pack();
		ventana.setLocationRelativeTo(null);

		lanzarStart();

		ventana.setVisible(true);
	}

	/** Muestra la pantalla de carga inicial ({@link view.StartView}). */
	public void lanzarStart() {
		cambiarPantalla(new StartView(this));
	}

	/** Muestra el menú principal ({@link view.MenuView}). */
	public void lanzarMenuPrincipal() {
		cambiarPantalla(new MenuView(this));
	}

	/** Muestra la gestión de jugadores ({@link view.PlayersView}). */
	public void lanzarMenuJugador() {
		cambiarPantalla(new PlayersView(this));
	}

	/** Muestra la pantalla de selección de jugadores y estadio ({@link view.SeleccionJugadoresView}). */
	public void lanzarPartida() {
		cambiarPantalla(new SeleccionJugadoresView(this));
	}

	/**
	 * Inicia la pantalla de batalla entre dos jugadores en un estadio concreto.
	 *
	 * @param j1     Jugador 1.
	 * @param j2     Jugador 2.
	 * @param estadio Estadio seleccionado para la partida.
	 */
	public void lanzarBatalla(Jugador j1, Jugador j2, Estadio estadio) {
		cambiarPantalla(new PartidaView(this, j1, j2, estadio));
	}

	/** Muestra el historial de partidas ({@link view.HistorialView}). */
	public void lanzarHistorial() {
		cambiarPantalla(new HistorialView(this));
	}

	/** Muestra el catálogo de cartas ({@link view.CartaView}). */
	public void lanzarMenuCartas() {
		cambiarPantalla(new CartaView(this));
	}

	/**
	 * Muestra el editor de mazo del jugador indicado ({@link view.MazoView}).
	 *
	 * @param j Jugador cuyo mazo se va a editar.
	 */
	public void lanzarMenuMazo(Jugador j) {
		cambiarPantalla(new MazoView(this, j));
	}

	/**
	 * Reemplaza el contenido de la ventana principal por la nueva pantalla.
	 *
	 * @param nuevaPantalla Panel que se mostrará a continuación.
	 */
	private void cambiarPantalla(JPanel nuevaPantalla) {
		ventana.getContentPane().removeAll();
		ventana.getContentPane().add(nuevaPantalla);
		ventana.getContentPane().revalidate();
		ventana.getContentPane().repaint();

		SwingUtilities.invokeLater(() -> nuevaPantalla.requestFocusInWindow());
	}

	/**
	 * Devuelve la ventana principal de la aplicación.
	 *
	 * @return {@link JFrame} de la ventana principal.
	 */
	public JFrame getVentana() {
		return ventana;
	}

}
