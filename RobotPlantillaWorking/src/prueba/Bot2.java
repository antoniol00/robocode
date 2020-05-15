package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import robocode.Robot;

public class Bot2 extends Robot {

	/***********************************************************************************/
	long semilla = 92;
	int numObstaculos = 23;
	/***********************************************************************************/

	int fila = 16;
	int columna = 12;
	int tamCelda = 50;
	int filaPixels = 800;
	Problema pr = new Problema(semilla, fila, columna, numObstaculos);

	public void run() {

		System.out.println("Iniciando ejecucion del robot (MODELO BOT 2)\n Tablero:");
		System.out.println(pr);

		// Escogemos el color BLANCO para el robot

		super.setAllColors(Color.white);

		// Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));

		// El robot solo girará sobre si mismo en la posición inicial
		boolean ok = true;
		while (ok) {
			super.turnLeft(90);
		}

	}

	/**
	 * El método paint en Bot 2 pinta las casillas inicial y final, así como las
	 * cuadrículas. Incluye una leyenda con los colores de cada una
	 */
	public void onPaint(Graphics2D g) {

		Casilla _final = pr.getPosFinal();
		Casilla _inicial = pr.getPosInicial();
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 16)); // seleccion fuente

		// INICIAL
		g.setColor(Color.GREEN);
		g.drawString("INICIAL", 5, 5);
		g.fillRect((int) _inicial.getFila() * tamCelda + 8, (int) _inicial.getColumna() * tamCelda + 8, tamCelda - 16,
				tamCelda - 16);

		// DESTINO
		g.setColor(Color.RED);
		g.drawString("FINAL", 94, 5);
		g.fillRect((int) _final.getFila() * tamCelda + 8, (int) _final.getColumna() * tamCelda + 8, tamCelda - 16,
				tamCelda - 16);

		g.setPaint(Color.white);

		for (int i = 0; i < fila; i++)
			g.drawLine(i * tamCelda + 1, 0, i * tamCelda + 1, filaPixels);

		for (int i = 0; i < columna; i++)
			g.drawLine(0, i * tamCelda + 1, filaPixels, i * tamCelda + 1);
	}
}
