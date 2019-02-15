package chess;

import boardgame.Board;

public class ChessMatch {
	// essa classe possui as regras do jogo de xadrez
	
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessMatch() {
		board = new Board(8, 8);
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
	
	
}
