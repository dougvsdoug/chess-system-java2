package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King( Board board, Color color ) {
		super(board, color);
	}
	
	/*------------------------------------------------------------------------------------------------------*/
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove( Position position ) {//diz se o rei pode mover para uma determinada posi��o
		ChessPiece p = (ChessPiece) getBoard().piece(position);//note q a posi��o j� foi validada pelo m�todo
		// piece
		return p == null || p.getColor() != getColor();// acho q aqui se p == null ele nem testa o p.getcolor
		// acho q daria NullPointerException
	}
	
	@Override
	public boolean[][] possibleMoves() {// retorna uma matriz com todos os movimentos poss�veis da pe�a
		//Obs: � obrigat�rio implementar esse m�todo pq ele � um m�todo herdado q era abstrato
		// Obs: por padr�o todas as posi��es de uma matriz de boolean come�am com falso
		
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];// pelo q entendi no
		//getBoard().getRows, getBoard retorna um objeto da classe Board e n�s utilizamos o m�todo
		// getRows desse objeto da classe Board
		// getBoard foi herdado da classe Piece
		// note q quando instancia uma matriz de boolean, as posi�es s�o preenchidas com false por padr�o
	
		Position p = new Position(0, 0);// p � uma var auxiliar, ela come�a com 0 pq � um valor qualquer
		//eles ser�o mudados depois, pelo m�todo setValues
		
		//above
		
		p.setValues( position.getRow() - 1, position.getColumn() );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//below
		
		p.setValues( position.getRow() + 1, position.getColumn() );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//left

		p.setValues( position.getRow(), position.getColumn() - 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	

		//right

		p.setValues( position.getRow(), position.getColumn() + 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}			
		
		//northwest

		p.setValues( position.getRow() - 1, position.getColumn() - 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//northeast

		p.setValues( position.getRow() - 1, position.getColumn() + 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//south-west

		p.setValues( position.getRow() + 1, position.getColumn() - 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//south-east

		p.setValues( position.getRow() + 1, position.getColumn() + 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		return mat;
	}
}
