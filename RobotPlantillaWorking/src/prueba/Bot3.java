package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import robocode.Robot;

public class Bot3 extends Robot {

	/***********************************************************************************/
	public static long semilla = 99;
	public static int numObstaculos = 27; 
	public static int value = 3;
	/*************************************************************************************/

	private int fila = 16;
	private int columna = 12;
	private int tamCelda = 50;
	private int filaPixels = 800;
	private Problema pr;
	private Solucion s1;
	
	public void run() {

		pr = new Problema(semilla, fila, columna, numObstaculos);
		System.out.println("Iniciando ejecucion del robot (MODELO BOT 3)\n Tablero:");
		System.out.println(pr);

		// Escogemos el color BLANCO para el robot

		super.setColors(Color.red, Color.white, Color.blue);

		// Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));

		s1 = new Solucion(pr, value);
		String s = "";
		if (value == 1)
			s = "AMPLITUD";
		else if (value == 2)
			s = "VORAZ";
		else
			s = "A*";

		System.out.println("Algoritmo " + s + "...  Ocho movimientos\nSemilla: " + this.pr.getSemilla()
				+ " Obstaculos: " + this.pr.getObstaculos() + "\nHay solución para esta semilla? "
				+ (s1.getSol() == null ? "NO\n" : "SI\n"));

		if (s1.getSol() == null) // no hay ningun camino!
			System.out.println(
					"No hay ningún camino!!\nLa semilla " + pr.getSemilla() + " no genera un problema con solución :(");
		else {
			int cont = 0;
			for (Nodo e : s1.getSol()) { // recorrido por los nodos solucion
				Movimiento m = new Movimiento(e.getMovimiento());
				System.out.println(cont + ".  [" + m.getSelected() + " : " + e.getPadre().getActual() + " -> "
						+ e.getActual() + "]   ");
				int grados = 0;
				boolean esDiag = false;
				switch (m.getSelected()) { // seleccion de grados dado movimiento del nodo
				case ARRIBA:
					grados = 0;
					break;
				case DERECHA:
					grados = 90;
					break;
				case ABAJO:
					grados = 180;
					break;
				case IZQUIERDA:
					grados = 270;
					break;
				case ABAJODERECHA:
					grados = 135;
					esDiag = true;
					break;
				case ABAJOIZQUIERDA:
					grados = 225;
					esDiag = true;
					break;
				case ARRIBADERECHA:
					grados = 45;
					esDiag = true;
					break;
				case ARRIBAIZQUIERDA:
					grados = 315;
					esDiag = true;
					break;
				default:
					grados = 0;
				}
				turnRight(normalRelativeAngleDegrees(grados - getHeading())); // movimiento giro robot
				if (esDiag) {
					ahead(Math.sqrt(2) * tamCelda); // avance en diagonal
				} else {
					ahead(tamCelda); // avance en linea recta
				}
				cont++;
			}
			System.out.println("\nNodos expandidos: " + s1.getNodos_expandidos() + " Nodos abiertos: "
					+ s1.getNodos_abiertos() + " Coste: " + s1.getCoste_total()); // imprimir información útil de
																					// ejecución
		}

		while (true) { // se queda girando indefinidamente
			super.turnLeft(90);
		}
	}
	
	/**
	 * El método paint en Bot3 pinta las casillas inicial y final, así como las
	 * cuadrículas, los nodos abiertos, cerrados y el camino solución. Incluye una
	 * leyenda con los colores de cada una
	 */
	public void onPaint(Graphics2D g) {
	
		Casilla _final = pr.getPosFinal();
		Casilla _inicial = pr.getPosInicial();
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 16)); // seleccion fuente

		// INICIAL -> RECTANGULO GRANDE VERDE
		g.setColor(Color.GREEN);
		g.drawString("INICIAL", 5, 5);
		g.fillRect((int) _inicial.getFila() * tamCelda + 8, (int) _inicial.getColumna() * tamCelda + 8, tamCelda - 16,
				tamCelda - 16);

		// DESTINO -> RECTANGULO GRANDE ROJO
		g.setColor(Color.RED);
		g.drawString("FINAL", 65, 5);
		g.fillRect((int) _final.getFila() * tamCelda + 8, (int) _final.getColumna() * tamCelda + 8, tamCelda - 16,
				tamCelda - 16);

		if (s1.getSol() != null) { // si hay un camino...
			// cerrados -> dibujamos nodos cerrados en azul
			g.setColor(Color.CYAN);
			g.drawString("Cerrados", 5, 23); // leyenda
			for (Nodo n : s1.getCerrados()) {
				g.fillRect((int) n.getActual().getFila() * tamCelda + 38,
						(int) n.getActual().getColumna() * tamCelda + 38, tamCelda - 40, tamCelda - 40);
			}

			// abiertos -> dibujamos nodos abiertos en amarillo
			g.setColor(Color.YELLOW);
			g.drawString("Abiertos", 80, 23); // leyenda
			for (Nodo n : s1.getAbiertos()) {
				g.fillRect((int) n.getActual().getFila() * tamCelda + 38,
						(int) n.getActual().getColumna() * tamCelda + 38, tamCelda - 40, tamCelda - 40);
			}

			// camino -> dibujamos el camino resultado en magenta
			g.setColor(Color.MAGENTA);
			g.drawString("Camino", 150, 23);// leyenda
			for (Nodo n : s1.getSol()) {
				g.fillRect((int) n.getActual().getFila() * tamCelda + 38,
						(int) n.getActual().getColumna() * tamCelda + 38, tamCelda - 40, tamCelda - 40);
			}

		}

		g.setPaint(Color.white); // pintamos celdas

		for (int i = 0; i < fila; i++)
			g.drawLine(i * tamCelda + 1, 0, i * tamCelda + 1, filaPixels);

		for (int i = 0; i < columna; i++)
			g.drawLine(0, i * tamCelda + 1, filaPixels, i * tamCelda + 1);
	}

}
