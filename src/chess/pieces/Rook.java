package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
	public boolean[][] possibleMoves() {// retorna uma matriz com todos os movimentos possíveis da peça
		//Obs: é obrigatório implementar esse método pq ele é um método herdado q era abstrato
		// Obs: por padrão todas as posições de uma matriz de boolean começam com falso
		
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];// pelo q entendi no
		//getBoard().getRows, getBoard retorna um objeto da classe Board e nós utilizamos o método
		// getRows desse objeto da classe Board
		// getBoard foi herdado da classe Piece
	
		Position p = new Position(0, 0);// p é uma var auxiliar, ela começa com 0 pq é um valor qualquer
		//eles serão mudados depois, pelo método setValues
		
		//above
		p.setValues( position.getRow() - 1, position.getColumn() );// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow( p.getRow() - 1 );// vai subindo no tabuleiro
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//bellow
		p.setValues( position.getRow() + 1, position.getColumn() );// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow( p.getRow() + 1 );// vai descendo no tabuleiro
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//right
		p.setValues( position.getRow(), position.getColumn() + 1 );// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn( p.getColumn() + 1 );// vai indo para a direita no tabuleiro
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left
		p.setValues( position.getRow(), position.getColumn() - 1 );// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn( p.getColumn() - 1 );// vai indo para a esquerda no tabuleiro
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
}
