package prueba;

import java.util.Scanner;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSetup;
import robocode.control.RobotSpecification;

public class RouteFinder {

	private static long semilla;
	private static int numObstaculos;

	public static void main(String[] args) {

		/***********************************************************************************/
		Scanner sc = new Scanner(System.in);

		System.out.print("Indique el numero de semilla: ");
		semilla = sc.nextLong();

		System.out.print("Indique el numero de obstaculos: ");
		numObstaculos = sc.nextInt();

		String response = "";
		while (response.equals("")) {
			System.out.print(
					"¿Desea que se resuelva el problema? [S]: Resolver problema [N]: Solo generarlo y visualizarlo\n");
			response = sc.next();
			if (!(response.equalsIgnoreCase("S") || response.equalsIgnoreCase("N"))) {
				response = "";
				System.out.println("Opcion incorrecta. Intentelo de nuevo...");
			}
		}

		boolean hito2 = false;
		int opcion = 0;
		switch (response.toUpperCase()) {
		case "S":
			hito2 = false;
			while (opcion == 0) {
				System.out.println("Indique que algoritmo desea ejecutar:\n\t[1] AMPLITUD\n\t[2] VORAZ\n\t[3] A*\n");
				opcion = sc.nextInt();
				if (opcion < 1 || opcion > 3) {
					opcion = 0;
					System.out.println("Opcion incorrecta. Intentelo de nuevo...");
				}
			}
			System.out.println("Cambie en clase Bot3 los valores de las variables para que queden del siguiente modo:");
			System.out.println("public static long semilla = " + semilla);
			System.out.println("public static int numObstaculos = " + numObstaculos);
			System.out.println("public static int value = " + opcion);
			System.out.println("\nPulse intro para continuar...");
			Scanner sx = new Scanner(System.in);
			sx.nextLine();
			sx.close();
			break;
		case "N":
			hito2 = true;
			System.out.println("Cambie en clase Bot2 los valores de las variables para que queden del siguiente modo:");
			System.out.println("long semilla = " + semilla);
			System.out.println("int numObstaculos = " + numObstaculos);
			System.out.println("\nPulse intro para continuar...");
			Scanner sx2 = new Scanner(System.in);
			sx2.nextLine();
			sx2.close();
		}

		sc.close();
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
