package menuPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

// Importamos tus DAOs y modelos
import dao.*;
import modulos.*;

public class MenuPrincipal extends JFrame {

    // Instancias de los DAOs
    private JugadorDAO jugadorDAO = new JugadorDAO();
    private CartaDAO cartaDAO = new CartaDAO();
    private MazoDAO mazoDAO = new MazoDAO();
    private EstadioDAO estadioDAO = new EstadioDAO();

    public MenuPrincipal() {
        // 1. Configuración de la ventana principal (el lanzador)
        setTitle("CardBattle - Lanzador");
        setSize(300, 500); // Ventana más estrecha y alta
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2. Título superior
        JLabel lblTitulo = new JLabel("MENÚ PRINCIPAL", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // 3. Panel central con botones verticales
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 20)); // 4 filas, 1 columna
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        JButton btnJugadores = new JButton("👤 JUGADORES");
        JButton btnCatalogo = new JButton("🃏 CATÁLOGO");
        JButton btnMazos = new JButton("📦 MIS MAZOS");
        JButton btnBatalla = new JButton("⚔️ BATALLA");

        // Estilo de los botones
        Font fuenteBotones = new Font("Arial", Font.PLAIN, 16);
        btnJugadores.setFont(fuenteBotones);
        btnCatalogo.setFont(fuenteBotones);
        btnMazos.setFont(fuenteBotones);
        btnBatalla.setFont(fuenteBotones);

        // 4. Acciones de los botones (Abrir ventanas nuevas)
        btnJugadores.addActionListener(e -> abrirVentana("Gestión de Jugadores", crearPanelJugadores()));
        btnCatalogo.addActionListener(e -> abrirVentana("Catálogo de Cartas", crearPanelCatalogo()));
        btnMazos.addActionListener(e -> abrirVentana("Gestión de Mazos", crearPanelMazos()));
        btnBatalla.addActionListener(e -> abrirVentana("Nueva Partida", crearPanelPartida()));

        panelBotones.add(btnJugadores);
        panelBotones.add(btnCatalogo);
        panelBotones.add(btnMazos);
        panelBotones.add(btnBatalla);

        add(panelBotones, BorderLayout.CENTER);
    }

    /**
     * Método auxiliar para crear y mostrar una ventana nueva (JDialog)
     */
    private void abrirVentana(String titulo, JPanel contenido) {
        JDialog ventana = new JDialog(this, titulo, false); // false = permite tocar la principal mientras esta abierta
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(this);
        ventana.add(contenido);
        ventana.setVisible(true);
    }

    // --- LOS MÉTODOS DE LOS PANELES (IGUAL QUE ANTES PERO DENTRO DE ESTA CLASE) ---

    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID", "Apodo", "Nombre", "MMR"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        
        List<Jugador> lista = jugadorDAO.listar();
        for (Jugador j : lista) {
            modeloTabla.addRow(new Object[]{j.getId_jugador(), j.getApodo(), j.getNombre(), j.getMMR()});
        }
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelCatalogo() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = {"Nombre", "Tipo", "Maná", "Daño", "Escudo", "Rareza"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);

        List<Carta> cartas = cartaDAO.listar();
        for (Carta c : cartas) {
            modelo.addRow(new Object[]{c.getNombre(), c.getTipo(), c.getCoste_mana(), c.getDano(), c.getEscudo(), c.getRareza()});
        }
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMazos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<Jugador> comboJugadores = new JComboBox<>();
        jugadorDAO.listar().forEach(comboJugadores::addItem);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaMazos = new JList<>(modeloLista);

        comboJugadores.addActionListener(e -> {
            modeloLista.clear();
            Jugador sel = (Jugador) comboJugadores.getSelectedItem();
            if (sel != null) {
                mazoDAO.listarPorJugador(sel.getId_jugador()).forEach(m -> modeloLista.addElement(m.getNombre()));
            }
        });

        panel.add(comboJugadores, BorderLayout.NORTH);
        panel.add(new JScrollPane(listaMazos), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelPartida() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JComboBox<Jugador> j1 = new JComboBox<>();
        JComboBox<Jugador> j2 = new JComboBox<>();
        List<Jugador> jugadores = jugadorDAO.listar();
        jugadores.forEach(j -> { j1.addItem(j); j2.addItem(j); });

        JComboBox<Estadio> comboEstadio = new JComboBox<>();
        estadioDAO.listar().forEach(comboEstadio::addItem);

        JButton btnJugar = new JButton("¡EMPEZAR BATALLA!");
        
        panel.add(new JLabel("Jugador 1:")); panel.add(j1);
        panel.add(new JLabel("Jugador 2:")); panel.add(j2);
        panel.add(new JLabel("Estadio:")); panel.add(comboEstadio);
        panel.add(new JLabel("")); panel.add(btnJugar);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}