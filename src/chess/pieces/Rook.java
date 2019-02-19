package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {
		super(board, color);
		
	}

	/*--------------------------------------------------------------------------------------------------*/
	
	@Override
	public String toString() {
		return "R";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		//Obs: é obrigatório implementar esse método pq ele é um método herdado q era abstrato
		// Obs: por padrão todas as posições de uma matriz de boolean começam com falso
		
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];// pelo q entendi no
		//getBoard().getRows, getBoard retorna um objeto da classe Board e nós utilizamos o método
		// getRows desse objeto da classe Board
		// getBoard foi herdado da classe Piece
		return mat;
	}
}
