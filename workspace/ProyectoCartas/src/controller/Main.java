package controller;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación.
 * Lanza {@link MainController} en el hilo de despacho de eventos de Swing (EDT).
 */
public class Main {

	/**
	 * Método principal de la aplicación.
	 *
	 * @param args Argumentos de línea de comandos (no utilizados).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainController());
	}

}
