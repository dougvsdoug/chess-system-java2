package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	// essa classe possui as regras do jogo de xadrez
	
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();//note q quando cria a partida já faz o initial setup
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessPiece[][] getPiece(){
		// esse método retorna uma matriz de peças de xadrez correspondentes a essa partida
		//note q o programa (acho q o ChessMatch) só tera acesso ao ChessPiece e nao ao Piece
		//O programa irá conhecer apenas a camada de xadrez e nao a camada de tabuleiro
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i <board.getRows(); i++ ) {
			for(int j = 0; j<board.getColumns(); j++ ) {
				mat[i][j] = (ChessPiece) board.piece(i, j);// note que foi necessário fazer um cast para poder 
				//fazer o downcasting
			}
		}
		
		return mat;
	}
	
	private void initialSetup() {
		// esse método inicia a partida de xadrez, colocando as peças no tabuleiro
		
		board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
	}
	
}
