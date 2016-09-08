package algoritmo;

/**
 * UNIVERSIDADE DE FORTALEZA - I.A. 2016.2
 * 
 * @author Valmar J�nior - 1120793 / 6
 * @author Jo�o Pedro
 *
 */
@SuppressWarnings(value = { "all" })
public class Ladrao extends ProgramaLadrao {

	/**
	 * VARI�VEIS QUE CONTROLAM A A��O DO AGENTE-LADR�O (VER P�GINA 4 DO MANUAL
	 * DO JOGO)
	 */
	private final int MOVEMENT_PARADO 	= 0;
	private final int MOVEMENT_UP 		= 1;
	private final int MOVEMENT_DOWN 	= 2;
	private final int MOVEMENT_RIGHT 	= 3;
	private final int MOVEMENT_LEFT 	= 4;

	/**
	 * VARI�VEIS REPRESENTANDO OS VALORES DE OFALTO DO AGENTE-LADR�O (VIDE
	 * TABELA 01 DO MANUAL)
	 */
	private final int VISION_BLIND 		= -2;
	private final int VISION_OUTSIDE	= -1;
	private final int VISION_EMPTY 		= 0;
	private final int VISION_WALL 		= -2;
	private final int VISION_BANK 		= -2;
	private final int VISION_COIN 		= -2;
	private final int VISION_POWER_UP 	= -2;
	private final int VISION_SAVER 		= -2;
	private final int VISION_THIEF 		= -2;
	
	/**
	 * VARI�VEIS REPRESENTANDO OS VALORES DE FEROM�NIO DO AGENTE-LADR�O (VIDE
	 * TABELA 02 DO MANUAL)
	 */

	private final int PHEREOMONE_EMPTY 				= 0;
	private final int PHEREOMONE_ONE_STEP_AGO 		= 1;
	private final int PHEREOMONE_TWO_STEPS_AGO 		= 2;
	private final int PHEREOMONE_THREE_STEPS_AGO 	= 3;
	private final int PHEREOMONE_FOUR_STEPS_AGO 	= 4;
	private final int PHEREOMONE_FIVE_STEPS_AGO 	= 5;
	

	public int acao() {

		System.out.println(sensor.getPosicao());
		// for (int i = 0; i < sensor.getVisaoIdentificacao().length; i++) {
		// System.out.println();
		// System.out.println(sensor.getVisaoIdentificacao()[i]);
		// }
		return (int) (Math.random() * 5);
	}

}