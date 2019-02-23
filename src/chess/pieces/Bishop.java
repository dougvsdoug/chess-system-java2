package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {// retorna uma matriz com todos os movimentos possíveis da peça
		//Obs: é obrigatório implementar esse método pq ele é um método herdado q era abstrato
		// Obs: por padrão todas as posições de uma matriz de boolean começam com falso
		
		//OBS: CÓDIGO COPIADO DA CLASSE TORRE TALVEZ OS COMENTÁRIOS ESTAJAM ESTRANHOS
		
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];// pelo q entendi no
		//getBoard().getRows, getBoard retorna um objeto da classe Board e nós utilizamos o método
		// getRows desse objeto da classe Board
		// getBoard foi herdado da classe Piece
	
		Position p = new Position(0, 0);// p é uma var auxiliar, ela começa com 0 pq é um valor qualquer
		//eles serão mudados depois, pelo método setValues
		
		//northwest
		p.setValues( position.getRow() - 1, position.getColumn() - 1);// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() - 1, p.getColumn() - 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//northeast
		p.setValues( position.getRow() - 1, position.getColumn() + 1 );// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() - 1, p.getColumn() + 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//south-east
		p.setValues( position.getRow() + 1, position.getColumn() + 1 );// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() + 1, p.getColumn() + 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//south-west
		p.setValues( position.getRow() + 1, position.getColumn() - 1 );// position.get é a posição de origem da peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() + 1, p.getColumn() - 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// também é possível mover em cima da PRIMEIRA
			// peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return("B");
	}
}
