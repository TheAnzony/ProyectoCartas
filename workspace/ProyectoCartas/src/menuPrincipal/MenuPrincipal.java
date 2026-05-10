package menuPrincipal;


import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        // 1. Configuración básica de la ventana
        setTitle("CardBattle - Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al darle a la X
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // 2. Crear el contenedor de pestañas
        JTabbedPane pestañas = new JTabbedPane();

        // 3. Crear las pestañas (paneles vacíos por ahora)
        JPanel panelJugadores = new JPanel();
        panelJugadores.add(new JLabel("Aquí irá el registro y ranking de Jugadores"));

        JPanel panelCatalogo = new JPanel();
        panelCatalogo.add(new JLabel("Aquí mostraremos la vista del Catálogo de Cartas"));

        JPanel panelMazos = new JPanel();
        panelMazos.add(new JLabel("Aquí gestionaremos los Mazos de cada jugador"));

        JPanel panelPartida = new JPanel();
        panelPartida.add(new JLabel("Aquí configuraremos la batalla local"));

        // 4. Añadir los paneles al contenedor con su título y un icono (opcional, aquí pongo null)
        pestañas.addTab("👤 Jugadores", null, panelJugadores, "Gestionar usuarios y ver ranking");
        pestañas.addTab("🃏 Catálogo", null, panelCatalogo, "Ver todas las cartas del juego");
        pestañas.addTab("📦 Mis Mazos", null, panelMazos, "Crear y editar mazos");
        pestañas.addTab("⚔️ Nueva Partida", null, panelPartida, "Iniciar un combate local");

        // 5. Añadir las pestañas a la ventana principal
        add(pestañas);
    }

    // Método main rápido para probar solo esta ventana
    public static void main(String[] args) {
        // Se recomienda arrancar interfaces gráficas en este "hilo" especial de Java
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true); // ¡Hágase la luz!
            }
        });
    }
}