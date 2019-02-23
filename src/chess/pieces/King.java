package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;
	
	/*------------------------------------------------------------------------------------------------------*/
	
	public King( Board board, Color color, ChessMatch chessmatch ) {
		super(board, color);
		this.chessMatch = chessmatch;
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
		// o segundo teste desse return � para testar se ape�a � de um oponente
	}
	
	private boolean testRookCastling(Position position) {// testa se a torre est� apta para o Roque
		// Roque � Castling em ingl�s
		
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;// acno q
		// se a p for igual a null ele nem faz os outros testes pq daria um NullPointerException
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
		
		// # Specialmove castling
		
		if( getMoveCount() == 0 && !chessMatch.getCheck() ) {// parece q o rei nao pode ter sido movido para 
			// poder dar o Roque
			//OBS: nao sei pq precisa testar se o rei est� em cheque acho q essa condi��o est� errada
			
			// #Castling kingside rook (Roque para a direita)
			// � a mesma coisa para as duas cores de jogador
			
			Position posT1 = new Position( position.getRow(), position.getColumn() + 3 );// posi��o da torre da
			// direita
			
			if( testRookCastling(posT1) ) {
				//vamos testar se as duas casas a direita do rei est�o vazias
				Position p1 = new Position( position.getRow(), position.getColumn() + 1 );
				Position p2 = new Position( position.getRow(), position.getColumn() + 2 );
				
				if( getBoard().piece(p1) == null && getBoard().piece(p2) == null ) {//as posi��es entre o rei e
					// a torre est�o nulas
					mat[position.getRow()][position.getColumn() + 2 ] = true;
				}
			}
			
			
			// #Castling QueenSide rook (Roque para a esquerda)
			// � a mesma coisa para as duas cores de jogador
			
			Position posT2 = new Position( position.getRow(), position.getColumn() - 4 );// posi��o da torre da
			// esquerda
			
			if( testRookCastling(posT2) ) {
				//vamos testar se as tr�s casas a esquerda do rei est�o vazias
				Position p1 = new Position( position.getRow(), position.getColumn() - 1 );
				Position p2 = new Position( position.getRow(), position.getColumn() - 2 );
				Position p3 = new Position( position.getRow(), position.getColumn() - 3 );
				
				if( getBoard().piece(p1) == null && getBoard().piece(p2) == null && 
						getBoard().piece(p3)  == null ){//as posi��es entre o rei e
					// a torre est�o nulas
					mat[position.getRow()][position.getColumn() - 2 ] = true;
				}
			}
		}
		
		return mat;
	}
}
