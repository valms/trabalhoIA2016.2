package algoritmo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o estado, tanto atual, tanto o sucessor para o cenário
 * apresentado
 * 
 * @author Valmar Júnior - 1120793 / 6
 * @author João Pedro
 *
 */
class State {

	private int[] visionArray;

	private int[] smellThiefArray;

	private int[] smellSaverArray;

	private int coins;

	private Point point;

	private int util;

	private int moviment;

	private Ladrao thief;

	private SensoresLadrao thiefSensors;

	public State(int[] visionArray, int[] smellThiefArray, int[] smellSaverArray, int coins, Point point, int util,
			int moviment, SensoresLadrao ladrao) {

		this.visionArray = visionArray;
		this.smellThiefArray = smellThiefArray;
		this.smellSaverArray = smellSaverArray;
		this.coins = coins;
		this.point = point;
		this.util = util;
		this.moviment = moviment;
		this.thiefSensors = ladrao;

		this.thief = new Ladrao();
	}

	public int[] getVisionArray() {
		return visionArray;
	}

	public void setVisionArray(int[] visionArray) {
		this.visionArray = visionArray;
	}

	public int[] getSmellThiefArray() {
		return smellThiefArray;
	}

	public void setSmellThiefArray(int[] smellThiefArray) {
		this.smellThiefArray = smellThiefArray;
	}

	public int[] getSmellSaverArray() {
		return smellSaverArray;
	}

	public void setSmellSaverArray(int[] smellSaverArray) {
		this.smellSaverArray = smellSaverArray;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getUtil() {
		return util;
	}

	public void setUtil(int util) {
		this.util = util;
	}

	public int getMoviment() {
		return moviment;
	}

	public void setMoviment(int moviment) {
		this.moviment = moviment;
	}

	public State newState(int moviment) {

		int[] visionArray = new int[this.thiefSensors.getVisaoIdentificacao().length];
		int[] smellSaverArray = new int[this.thiefSensors.getAmbienteOlfatoPoupador().length];
		int[] smellThiefArray = new int[this.thiefSensors.getAmbienteOlfatoLadrao().length];

		
		
		
		System.arraycopy(this.thiefSensors.getVisaoIdentificacao(), 0, visionArray, 0,
				this.thiefSensors.getVisaoIdentificacao().length);

		System.arraycopy(this.thiefSensors.getAmbienteOlfatoPoupador(), 0, smellSaverArray, 0,
				this.thiefSensors.getAmbienteOlfatoPoupador().length);

		System.arraycopy(this.thiefSensors.getAmbienteOlfatoLadrao(), 0, smellThiefArray, 0,
				this.thiefSensors.getAmbienteOlfatoLadrao().length);

		return null;
	}

	public List<State> successorFunction(State actualState) {

		List<State> successor = new ArrayList<State>();

		// TODO TESTES
		for (int i = 0; i < 5; i++) {

			State successorState = null;

			switch (i) {
			case 0:

				break;
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;

			default:
				break;
			}

		}

		return null;
	}

}
