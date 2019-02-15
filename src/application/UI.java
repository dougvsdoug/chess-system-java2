package application;

import chess.ChessPiece;

public class UI {
	
	//nessa classe ser� criada um m�todo para imprimir as pe�as da partida
	// UI = user interface
	
	public static void printBoard( ChessPiece[][] pieces ) {
		
		// esse m�todo imprime o tabuleiro 
		
		for( int i = 0; i < pieces.length; i++ ) {
			System.out.print( (8-i) + " " );
			for( int j = 0; j < pieces.length; j++ ) {// consideramos q a matriz � quadrada
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}
		
		System.out.println("  a b c e f g h");
		
	}
	
	private static void printPiece( ChessPiece piece ) {
		//esse m�todo imprime uma �nica pe�a
		
		if(piece == null ) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		
		System.out.print(" ");
		
	}
}
