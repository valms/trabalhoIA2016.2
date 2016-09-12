package algoritmo;

import java.awt.Point;

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

	private int[] agentVision;
	private int[] agentSmell;
	private Point posicao = new Point();
	

	/**
	 * VARIÁVEIS QUE CONTROLAM A MOVIMENTAÇÃO DO AGENTE-LADRÃO (VER PÁGINA 4 DO
	 * MANUAL DO JOGO)
	 */
	private final int MOVEMENT_STOP 	= 0;
	private final int MOVEMENT_UP 		= 1;
	private final int MOVEMENT_DOWN 	= 2;
	private final int MOVEMENT_RIGHT 	= 3;
	private final int MOVEMENT_LEFT 	= 4;

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
	private final int PHEREOMONE_TWO_STEPS_AGO 		= 2;
	private final int PHEREOMONE_THREE_STEPS_AGO 	= 3;
	private final int PHEREOMONE_FOUR_STEPS_AGO 	= 4;
	private final int PHEREOMONE_FIVE_STEPS_AGO 	= 5;

	public int acao() {
		agentSmell = sensor.getAmbienteOlfatoLadrao();
		agentVision = sensor.getVisaoIdentificacao();
		sensor.getAmbienteOlfatoPoupador();

		for (int i = 0; i < agentSmell.length; i++) {

			switch (agentSmell[i]) {
			case PHEREOMONE_EMPTY:
				System.out.println("PHEREOMONE_EMPTY");

				break;
			case PHEREOMONE_ONE_STEP_AGO:
				System.out.println("PHEREOMONE_ONE_STEP_AGO");
				break;
			case PHEREOMONE_TWO_STEPS_AGO:
				System.out.println("PHEREOMONE_TWO_STEPS_AGO");
				break;
			case PHEREOMONE_THREE_STEPS_AGO:
				System.out.println("PHEREOMONE_THREE_STEPS_AGO");
				break;

			case PHEREOMONE_FIVE_STEPS_AGO:
				System.out.println("PHEREOMONE_FIVE_STEPS_AGO");
				break;
			default:
				break;
			}

			System.out.println("Índice " + i + " - Item: " + agentSmell[i]);
		}

		if (agentSmell[0] == PHEREOMONE_EMPTY) {
			System.out.println("Sem traço");

		} else {
			System.out.println("Opa");

		}
		
		return (int) (Math.random() * 5);
	}

	public int getMOVEMENT_STOP() {
		return MOVEMENT_STOP;
	}

	public int getMOVEMENT_UP() {
		return MOVEMENT_UP;
	}

	public int getMOVEMENT_DOWN() {
		return MOVEMENT_DOWN;
	}

	public int getMOVEMENT_RIGHT() {
		return MOVEMENT_RIGHT;
	}

	public int getMOVEMENT_LEFT() {
		return MOVEMENT_LEFT;
	}
}