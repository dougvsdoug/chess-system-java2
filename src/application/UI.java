package application;

import chess.ChessPiece;

public class UI {
	
	//nessa classe será criada um método para imprimir as peças da partida
	// UI = user interface
	
	public static void printBoard( ChessPiece[][] pieces ) {
		
		// esse método imprime o tabuleiro 
		
		for( int i = 0; i < pieces.length; i++ ) {
			System.out.print( (8-i) + " " );
			for( int j = 0; j < pieces.length; j++ ) {// consideramos q a matriz é quadrada
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}
		
		System.out.println("  a b c e f g h");
		
	}
	
	private static void printPiece( ChessPiece piece ) {
		//esse método imprime uma única peça
		
		if(piece == null ) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		
		System.out.print(" ");
		
	}
}
