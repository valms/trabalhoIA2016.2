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

	/*private int[] agentVision;
	private int[] agentSmell;
	private Point posicao = new Point();*/
	

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
	protected final int VISION_BLIND 		= -2;
	protected final int VISION_OUTSIDE 		= -1;
	protected final int VISION_EMPTY 		= 0;
	protected final int VISION_WALL 		= 1;
	protected final int VISION_BANK 		= 3;
	protected final int VISION_COIN 		= 4;
	protected final int VISION_POWER_UP 	= 5;
	protected final int VISION_SAVER 		= 100;
	protected final int VISION_THIEF 		= 200;

	/**
	 * VARIÁVEIS REPRESENTANDO OS VALORES DE FEROMÔNIO DO AGENTE-LADRÃO (VIDE
	 * TABELA 02 DO MANUAL)
	 */
	protected final int PHEREOMONE_EMPTY 			= 0;
	protected final int PHEREOMONE_ONE_STEP_AGO 	= 1;
	protected final int PHEREOMONE_TWO_STEPS_AGO	= 2;
	protected final int PHEREOMONE_THREE_STEPS_AGO 	= 3;
	protected final int PHEREOMONE_FOUR_STEPS_AGO 	= 4;
	protected final int PHEREOMONE_FIVE_STEPS_AGO 	= 5;
	

	public int acao() {
		System.out.println("-------------");
		for (int i = 0; i < sensor.getVisaoIdentificacao().length; i++) {
			
			System.out.println("Visão " + sensor.getVisaoIdentificacao()[i]);
		}
		System.out.println("-------------");
		
		Estado estado = new Estado();
		
		
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return (int) (Math.random() * 5);
	}

}