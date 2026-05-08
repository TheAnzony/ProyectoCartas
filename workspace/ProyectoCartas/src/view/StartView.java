package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;
import utils.config;

public class StartView extends JPanel implements config{
	
	
	private MainController controller;
	
	public StartView(MainController c) {
		// TODO Auto-generated constructor stub
		this.controller = c;
		setLayout(null);
        setPreferredSize(new Dimension(ANCHO,ALTO));

        // 2. Carga y Escalado de Imagen usando Constantes
        ImageIcon iconoOriginal = new ImageIcon(START_IMAGE);
        Image imgEscalada = iconoOriginal.getImage().getScaledInstance(
            ANCHO, 
            ALTO, 
            Image.SCALE_SMOOTH
        );
        
        JLabel background = new JLabel(new ImageIcon(imgEscalada));
        background.setBounds(0, 0,ANCHO, ALTO);

        // 3. Evento de Teclado: Detectar "Cualquier Tecla"
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Al pulsar cualquier tecla, avisamos al controlador para cambiar
                controller.lanzarMenuPrincipal();
            }
        });

        // 4. IMPORTANTE para que el teclado funcione
        setFocusable(true);
        requestFocusInWindow();

        add(background);
	}
	
	

}
