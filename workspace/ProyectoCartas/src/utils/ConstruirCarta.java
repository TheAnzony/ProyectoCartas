package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import modulos.Carta;

public class ConstruirCarta implements config {

	static ArrayList<ImageIcon> cartasDefault;

	public static void ConstruirCartasDefault() {

		ImageIcon cartaFuego = ImageUtils.cargarImagen(BOX_IMAGE_FUEGO, 200, 280);
		ImageIcon cartaAire = ImageUtils.cargarImagen(BOX_IMAGE_AIRE, 200, 280);
		ImageIcon cartaTierra = ImageUtils.cargarImagen(BOX_IMAGE_TIERRA, 200, 280);
		ImageIcon cartaAgua = ImageUtils.cargarImagen(BOX_IMAGE_AGUA, 200, 280);

		cartasDefault = new ArrayList<ImageIcon>();
		cartasDefault.add(cartaFuego);
		cartasDefault.add(cartaAgua);
		cartasDefault.add(cartaTierra);
		cartasDefault.add(cartaAire);
		
	}

	public static JLabel insertarCarta(Carta c) {
		return insertarCarta(c, 200, 280);
	}

	public static JLabel insertarCarta(Carta c, int w, int h) {

		double rw = w / 200.0;
		double rh = h / 280.0;

		ImageIcon fondoCarta = c.getImagen() != null
				? ImageUtils.cargarImagen(CARTAS_DIR + c.getImagen(), w, h)
				: null;

		JLabel titulo = new JLabel(c.getNombre(), JLabel.CENTER);
		JTextArea descripcion = new JTextArea(c.getDescripcion());

		if (fondoCarta == null) {
			titulo.setForeground(Color.white);
			titulo.setFont(new Font("Arial", Font.BOLD, (int)(15 * rw)));
			titulo.setBounds(0, (int)(50 * rh), w, (int)(25 * rh));

			descripcion.setLineWrap(true);
			descripcion.setWrapStyleWord(true);
			descripcion.setOpaque(false);
			descripcion.setEditable(false);
			descripcion.setForeground(Color.white);
			descripcion.setFont(new Font("Arial", Font.PLAIN, (int)(14 * rw)));
			descripcion.setBounds((int)(20 * rw), (int)(120 * rh), (int)(160 * rw), (int)(130 * rh));

			fondoCarta = ImageUtils.cargarImagen(getElementoRuta(c.getId_elemento()), w, h);
		}

		JLabel dano = new JLabel(c.getDano() + "");
		dano.setForeground(Color.white);
		dano.setFont(new Font("Arial", Font.BOLD, (int)(14 * rw)));
		dano.setBounds((int)(23 * rw), (int)(240 * rh), (int)(20 * rw), (int)(25 * rh));

		JLabel velocidad = new JLabel(c.getVelocidad() + "");
		velocidad.setForeground(Color.white);
		velocidad.setFont(new Font("Arial", Font.BOLD, (int)(16 * rw)));
		velocidad.setBounds(w - (int)(31 * rw), (int)(13 * rh), (int)(20 * rw), (int)(25 * rh));

		JLabel mana = new JLabel(String.valueOf(c.getCoste_mana()), JLabel.CENTER);
		mana.setForeground(Color.white);
		mana.setFont(new Font("Arial", Font.BOLD, (int)(15 * rw)));
		mana.setBounds(w - (int)(39 * rw), (int)(240 * rh), (int)(30 * rw), (int)(25 * rh));

		JLabel caja = new JLabel(fondoCarta);
		caja.setLayout(null);
		caja.setMaximumSize(new Dimension(w, h));

		caja.add(titulo);
		caja.add(descripcion);
		caja.add(dano);
		caja.add(velocidad);
		caja.add(mana);

		return caja;
	}

	private static String getElementoRuta(int id) {
		switch (id) {
			case 1:  return BOX_IMAGE_FUEGO;
			case 2:  return BOX_IMAGE_AGUA;
			case 3:  return BOX_IMAGE_TIERRA;
			case 4:  return BOX_IMAGE_AIRE;
			default: return BOX_IMAGE_FUEGO;
		}
	}

}
