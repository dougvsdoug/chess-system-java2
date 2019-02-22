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
	
	private boolean canMove( Position position ) {//diz se o rei pode mover para uma determinada posição
		ChessPiece p = (ChessPiece) getBoard().piece(position);//note q a posição já foi validada pelo método
		// piece
		return p == null || p.getColor() != getColor();// acho q aqui se p == null ele nem testa o p.getcolor
		// acho q daria NullPointerException
		// o segundo teste desse return é para testar se apeça é de um oponente
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

		
		if( getColor() == Color.WHITE ) {//OBS: O peão branco move para cima e o preto move para baixo
			
			// move 1 casa para frente
			p.setValues( position.getRow() - 1, position.getColumn() );
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {//lembrando q o peão só 
				// pode andar para frente se não tiver nenhuma peça no caminho
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 2 casas para frente
			p.setValues( position.getRow() - 2, position.getColumn() );
			Position p2 = new Position( position.getRow() - 1, position.getColumn() );//para testar se não tem
			//nenhuma peça q impeça o peão de mover duas casas
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) &&
					getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)
					&& getMoveCount() == 0) {//lembrando
				//q o peão só pode andar para frente se não tiver nenhuma peça no caminho
				//o peão só pode andar duas casas para frente se for o primeiro movimento dele
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal esquerda da frente (come peça)
			p.setValues( position.getRow() - 1, position.getColumn() - 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o peão  
				// só pode andar para diagonal se tiver uma peça adversária
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal direita da frente (come peça)
			p.setValues( position.getRow() - 1, position.getColumn() + 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o peão  
				// só pode andar para diagonal se tiver uma peça adversária
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
		}else {//caso o peão seja preto ele só move para baixo
			
			// move 1 casa para baixo
			p.setValues( position.getRow() + 1, position.getColumn() );
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {//lembrando q o peão só 
				// pode andar para frente se não tiver nenhuma peça no caminho
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 2 casas para baixo
			p.setValues( position.getRow() + 2, position.getColumn() );
			Position p2 = new Position( position.getRow() + 1, position.getColumn() );//para testar se não tem
			//nenhuma peça q impeça o peão de mover duas casas
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) &&
					getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)
					&& getMoveCount() == 0) {//lembrando
				//q o peão só pode andar para frente se não tiver nenhuma peça no caminho
				//o peão só pode andar duas casas para frente se for o primeiro movimento dele
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal esquerda de baixo (come peça)
			p.setValues( position.getRow() + 1, position.getColumn() - 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o peão  
				// só pode andar para diagonal se tiver uma peça adversária
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// move 1 casa para diagonal direita de baixo(come peça)
			p.setValues( position.getRow() + 1, position.getColumn() + 1 );
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) ) {//lembrando q o peão  
				// só pode andar para diagonal se tiver uma peça adversária
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;
		
	}

}
