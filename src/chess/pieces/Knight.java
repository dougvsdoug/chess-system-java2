package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{
	//OBS: C�DIGO COPIADO DA CLASSE KING TALVEZ OS COMENT�RIOS ESTAJAM ESTRANHOS

	public Knight( Board board, Color color ) {
		super(board, color);
	}
	
	/*------------------------------------------------------------------------------------------------------*/
	
	@Override
	public String toString() {
		return "N";
	}
	
	private boolean canMove( Position position ) {//diz se o rei pode mover para uma determinada posi��o
		ChessPiece p = (ChessPiece) getBoard().piece(position);//note q a posi��o j� foi validada pelo m�todo
		// piece
		return p == null || p.getColor() != getColor();// acho q aqui se p == null ele nem testa o p.getcolor
		// acho q daria NullPointerException
		// o segundo teste desse return � para testar se ape�a � de um oponente
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
		
		//above-left
		
		p.setValues( position.getRow() - 2, position.getColumn() - 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//above-right
		
		p.setValues( position.getRow() - 2, position.getColumn() + 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//bellow-left

		p.setValues( position.getRow() + 2, position.getColumn() - 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	

		//bellow-right

		p.setValues( position.getRow() + 2, position.getColumn() + 1 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}			
		
		//left-above

		p.setValues( position.getRow() - 1, position.getColumn() - 2 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//left-bellow

		p.setValues( position.getRow() + 1, position.getColumn() -2  );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//right-above

		p.setValues( position.getRow() - 1, position.getColumn() + 2 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//right-bellow

		p.setValues( position.getRow() + 1, position.getColumn() + 2 );// position.get � a posi��o de origem da pe�a
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		return mat;
	}
}
