package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}
	
	/*----------------------------------------------------------------------------------------------------*/
	
	@Override
	public String toString() {
		return("P");
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
	
		Position p = new Position(0, 0);// p � uma var auxiliar, ela come�a com 0 pq � um valor qualquer
		//eles ser�o mudados depois, pelo m�todo setValues

		
		if( getColor() == Color.WHITE ) {//OBS: O pe�o branco move para cima e o preto move para baixo
			
			// move 1 casa para frente
			p.setValues( position.getRow() - 1, position.getColumn() );
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {//lembrando q o pe�o s� 
				// pode andar para frente se n�o tiver nenhuma pe�a no caminho
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 2 casas para frente
			p.setValues( position.getRow() - 2, position.getColumn() );
			Position p2 = new Position( position.getRow() - 1, position.getColumn() );//para testar se n�o tem
			//nenhuma pe�a q impe�a o pe�o de mover duas casas
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) &&
					getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)
					&& getMoveCount() == 0) {//lembrando
				//q o pe�o s� pode andar para frente se n�o tiver nenhuma pe�a no caminho
				//o pe�o s� pode andar duas casas para frente se for o primeiro movimento dele
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal esquerda da frente (come pe�a)
			p.setValues( position.getRow() - 1, position.getColumn() - 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o pe�o  
				// s� pode andar para diagonal se tiver uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal direita da frente (come pe�a)
			p.setValues( position.getRow() - 1, position.getColumn() + 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o pe�o  
				// s� pode andar para diagonal se tiver uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
		}else {//caso o pe�o seja preto ele s� move para baixo
			
			// move 1 casa para baixo
			p.setValues( position.getRow() + 1, position.getColumn() );
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {//lembrando q o pe�o s� 
				// pode andar para frente se n�o tiver nenhuma pe�a no caminho
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 2 casas para baixo
			p.setValues( position.getRow() + 2, position.getColumn() );
			Position p2 = new Position( position.getRow() + 1, position.getColumn() );//para testar se n�o tem
			//nenhuma pe�a q impe�a o pe�o de mover duas casas
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) &&
					getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)
					&& getMoveCount() == 0) {//lembrando
				//q o pe�o s� pode andar para frente se n�o tiver nenhuma pe�a no caminho
				//o pe�o s� pode andar duas casas para frente se for o primeiro movimento dele
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal esquerda de baixo (come pe�a)
			p.setValues( position.getRow() + 1, position.getColumn() - 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o pe�o  
				// s� pode andar para diagonal se tiver uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal direita de baixo(come pe�a)
			p.setValues( position.getRow() + 1, position.getColumn() + 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o pe�o  
				// s� pode andar para diagonal se tiver uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;
		
	}

}
