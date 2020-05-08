package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.Color;
import java.util.Random;

import robocode.Robot;

public class Bot1 extends Robot {

	int fila = 12;
	int columna = 16;
	int tamCelda = 50;
	int filaPixels = 800;

	// método de ejecución del robot
	
	public void run() {

		System.out.println("Iniciando ejecucion del robot (MODELO BOT 1)...");

		// Escogemos el color NEGRO para el robot

		setAllColors(Color.black);

		// Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));

		// A continuacion nuestro robot gira para la derecha, se mueve hacia adelante y
		// dispara
		boolean ok = true;
		Random ran = new Random();
		while (ok) { // bucle infinito
			if (ran.nextInt(100) < 50) {
				super.turnLeft(ran.nextInt(180));
				super.ahead(ran.nextInt(100) + 30);
			} else {
				super.turnRight(ran.nextInt(180));
				super.ahead(ran.nextInt(100) + 30);
			}
			super.fire(30);
		}

	}
}
