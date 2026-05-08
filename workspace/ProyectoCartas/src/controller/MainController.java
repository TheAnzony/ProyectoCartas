package controller;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.config;
import view.MenuView;
import view.StartView;

public class MainController implements config{
	
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
	
	private void cambiarPantalla(JPanel nuevaPantalla) {
		ventana.getContentPane().removeAll();
		ventana.getContentPane().add(nuevaPantalla);
		ventana.getContentPane().revalidate();
		ventana.getContentPane().repaint();
		
		nuevaPantalla.requestFocusInWindow();
	}

}
