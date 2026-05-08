package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;
import utils.config;

public class MenuView extends JPanel implements config {

	private MainController controller;

	public MenuView(MainController c) {
		// TODO PRuebas
		this.controller = c;

		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));
		
		JPanel oscurece = new JPanel();
		oscurece.setBackground(new Color(0, 0, 0, 80));
		oscurece.setOpaque(true);
		oscurece.setBounds(0, 0, ANCHO, ALTO);
		
		// BOTONES
		Image b = new ImageIcon(BOTON_PARTIDA_IMAGE).getImage().getScaledInstance(240, 80, Image.SCALE_REPLICATE);
		JButton btnIniciarPartida = new JButton(new ImageIcon(b));
		btnIniciarPartida.setBounds((ANCHO - 240) / 2, (ALTO - 80) / 2, 240, 80);
		btnIniciarPartida.setBorderPainted(false); // quita el borde de Swing
		btnIniciarPartida.setContentAreaFilled(false); // quita el fondo gris de Swing
		btnIniciarPartida.setFocusPainted(false); // quita el borde de foco
		btnIniciarPartida.setOpaque(false);
		
		

		Image escalada = new ImageIcon(MENU_IMAGE).getImage().getScaledInstance(ANCHO, ALTO, Image.SCALE_SMOOTH);

		ImageIcon imagen = new ImageIcon(escalada);

		JLabel fondo = new JLabel(imagen);
		fondo.setBounds(0, 0, ANCHO, ALTO);
		
		
		add(btnIniciarPartida);
		add(oscurece);           // capa oscura
		add(fondo);

	}

}
