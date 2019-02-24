package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch ) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	/*----------------------------------------------------------------------------------------------------*/
	
	@Override
	public String toString() {
		return("P");
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
			
			
			// #special move en passant white; en passant da pe�a (pe�o ) branco matando o advers�rio preto
			
			if( position.getRow() == 3 ) {// o pe�o s� pode realizar o enPassant na linha 3, acho q esse if
				// � desnecess�rio; na verdade acho q ele serve para nao poder dar enPassant no peao do pr�prio
				// current player (matar o pr�prio pe�o)
				
				//enPassant left
				Position left = new Position( position.getRow(), position.getColumn() - 1 );
				if( getBoard().positionExists(left) && isThereOpponentPiece(left) &&
						getBoard().piece(left) == chessMatch.getEnPassantVunerable() ) {
					mat[left.getRow() - 1][left.getColumn()] = true;// o peao move para a pe�a de cima do peao do
					//advers�rio q ele captura
				}
				
				//enPassant right
				Position right = new Position( position.getRow(), position.getColumn() + 1 );
				if( getBoard().positionExists(right) && isThereOpponentPiece(right) &&
						getBoard().piece(right) == chessMatch.getEnPassantVunerable() ) {
					mat[right.getRow() - 1][right.getColumn()] = true;// o peao move para a pe�a de cima do peao do
					//advers�rio q ele captura
				}
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
			
			
			// #special move en passant black; en passant da pe�a (pe�o ) preto matando o advers�rio branco
			
			if( position.getRow() == 4 ) {// o pe�o s� pode realizar o enPassant na linha 4, acho q esse if
				// � desnecess�rio; na verdade acho q ele serve para nao poder dar enPassant no peao do pr�prio
				// current player (matar o pr�prio pe�o)
				
				//enPassant left
				Position left = new Position( position.getRow(), position.getColumn() - 1 );
				
				/*----------------------------------------------*/
/*				
				if( chessMatch == null ) {
					System.out.println("chessMatch vale null");
				}
				
				if(chessMatch.getEnPassantVunerable() == null ) {
					System.out.println("null");
				}
				System.out.println(chessMatch.getEnPassantVunerable());*/
				
				/*---------------------------------------------------------*/
				
				if( getBoard().positionExists(left) && isThereOpponentPiece(left) &&
						getBoard().piece(left) == chessMatch.getEnPassantVunerable() ) {
					mat[left.getRow() + 1][left.getColumn()] = true;// o peao move para a pe�a de baixo do peao
					//do advers�rio q ele captura
				}
				
				//enPassant right
				Position right = new Position( position.getRow(), position.getColumn() + 1 );
				if( getBoard().positionExists(right) && isThereOpponentPiece(right) &&
						getBoard().piece(right) == chessMatch.getEnPassantVunerable() ) {
					mat[right.getRow() + 1][right.getColumn()] = true;// o peao move para a pe�a de baixo do peao
					//do advers�rio q ele captura
				}
			}
		}
		
		return mat;
		
	}

}
