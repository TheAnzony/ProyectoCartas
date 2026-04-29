package test;

import java.util.List;

import dao.TurnoCartaDAO;
import modulos.Turno_carta;

public class PruebaTurnoCartaDAO {

	public static void main(String[] args) {

		TurnoCartaDAO dao = new TurnoCartaDAO();

		// ── 1. INSERTAR ──────────────────────────────────────────────────────────
		// Requiere que exista el turno con id=1 y la carta con id=2 en la BD
		System.out.println("=== INSERTAR ===");
		Turno_carta tc = new Turno_carta(2, 2, 3, "OFENSIVA", 22, "Carta insertada desde Java");
		boolean insertado = dao.insertar(tc);
		System.out.println("Resultado insertar: " + insertado);

		// ── 2. LISTAR POR TURNO ──────────────────────────────────────────────────
		System.out.println("\n=== LISTAR POR TURNO (id=1) ===");
		List<Turno_carta> porTurno = dao.listarPorTurno(2);
		if (porTurno.isEmpty()) {
			System.out.println("No hay cartas jugadas en el turno 1.");
		} else {
			for (Turno_carta t : porTurno) {
				System.out.println(t);
			}
		}

		// ── 3. LISTAR POR TURNO 3 ───────────────────────────────────────────────
		System.out.println("\n=== LISTAR POR TURNO (id=3) ===");
		List<Turno_carta> porTurno3 = dao.listarPorTurno(3);
		if (porTurno3.isEmpty()) {
			System.out.println("No hay cartas jugadas en el turno 3.");
		} else {
			for (Turno_carta t : porTurno3) {
				System.out.println(t);
			}
		}

	}

}
