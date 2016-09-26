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
 * @author João Pedro
 *
 */
@SuppressWarnings(value = { "all" })
public class Ladrao extends ProgramaLadrao {

	/**
	 * VARIÁVEIS QUE CONTROLAM A MOVIMENTAÇÃO DO AGENTE-LADRÃO (VER PÁGINA 4 DO
	 * MANUAL DO JOGO)
	 */
	protected final int MOVEMENT_STOP 	= 0;
	protected final int MOVEMENT_UP 	= 1;
	protected final int MOVEMENT_DOWN 	= 2;
	protected final int MOVEMENT_RIGHT 	= 3;
	protected final int MOVEMENT_LEFT 	= 4;

	/**
	 * VARIÁVEIS REPRESENTANDO OS VALORES DE OFALTO DO AGENTE-LADRÃO (VIDE
	 * TABELA 01 DO MANUAL)
	 */
	private final int VISION_BLIND 		= -2;
	private final int VISION_OUTSIDE 	= -1;
	private final int VISION_EMPTY 		= 0;
	private final int VISION_WALL 		= 1;
	private final int VISION_BANK 		= 3;
	private final int VISION_COIN 		= 4;
	private final int VISION_POWER_UP 	= 5;
	private final int VISION_SAVER 		= 100;
	private final int VISION_THIEF 		= 200;

	/**
	 * VARIÁVEIS REPRESENTANDO OS VALORES DE FEROMÔNIO DO AGENTE-LADRÃO (VIDE
	 * TABELA 02 DO MANUAL)
	 */
	private final int PHEREOMONE_EMPTY 				= 0;
	private final int PHEREOMONE_ONE_STEP_AGO 		= 1;
	private final int PHEREOMONE_TWO_STEPS_AGO		= 2;
	private final int PHEREOMONE_THREE_STEPS_AGO 	= 3;
	private final int PHEREOMONE_FOUR_STEPS_AGO 	= 4;
	private final int PHEREOMONE_FIVE_STEPS_AGO 	= 5;
	
	
	
	int ultimaAcaoAgente 			= 0;
	int matrizVisaoAgente[][] 		= new int[5][5];
	int matrizOfaltoAgente[][] 		= new int[3][3];
	int matrizCasasJáVisitadas[][] 	= new int[30][30];

	/**
	 * ADICIONANDO PESOS CONFORME A ORIENTAÇÃO DO RAFAEL NA ÚLTIMA AULA.
	 * SEGUNDO ELE, O PESO AJUDA O AGENTE A DECIDIR O QUE É MELHOR FAZER NO STADO ATUAL
	 */
	private final int OBJETIVOS 	= 900;
	private final int OBSTACULOS	= -1600;
	private final int MOEDA 		= 1;
	private final int ESPACO_VAZIO 	= 3;
	private final int mate 			= 110;
	private final int ULTIMA_ACAO 	= -350;
	private final int wall 			= -2;
	private final int outOfWorld 	= -3;
	private final int bank 			= 1500;
	private final int powerCoin 	= -1;
	private final int smellConstant = 30;
	private final int samePointIwas = -18;

	@Override
	public int acao() {

		HashMap<Integer, Integer> hashMap 	= new HashMap<Integer, Integer>();
		ArrayList<Integer> arrayList 		= new ArrayList<Integer>();

		matrizVisaoAgente 	= getVisionMatrix();
		matrizOfaltoAgente 	= getSmellMatrix();

		if (ultimaAcaoAgente == MOVEMENT_STOP) {
			for (int i = 0; i < 30; i++) {
				for (int j = 0; j < 30; j++) {
					matrizCasasJáVisitadas[i][j] = 0;
				}
			}

		}

		hashMap.put(calculateTheFrontSide	(matrizVisaoAgente, matrizOfaltoAgente), 1);
		hashMap.put(calculateTheBackSide	(matrizVisaoAgente, matrizOfaltoAgente), 2);
		hashMap.put(calculateTheRightSide	(matrizVisaoAgente, matrizOfaltoAgente), 3);
		hashMap.put(calculateTheLeftSide	(matrizVisaoAgente, matrizOfaltoAgente), 4);

		arrayList.add(calculateTheLeftSide	(matrizVisaoAgente, matrizOfaltoAgente));
		arrayList.add(calculateTheRightSide	(matrizVisaoAgente, matrizOfaltoAgente));
		arrayList.add(calculateTheBackSide	(matrizVisaoAgente, matrizOfaltoAgente));
		arrayList.add(calculateTheFrontSide	(matrizVisaoAgente, matrizOfaltoAgente));

		Collections.sort(arrayList);

		int action = hashMap.get(arrayList.get(arrayList.size() - 1));
		ultimaAcaoAgente = action;

		includeVisitedPoint(action);

		return action;
	}

	public int calculateTheFrontSide(int[][] matrixVision, int[][] matrixSmell) {
		int sum = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				// ADICIONAR O VALOR DA PAREDE CASO TENHA UM LADRAO A FRENTE.
				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j++) {

				if (matrixSmell[i][j] != 0)
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
			}

		}

		if (matrixVision[1][2] != 0)
			sum += OBSTACULOS;

		if (matrixVision[1][2] >= 100 && matrixVision[1][2] < 200)
			sum -= OBSTACULOS;

		if (ultimaAcaoAgente == 2)
			sum += ULTIMA_ACAO;

		sum += (getTimesIVisitedThisPoint(1) * samePointIwas);

		return sum;

	}

	public int calculateTheBackSide(int[][] matrixVision, int[][] matrixSmell) {
		int sum = 0;

		for (int i = 3; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 2; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (matrixSmell[i][j] != 0)
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
			}

		}

		if (matrixVision[3][2] != 0)
			sum += OBSTACULOS;

		if ((matrixVision[3][2] >= 100 && matrixVision[3][2] < 200))
			sum -= OBSTACULOS;

		if (ultimaAcaoAgente == 1)
			sum += ULTIMA_ACAO;

		sum += getTimesIVisitedThisPoint(2) * samePointIwas;

		return sum;
	}

	public int calculateTheLeftSide(int[][] matrixVision, int[][] matrixSmell) {
		int sum = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2; j++) {

				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 1; j++) {

				if (matrixSmell[i][j] != 0)
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
			}

		}

		if (matrixVision[2][1] != 0)
			sum += OBSTACULOS;

		if ((matrixVision[2][1] >= 100 && matrixVision[2][1] < 200))
			sum -= OBSTACULOS;

		if (ultimaAcaoAgente == 3)
			sum += ULTIMA_ACAO;

		sum += getTimesIVisitedThisPoint(4) * samePointIwas;

		return sum;

	}

	public int calculateTheRightSide(int[][] matrixVision, int[][] matrixSmell) {
		int sum = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 3; j < 5; j++) {

				sum += getTheValueOfTheConst(matrixVision[i][j]);
			}

		}

		for (int i = 0; i < 3; i++) {
			for (int j = 2; j < 3; j++) {

				if (matrixSmell[i][j] != 0)
					sum += (1.0 / matrixSmell[i][j]) * smellConstant;
			}

		}

		if (matrixVision[2][3] != 0)
			sum += OBSTACULOS;

		if ((matrixVision[2][3] >= 100 && matrixVision[2][3] < 200))
			sum -= OBSTACULOS;

		if (ultimaAcaoAgente == 4)
			sum += ULTIMA_ACAO;

		sum += getTimesIVisitedThisPoint(3) * samePointIwas;

		return sum;

	}

	public int getTheValueOfTheConst(int f) {

		if (f == 0)
			return ESPACO_VAZIO;

		if (f >= 100 && f < 200)
			return OBJETIVOS;

		if (f >= 200 && f < 300)
			return mate;

		if (f == 4)
			return MOEDA;

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
		System.out.println(array.length);
		int indexArray = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				if (indexArray == 12) {
					conversionMatrix[i][j] = 100;
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
		System.out.println(array.length);
		int indexArray = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (indexArray == 4) {
					conversionMatrix[i][j] = 100;
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
			matrizCasasJáVisitadas[x][y]++;

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
			return matrizCasasJáVisitadas[x][y];
		else
			return 0;
	}
}