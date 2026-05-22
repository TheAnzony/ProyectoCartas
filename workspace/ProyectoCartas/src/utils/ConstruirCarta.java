package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import modulos.Carta;

/**
 * Utilidad estática para construir componentes Swing que representan cartas visualmente.
 * Genera un {@link javax.swing.JLabel} con el marco de elemento correcto, el nombre,
 * la descripción, la velocidad, el coste de maná y la estadística principal de la carta.
 */
public class ConstruirCarta implements config {

	/** Imágenes de marco por defecto indexadas por elemento (Fuego, Agua, Tierra, Aire). */
	static ArrayList<ImageIcon> cartasDefault;

	/**
	 * Precarga en caché los cuatro marcos de elemento por defecto a tamaño 200×280.
	 * Debe llamarse una vez al inicio de la aplicación (en {@link view.StartView}).
	 */
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

	/**
	 * Construye el componente visual de una carta a tamaño estándar (200×280 px).
	 *
	 * @param c Carta a representar.
	 * @return {@link javax.swing.JLabel} con el layout completo de la carta.
	 */
	public static JLabel insertarCarta(Carta c) {
		return insertarCarta(c, 200, 280);
	}

	/**
	 * Construye el componente visual de una carta a las dimensiones indicadas.
	 * Escala proporcionalmente todos los textos e iconos internos.
	 * Si la carta no tiene imagen propia se usa el marco de su elemento.
	 *
	 * @param c Carta a representar.
	 * @param w Ancho deseado en píxeles.
	 * @param h Alto deseado en píxeles.
	 * @return {@link javax.swing.JLabel} con el layout completo de la carta.
	 */
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
		caja.add(velocidad);
		caja.add(mana);

		String tipo = c.getTipo();
		if ("OFENSIVA".equals(tipo)) {
			JLabel stat = new JLabel(c.getDano() + "");
			stat.setForeground(Color.white);
			stat.setFont(new Font("Arial", Font.BOLD, (int)(14 * rw)));
			stat.setBounds((int)(23 * rw), (int)(240 * rh), (int)(20 * rw), (int)(25 * rh));
			caja.add(stat);
		} else if ("DEFENSIVA".equals(tipo)) {
			JLabel stat = new JLabel(c.getEscudo() + "");
			stat.setForeground(new Color(100, 180, 255));
			stat.setFont(new Font("Arial", Font.BOLD, (int)(14 * rw)));
			stat.setBounds((int)(23 * rw), (int)(240 * rh), (int)(20 * rw), (int)(25 * rh));
			caja.add(stat);
		}

		return caja;
	}

	/**
	 * Devuelve la ruta al marco de carta correspondiente a un elemento dado.
	 *
	 * @param id Identificador del elemento (1=Fuego, 2=Agua, 3=Tierra, 4=Aire).
	 * @return Ruta al fichero de imagen del marco.
	 */
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
