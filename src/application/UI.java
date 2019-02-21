package application;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	//nessa classe será criada um método para imprimir as peças da partida
	// UI = user interface
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	/*-------------------------------------------------------------------------------------------------------*/
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}	
	
	public static ChessPosition readChessPosition(Scanner sc) {//Lê uma posição do usuário
		//ele recebe um Scanner do programa principal
		
		try {
				
			String s = sc.nextLine();
			
			char column = s.charAt(0);// recebe a coluna
			
			int row = Integer.parseInt(s.substring(1));// pega o segundo membro da string e converte ele para int
			
			return new ChessPosition(column, row);
			
		}
		catch( RuntimeException e ) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
			// InputMismatchException é um tipo de exceção q siginifica um erro de entrada de dados
 		}
		
	}
	
	public static void printMatch( ChessMatch chessMatch, List<ChessPiece> captured ) {
		printBoard( chessMatch.getPieces() );
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		System.out.println("Waiting player: " + chessMatch.getCurrentPlayer() );
	}
	
	public static void printBoard( ChessPiece[][] pieces ) {// imprime o tabuleiro sem pintar 
		// as posições possíveis
		
		for( int i = 0; i < pieces.length; i++ ) {
			System.out.print( (8-i) + " " );
			for( int j = 0; j < pieces.length; j++ ) {// consideramos q a matriz é quadrada
				printPiece(pieces[i][j], false);// imprime o tabuleiro sem pintar as posições possíveis
			}
			System.out.println();
		}
		
		System.out.println("  a b c d e f g h");
		 
	}
	
	public static void printBoard( ChessPiece[][] pieces, boolean[][] possibleMoves ) {// sobrecarga
		// imprime o tabuleiro pintando as posições possíveis
		
		for( int i = 0; i < pieces.length; i++ ) {
			System.out.print( (8-i) + " " );
			for( int j = 0; j < pieces.length; j++ ) {// consideramos q a matriz é quadrada
				printPiece(pieces[i][j], possibleMoves[i][j] );
			}
			System.out.println();
		}
		
		System.out.println("  a b c d e f g h");
		
	}
	
	private static void printPiece( ChessPiece piece, boolean background ) {//esse método imprime uma única peça
		
		if( background ) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		
		if(piece == null ) {	
			System.out.print("-" + ANSI_RESET);
			
		}
		else {
			
			if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
            	// note que usamos a cor amarela para as peças pretas
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
		}
		
		System.out.print(" ");
		
	}
	
	private static void printCapturedPieces( List<ChessPiece> captured ) {// imprime as peças capturadas
		
		List <ChessPiece> white = captured.stream().filter( x -> x.getColor() == Color.WHITE ).collect
				(Collectors.toList());
		
		List <ChessPiece> black = captured.stream().filter( x -> x.getColor() == Color.BLACK ).collect
				(Collectors.toList());
		
		System.out.println("Captured pieces: ");
		
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);// para garantir q a lista vai ser impressa na cor branca
		System.out.println(Arrays.toString(white.toArray()));// isso é uma forma padrão de imprimir um Array 
		// de valores no java
		System.out.print(ANSI_RESET);
		
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);// para garantir q a lista vai ser impressa na cor branca
		System.out.println(Arrays.toString(black.toArray()));// isso é uma forma padrão de imprimir um Array 
		// de valores no java
		System.out.print(ANSI_RESET);
	}
	

}
