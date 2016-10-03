package algoritmo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import sun.management.resources.agent;

/**
 * UNIVERSIDADE DE FORTALEZA - I.A. 2016.2
 *
 * @author Valmar Júnior - 1120793 / 6
 * @author João Pedro - 1211207 / 6
 *
 */
@SuppressWarnings(value = { "all" })
public class Ladrao extends ProgramaLadrao {

	int myLastAction 		= 0;
	int visionMatrix[][] 	= new int[5][5];
	int smellMatrix[][] 	= new int[3][3];
	int matrixAlreadyVisited[][] = new int[30][30];
	boolean chasing;
	int coins;
	int posObj;
	Point myLastPos = new Point();
	Point myNextPos = new Point();
	boolean stopped;
	boolean lookingBank;
	int action;
	Double front;
	Double back;
	Double right;
	Double left;
	int dBank;
	int safetyZone;
	Point posBank = new Point();
	int[] pBank = new int[] { posBank.y, posBank.x };
	int[] pAtual;
	int outZone;
	boolean discoveryBank = false;

	int objective;
	int obstacle;
	int coin;
	int emptySpace;
	int mate;
	int lastAction;
	int wall;
	int outOfWorld;
	int bank;
	int powerCoin;
	int smellConstant;
	int samePointIwas;

	public int acao() {

		HashMap<Double, Integer> hmap = new HashMap<Double, Integer>();
		ArrayList<Double> auxList = new ArrayList<Double>();

		pAtual = new int[] { sensor.getPosicao().y, sensor.getPosicao().x };

		front = 0d;
		back = 0d;
		right = 0d;
		left = 0d;
		safetyZone = 15;
		outZone = -10000;
		action = 0;
		chasing = false;
		stopped = false;
		lookingBank = false;

		visionMatrix = getVisionMatrix();
		smellMatrix = getSmellMatrix();

		System.out.println("\n" + this.hashCode());

		ponderations();

		myLastPos = sensor.getPosicao();

		front = calculateTheFrontSide(visionMatrix, smellMatrix);
		// System.out.println("Front:" + front);
		back = calculateTheBackSide(visionMatrix, smellMatrix);
		// System.out.println("Back:" + back);
		right = calculateTheRightSide(visionMatrix, smellMatrix);
		// System.out.println("Right:" + right);
		left = calculateTheLeftSide(visionMatrix, smellMatrix);
		// System.out.println("Left:" + left);

		hmap.put(front, 1);
		hmap.put(back, 2);
		hmap.put(right, 3);
		hmap.put(left, 4);

		auxList.add(back);
		auxList.add(right);
		auxList.add(left);
		auxList.add(front);

		Collections.sort(auxList);

		action = hmap.get(auxList.get(auxList.size() - 1));

		if (sensor.getNumeroDeMoedas() > coins) {
			coins = sensor.getNumeroDeMoedas();
		}

		System.out.print("Ação: " + action);

		myLastAction = action;

		includeVisitedPoint(action);

		System.out.print(" - ");

		switch (action) {
		case 1:
			System.out.println("Front");
			break;
		case 2:
			System.out.println("Back");
			break;
		case 3:
			System.out.println("Right");
			break;
		case 4:
			System.out.println("Left");
			break;
		default:
			break;
		}

		System.out.println("Thief x: " + pAtual[1] + " y: " + pAtual[0]);

		System.out.println("Bank x: " + pBank[1] + " x: " + pBank[0]);

		return action;
	}

	public void ponderations() {

		if (chasing) {

			objective = 500;
			obstacle = -100;
			coin = 0;
			emptySpace = 0;
			mate = 0;
			lastAction = 0;
			wall = 0;
			outOfWorld = 0;
			bank = 100;
			powerCoin = 0;
			smellConstant = 100;
			samePointIwas = 0;

			System.out.println("\n" + this.hashCode() + " ----------------- Modo Chasing");
		} else {

			objective = 0;
			obstacle = -200;
			coin = -2;
			emptySpace = 5;
			mate = 0;
			lastAction = -30;
			wall = -10;
			outOfWorld = -5;
			bank = 150;
			powerCoin = 3;
			smellConstant = 100;
			samePointIwas = -30;

			System.out.println("\n" + this.hashCode() + " ---------------- Modo Searching");
		}

		if (myLastPos.equals(sensor.getPosicao())) {
			// System.out.println("---------------------Preso");
			emptySpace = 500;
			wall = -100;
		}

		if (lookingBank && chasing) {
			samePointIwas = 0;
		}

		if (lookingBank || chasing) {
			mate = 100;
			bank = 200;
		}

	}

	public Double calculateTheFrontSide(int[][] matrixVision, int[][] matrixSmell) {
		Double sum = 0d;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j++) {

				if (matrixSmell[i][j] > 1) {
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
					// System.out.println("smell front" + (1.0 /
					// matrixSmell[i][j]) * smellConstant);
				}
			}

		}

		if (matrixVision[1][2] != 0 && !(matrixVision[2][1] >= 100 && matrixVision[2][1] < 200))
			sum += obstacle;

		sum += (getTimesIVisitedThisPoint(1) * samePointIwas);

		if (discoveryBank) {
			dBank = distanceManhattan(pAtual[0] - 1, pAtual[1], pBank[0], pBank[1]);

			// System.out.println("Distancia Cima " + dBank);

			if (dBank > safetyZone)
				sum += outZone;
		}
		return sum;

	}

	public Double calculateTheBackSide(int[][] matrixVision, int[][] matrixSmell) {
		Double sum = 0d;

		for (int i = 3; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 2; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// System.out.println(matrixSmell[i][j]);
				if (matrixSmell[i][j] > 1) {
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
					// System.out.println("smell back" + (1.0 /
					// matrixSmell[i][j]) * smellConstant);
				}
			}

		}

		if (matrixVision[3][2] != 0 && !(matrixVision[2][1] >= 100 && matrixVision[2][1] < 200))
			sum += obstacle;

		if (myLastAction == 1)
			sum += lastAction;

		sum += getTimesIVisitedThisPoint(2) * samePointIwas;

		if (discoveryBank) {
			dBank = distanceManhattan(pAtual[0] + 1, pAtual[1], pBank[0], pBank[1]);

			// System.out.println("Distancia Baixo " + dBank);

			if (dBank > safetyZone)
				sum += outZone;
		}

		return sum;
	}

	public Double calculateTheLeftSide(int[][] matrixVision, int[][] matrixSmell) {
		Double sum = 0d;

		for (int i = 1; i < 4; i++) {
			for (int j = 0; j < 2; j++) {

				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 1; j++) {

				if (matrixSmell[i][j] > 1) {
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
					// System.out.println("smell left" + (1.0 /
					// matrixSmell[i][j]) * smellConstant);
				}
			}

		}

		if (matrixVision[2][1] != 0 && !(matrixVision[2][1] >= 100 && matrixVision[2][1] < 200))
			sum += obstacle;

		if (myLastAction == 3)
			sum += lastAction;

		sum += getTimesIVisitedThisPoint(4) * samePointIwas;

		if (discoveryBank) {
			dBank = distanceManhattan(pAtual[0], pAtual[1] - 1, pBank[0], pBank[1]);
			// System.out.println("Distancia Esquerda " + dBank);
			if (dBank > safetyZone)
				sum += outZone;

		}

		return sum;

	}

	public Double calculateTheRightSide(int[][] matrixVision, int[][] matrixSmell) {
		Double sum = 0d;

		for (int i = 1; i < 4; i++) {
			for (int j = 3; j < 5; j++) {

				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 0; i < 3; i++) {
			for (int j = 2; j < 3; j++) {

				if (matrixSmell[i][j] > 1) {
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
					// System.out.println("smell right" + (1.0 /
					// matrixSmell[i][j]) * smellConstant);
				}
			}

		}

		if (matrixVision[2][3] != 0 && !(matrixVision[2][1] >= 100 && matrixVision[2][1] < 200))
			sum += obstacle;

		if (myLastAction == 4)
			sum += lastAction;

		sum += getTimesIVisitedThisPoint(3) * samePointIwas;

		if (discoveryBank) {
			dBank = distanceManhattan(pAtual[0], pAtual[1] + 1, pBank[0], pBank[1]);
			// System.out.println("Distancia Direita " + dBank);
			if (dBank > safetyZone)
				sum += outZone;
		}

		return sum;

	}

	public int getTheValueOfTheConst(int f) {

		if (f == 0)
			return emptySpace;

		if (f >= 100 && f < 200)
			return objective;

		if (f >= 200 && f < 300)
			return mate;

		if (f == 4)
			return coin;

		if (f == -2)
			return wall;

		if (f == -1)
			return outOfWorld;

		if (f == 3)
			return bank;
		if (f == 5)
			return powerCoin;

		return 0;
	}

	public int[][] getVisionMatrix() {
		int conversionMatrix[][] = new int[5][5];
		int array[] = new int[24];

		array = sensor.getVisaoIdentificacao();

		int indexArray = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				if ((array[indexArray] >= 100 && array[indexArray] < 200))
					chasing = true;
				if (array[indexArray] == 3) {
					lookingBank = true;

					if (!discoveryBank) {

						pBank[0] = pAtual[0] + (i - 2);
						pBank[1] = pAtual[1] + (j - 2);

						discoveryBank = true;

					}

				}

				if (indexArray == 12) {
					// System.out.println(conversionMatrix[i][j]);
					j++;
					conversionMatrix[i][j] = array[indexArray];
					indexArray++;

				} else {
					conversionMatrix[i][j] = array[indexArray];
					indexArray++;
				}

			}

		}
		return conversionMatrix;
	}

	public int[][] getSmellMatrix() {
		int conversionMatrix[][] = new int[3][3];
		int array[] = new int[8];

		array = sensor.getAmbienteOlfatoLadrao();

		int indexArray = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (indexArray == 4) {
					// System.out.println(conversionMatrix[i][j]);
					j++;
					conversionMatrix[i][j] = array[indexArray];
					indexArray++;

				} else {
					conversionMatrix[i][j] = array[indexArray];
					indexArray++;
				}

			}

		}
		return conversionMatrix;
	}

	public void includeVisitedPoint(int action) {
		int i = 0, j = 0;
		Point matrixHash[][] = new Point[5][5];

		matrixHash[1][2] = new Point(-1, 0);
		matrixHash[2][1] = new Point(0, -1);
		matrixHash[2][3] = new Point(0, +1);
		matrixHash[3][2] = new Point(+1, 0);

		if (action == 1) {
			i = 1;
			j = 2;
		}
		if (action == 2) {
			i = 3;
			j = 2;
		}
		if (action == 3) {
			i = 2;
			j = 3;
		}
		if (action == 4) {
			i = 2;
			j = 1;
		}

		int x = (int) (sensor.getPosicao().getY() + matrixHash[i][j].getX());
		int y = (int) (sensor.getPosicao().getX() + matrixHash[i][j].getY());

		if ((x >= 0 && x < 30) && (y >= 0 && y < 30))
			matrixAlreadyVisited[x][y]++;

	}

	public int getTimesIVisitedThisPoint(int action) {

		int i = 0, j = 0;
		Point matrixHash[][] = new Point[5][5];

		matrixHash[1][2] = new Point(-1, 0);
		matrixHash[2][1] = new Point(0, -1);
		matrixHash[2][3] = new Point(0, +1);
		matrixHash[3][2] = new Point(+1, 0);

		if (action == 1) {
			i = 1;
			j = 2;
		}
		if (action == 2) {
			i = 3;
			j = 2;
		}
		if (action == 3) {
			i = 2;
			j = 3;
		}
		if (action == 4) {
			i = 2;
			j = 1;
		}

		int x = (int) (sensor.getPosicao().getY() + matrixHash[i][j].getX());
		int y = (int) (sensor.getPosicao().getX() + matrixHash[i][j].getY());

		if ((x >= 0 && x < 30) && (y >= 0 && y < 30))
			return matrixAlreadyVisited[x][y];
		else
			return 0;
	}

	public int distanceManhattan(int x1, int y1, int x2, int y2) {
		return (Math.abs(x1 - x2) + Math.abs(y1 - y2));
	}
}