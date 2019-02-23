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
	public boolean[][] possibleMoves() {// retorna uma matriz com todos os movimentos poss�veis da pe�a
		//Obs: � obrigat�rio implementar esse m�todo pq ele � um m�todo herdado q era abstrato
		// Obs: por padr�o todas as posi��es de uma matriz de boolean come�am com falso
		
		//OBS: C�DIGO COPIADO DA CLASSE TORRE TALVEZ OS COMENT�RIOS ESTAJAM ESTRANHOS
		
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];// pelo q entendi no
		//getBoard().getRows, getBoard retorna um objeto da classe Board e n�s utilizamos o m�todo
		// getRows desse objeto da classe Board
		// getBoard foi herdado da classe Piece
	
		Position p = new Position(0, 0);// p � uma var auxiliar, ela come�a com 0 pq � um valor qualquer
		//eles ser�o mudados depois, pelo m�todo setValues
		
		//northwest
		p.setValues( position.getRow() - 1, position.getColumn() - 1);// position.get � a posi��o de origem da pe�a
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() - 1, p.getColumn() - 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// tamb�m � poss�vel mover em cima da PRIMEIRA
			// pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//northeast
		p.setValues( position.getRow() - 1, position.getColumn() + 1 );// position.get � a posi��o de origem da pe�a
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() - 1, p.getColumn() + 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// tamb�m � poss�vel mover em cima da PRIMEIRA
			// pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//south-east
		p.setValues( position.getRow() + 1, position.getColumn() + 1 );// position.get � a posi��o de origem da pe�a
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() + 1, p.getColumn() + 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// tamb�m � poss�vel mover em cima da PRIMEIRA
			// pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//south-west
		p.setValues( position.getRow() + 1, position.getColumn() - 1 );// position.get � a posi��o de origem da pe�a
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues( p.getRow() + 1, p.getColumn() - 1);
		}
		if( getBoard().positionExists(p) && isThereOpponentPiece(p)){// tamb�m � poss�vel mover em cima da PRIMEIRA
			// pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return("B");
	}
}
