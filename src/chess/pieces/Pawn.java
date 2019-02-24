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
			
			
			// #special move en passant white; en passant da peça (peão ) branco matando o adversário preto
			
			if( position.getRow() == 3 ) {// o peão só pode realizar o enPassant na linha 3, acho q esse if
				// é desnecessário; na verdade acho q ele serve para nao poder dar enPassant no peao do próprio
				// current player (matar o próprio peão)
				
				//enPassant left
				Position left = new Position( position.getRow(), position.getColumn() - 1 );
				if( getBoard().positionExists(left) && isThereOpponentPiece(left) &&
						getBoard().piece(left) == chessMatch.getEnPassantVunerable() ) {
					mat[left.getRow() - 1][left.getColumn()] = true;// o peao move para a peça de cima do peao do
					//adversário q ele captura
				}
				
				//enPassant right
				Position right = new Position( position.getRow(), position.getColumn() + 1 );
				if( getBoard().positionExists(right) && isThereOpponentPiece(right) &&
						getBoard().piece(right) == chessMatch.getEnPassantVunerable() ) {
					mat[right.getRow() - 1][right.getColumn()] = true;// o peao move para a peça de cima do peao do
					//adversário q ele captura
				}
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
			
			
			// #special move en passant black; en passant da peça (peão ) preto matando o adversário branco
			
			if( position.getRow() == 4 ) {// o peão só pode realizar o enPassant na linha 4, acho q esse if
				// é desnecessário; na verdade acho q ele serve para nao poder dar enPassant no peao do próprio
				// current player (matar o próprio peão)
				
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
					mat[left.getRow() + 1][left.getColumn()] = true;// o peao move para a peça de baixo do peao
					//do adversário q ele captura
				}
				
				//enPassant right
				Position right = new Position( position.getRow(), position.getColumn() + 1 );
				if( getBoard().positionExists(right) && isThereOpponentPiece(right) &&
						getBoard().piece(right) == chessMatch.getEnPassantVunerable() ) {
					mat[right.getRow() + 1][right.getColumn()] = true;// o peao move para a peça de baixo do peao
					//do adversário q ele captura
				}
			}
		}
		
		return mat;
		
	}

}
