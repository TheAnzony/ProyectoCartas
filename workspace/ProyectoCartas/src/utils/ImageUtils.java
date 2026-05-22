package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Utilidad estática para carga, escalado y procesado de imágenes en la interfaz gráfica.
 * Mantiene una caché interna por ruta+dimensiones para evitar recargas innecesarias.
 */
public class ImageUtils implements config {

	/** Caché de imágenes indexada por {@code "ruta_ANCHOxALTO"}. */
	private static final Map<String, ImageIcon> cache = new HashMap<>();

	/**
	 * Carga una imagen desde disco, la escala a las dimensiones indicadas y la almacena en caché.
	 * Si la imagen ya fue cargada con las mismas dimensiones, la devuelve directamente desde caché.
	 *
	 * @param ruta  Ruta al fichero de imagen.
	 * @param ancho Ancho deseado en píxeles.
	 * @param alto  Alto deseado en píxeles.
	 * @return {@link ImageIcon} escalado, o {@code null} si el fichero no existe o falla la lectura.
	 */
	public static ImageIcon cargarImagen(String ruta, int ancho, int alto) {
		String key = ruta + "_" + ancho + "x" + alto;

		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		try {
			File f = new File(ruta);
			if (!f.exists()) {
				System.out.println("Imagen no existe " + ruta);
				return null;
			}
			BufferedImage original = ImageIO.read(f);
			BufferedImage escalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = escalada.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(original, 0, 0, ancho, alto, null);
			g2d.dispose();
			ImageIcon icono = new ImageIcon(escalada);
			cache.put(key, icono);
			return icono;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Carga una fuente TrueType desde disco y la deriva al tamaño indicado.
	 * Si falla la carga, devuelve una fuente Serif de respaldo.
	 *
	 * @param ruta Ruta al fichero {@code .ttf}.
	 * @param size Tamaño de la fuente en puntos.
	 * @return {@link Font} cargada a partir del fichero, o una fuente de respaldo si hay error.
	 */
	public static Font cargarFuente(String ruta, float size) {
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
			return f.deriveFont(Font.PLAIN, size);
		} catch (Exception e) {
			e.printStackTrace();
			return new Font("Serif", Font.BOLD, (int) size);
		}
	}

	/**
	 * Aplica una capa negra semitransparente sobre un {@link ImageIcon} para el efecto hover de botones.
	 *
	 * @param icono Imagen original.
	 * @return Nueva imagen oscurecida (el original no se modifica).
	 */
	public static ImageIcon oscurecerImagen(ImageIcon icono) {
		BufferedImage original = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = original.createGraphics();
		g2d.drawImage(icono.getImage(), 0, 0, null);
		g2d.setColor(new Color(0, 0, 0, 50));
		g2d.fillRect(0, 0, icono.getIconWidth(), icono.getIconHeight());
		g2d.dispose();
		return new ImageIcon(original);
	}

}
