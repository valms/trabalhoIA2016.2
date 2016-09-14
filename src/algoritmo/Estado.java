package algoritmo;

/**
 * Classe que representa o estado, tanto atual, tanto o sucessor para o cenário
 * apresentado
 * 
 * 
 * @version 1.0 ALPHA
 * 
 * @author Valmar Júnior - 1120793 / 6
 * @author João Pedro
 *
 */

public class Estado extends Ladrao {

	public Estado() {

	}

	public Estado novoEstado(int movimentoAdotado) {

		switch (movimentoAdotado) {
		
		case MOVEMENT_STOP:

			break;

		case MOVEMENT_UP:

			break;
		case MOVEMENT_DOWN:

			break;
		case MOVEMENT_LEFT:

			break;
		case MOVEMENT_RIGHT:

			break;
		}

		return null;
	}

	public void funcaoSucessora(Estado estado) {

	}

}
