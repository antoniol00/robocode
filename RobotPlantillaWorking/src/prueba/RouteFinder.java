package prueba;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSetup;
import robocode.control.RobotSpecification;

public class RouteFinder {

	public static void main(String[] args) {

		/***********************************************************************************/
		/*
		 * CAMBIAR ESTOS VALORES PARA GENERAR NUEVO PROBLEMA: SEMILLA Y OBSTACULOS HAY
		 * QUE CAMBIAR TAMBIÉN EN CLASE BOT3 Y/O BOT 2!!!!!
		 */
		long semilla = 100;
		int numObstaculos = 20;
		/***********************************************************************************/

		// Creamos un mapa con los datos especificados
		int numPixelFila = 800;
		int numPixelCol = 600;
		int tamCelda = 50; // celdas de 50 x 50

		int nFil = numPixelFila / tamCelda;
		int nCol = numPixelCol / tamCelda;

		Problema problema = new Problema(semilla, nFil, nCol, numObstaculos); // creamos el problema

		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode")); // Windows

		engine.setVisible(true);

		BattlefieldSpecification battlefield = new BattlefieldSpecification(numPixelFila, numPixelCol);

		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = true;

		/*
		 * Para ejecutar HITO 2 (Definición de problema y robot que gira) => hito2 =
		 * true Para ejecutar HITO 3 (Solución del problema y robot que simula) => hito2
		 * = false
		 */
		boolean hito2 = false;

		RobotSpecification[] modelRobots = null;

		if (hito2) {
			modelRobots = engine.getLocalRepository("sample.SittingDuck,prueba.Bot2*");
		} else {
			modelRobots = engine.getLocalRepository("sample.SittingDuck,prueba.Bot3*");
		}

		// Incluiremos un robot sittingDuck por obstaculo, mas nuestro propio robot.
		RobotSpecification[] existingRobots = new RobotSpecification[numObstaculos + 1];
		RobotSetup[] robotSetups = new RobotSetup[numObstaculos + 1];

		int indice = 0;

		existingRobots[indice] = modelRobots[1]; // creamos nuestro ROBOT

		double fila = (problema.getPosInicial().getFila() * 50) + 25;
		double columna = (problema.getPosInicial().getColumna() * 50) + 25;
		double arriba = 0;

		robotSetups[indice] = new RobotSetup(fila, columna, arriba);

		indice++;
		for (int f = 0; f < nFil; f++) {
			for (int c = 0; c < nCol; c++) {
				if (problema.obtenerValor(new Casilla(f, c)) == 1) {
					existingRobots[indice] = modelRobots[0]; // sittingDuck
					robotSetups[indice] = new RobotSetup((double) (f * 50 + 25), (double) (c * 50 + 25), arriba);
					indice++;
				}
			}
		}

		System.out.println("\n" + "Generados " + (indice - 1) + " sitting ducks.\n");

		BattleSpecification battleSpec = new BattleSpecification(battlefield, numberOfRounds, inactivityTime,
				gunCoolingRate, sentryBorderSize, hideEnemyNames, existingRobots, robotSetups);

		System.out.println("************Tablero generado correctamente************\n"); // mensaje de confirmacion
		engine.runBattle(battleSpec, true);

		engine.close();
		System.exit(0);

	}
}
