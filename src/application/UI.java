package application;

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
	
	//nessa classe ser� criada um m�todo para imprimir as pe�as da partida
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
	
	public static ChessPosition readChessPosition(Scanner sc) {//L� uma posi��o do usu�rio
		//ele recebe um Scanner do programa principal
		
		try {
				
			String s = sc.nextLine();
			
			char column = s.charAt(0);// recebe a coluna
			
			int row = Integer.parseInt(s.substring(1));// pega o segundo membro da string e converte ele para int
			
			return new ChessPosition(column, row);
			
		}
		catch( RuntimeException e ) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
			// InputMismatchException � um tipo de exce��o q siginifica um erro de entrada de dados
 		}
		
	}
	
	public static void printMatch( ChessMatch chessMatch, List<ChessPiece> captured ) {
		printBoard( chessMatch.getPieces() );
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		
		if( !chessMatch.getCheckMate() ){
			System.out.println("Waiting player: " + chessMatch.getCurrentPlayer() );	
			if( chessMatch.getCheck() ) {
				System.out.println(ANSI_RED + "CHECK!!!!!!!" + ANSI_RESET);
			}
		}else{// caso tenha dado checkMate
		
			if( chessMatch.getCurrentPlayer() == Color.WHITE ) {
				System.out.println(ANSI_WHITE + "CHECKMATE" + ANSI_RESET);
				System.out.println(ANSI_WHITE + "Winner " + chessMatch.getCurrentPlayer() + ANSI_WHITE );
			}else {
				System.out.println(ANSI_GREEN + "CHECKMATE" + ANSI_RESET);// note que usamos a cor verde 
				//para as pe�as pretas
				System.out.println(ANSI_GREEN + "Winner " + chessMatch.getCurrentPlayer() + ANSI_WHITE );
			}
		}
	}
	
	public static void printBoard( ChessPiece[][] pieces ) {// imprime o tabuleiro sem pintar 
		// as posi��es poss�veis
		
		for( int i = 0; i < pieces.length; i++ ) {
			System.out.print( (8-i) + " " );
			for( int j = 0; j < pieces.length; j++ ) {// consideramos q a matriz � quadrada
				printPiece(pieces[i][j], false);// imprime o tabuleiro sem pintar as posi��es poss�veis
			}
			System.out.println();
		}
		
		System.out.println("  a b c d e f g h");
		 
	}
	
	public static void printBoard( ChessPiece[][] pieces, boolean[][] possibleMoves ) {// sobrecarga
		// imprime o tabuleiro pintando as posi��es poss�veis
		
		for( int i = 0; i < pieces.length; i++ ) {
			System.out.print( (8-i) + " " );
			for( int j = 0; j < pieces.length; j++ ) {// consideramos q a matriz � quadrada
				printPiece(pieces[i][j], possibleMoves[i][j] );
			}
			System.out.println();
		}
		
		System.out.println("  a b c d e f g h");
		
	}
	
	private static void printPiece( ChessPiece piece, boolean background ) {//esse m�todo imprime uma �nica pe�a
		
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
            	// note que usamos a cor verde para as pe�as pretas
                System.out.print(ANSI_GREEN+ piece + ANSI_RESET);
            }
		}
		
		System.out.print(" ");
		
	}
	
	private static void printCapturedPieces( List<ChessPiece> captured ) {// imprime as pe�as capturadas
		
		List <ChessPiece> white = captured.stream().filter( x -> x.getColor() == Color.WHITE ).collect
				(Collectors.toList());
		
		List <ChessPiece> black = captured.stream().filter( x -> x.getColor() == Color.BLACK ).collect
				(Collectors.toList());
		
		System.out.println("Captured pieces: ");
		
		//pe�as brancas
		System.out.print(ANSI_WHITE);// para garantir q a lista vai ser impressa na cor branca
		System.out.print("White: ");
		System.out.println(Arrays.toString(white.toArray()));// isso � uma forma padr�o de imprimir um Array 
		// de valores no java
		System.out.print(ANSI_RESET);
		
		//pe�as pretas
		System.out.print(ANSI_GREEN);// para garantir q a lista vai ser impressa na cor branca
		System.out.print("Black: ");
		// note que usamos a cor verde para as pe�as pretas
		System.out.println(Arrays.toString(black.toArray()));// isso � uma forma padr�o de imprimir um Array 
		// de valores no java
		System.out.print(ANSI_RESET);// note que usamos a cor verde para as pe�as pretas
	}
	

}
