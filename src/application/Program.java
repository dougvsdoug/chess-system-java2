package application;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();//lista de pe�as capturadas (mortas)
		
		while ( !chessMatch.getCheckMate() ) {// enquanto a partida n�o estiver com cheque-mate
			
			try {

				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves );// o getPiece retorna uma matriz 
				// de ChessPiece para o UI.printboard imprimir
				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				if( capturedPiece != null ) {
					captured.add(capturedPiece);
				}
				
				if( chessMatch.getPromoted() != null ) {// a jogada promotion est� cheia de bugs
					System.out.println("Enter piece for promotion (B/N/R/Q): ");// acho q aqui pode ter alguns bugs
					// ao testar as condi��es de cheque porque o programa vai testar as condi��es de cheque para
					// a rainha independente de qual pe�a o usu�rio para o pe�o ser promovido
					String type = sc.nextLine();
					type = type.toUpperCase();// para nao dar problema caso o usu�rio digite uma letra min�scula
					chessMatch.replacePromotedPiece(type);
				}
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();// aguarda o usu�rio precionar enter
			}catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();// aguarda o usu�rio precionar enter
			}catch (IllegalStateException e) {
				System.out.println(e.getMessage());
				sc.nextLine();// aguarda o usu�rio precionar enter
			}catch (InvalidParameterException e) {
				System.out.println(e.getMessage());
				sc.nextLine();// aguarda o usu�rio precionar enter
			}
			
			//acho q poderia colocar mais catchs, acho q faltou para os erros da specialmove promotion
		}
		//Terminou a partida
		
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
		
	}
}
