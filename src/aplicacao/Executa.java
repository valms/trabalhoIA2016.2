package aplicacao;

import gui.FramePrincipal;

public class Executa {

	public static void main(String[] args) {

		/*
		 * int[] src = new int[]{1,2,3,4,5}; int[] dest = new int[5];
		 * 
		 * System.arraycopy( src, 0, dest, 0, src.length );
		 * 
		 * for (int i = 0; i < dest.length; i++) { System.out.println(dest[i]);
		 * }
		 */
		
		FramePrincipal jogo = new FramePrincipal();
		jogo.iniciarJogo();
	}
}
