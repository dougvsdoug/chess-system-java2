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
	
	private boolean canMove( Position position ) {//diz se o rei pode mover para uma determinada posição
		ChessPiece p = (ChessPiece) getBoard().piece(position);//note q a posição já foi validada pelo método
		// piece
		return p == null || p.getColor() != getColor();// acho q aqui se p == null ele nem testa o p.getcolor
		// acho q daria NullPointerException
		// o segundo teste desse return é para testar se apeça é de um oponente
	}
	
	private boolean testRookCastling(Position position) {// testa se a torre está apta para o Roque
		// Roque é Castling em inglês
		
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;// acno q
		// se a p for igual a null ele nem faz os outros testes pq daria um NullPointerException
	}
	
	@Override
	public boolean[][] possibleMoves() {// retorna uma matriz com todos os movimentos possíveis da peça
		//Obs: é obrigatório implementar esse método pq ele é um método herdado q era abstrato
		// Obs: por padrão todas as posições de uma matriz de boolean começam com falso
		
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];// pelo q entendi no
		//getBoard().getRows, getBoard retorna um objeto da classe Board e nós utilizamos o método
		// getRows desse objeto da classe Board
		// getBoard foi herdado da classe Piece
		// note q quando instancia uma matriz de boolean, as posiões são preenchidas com false por padrão
	
		Position p = new Position(0, 0);// p é uma var auxiliar, ela começa com 0 pq é um valor qualquer
		//eles serão mudados depois, pelo método setValues
		
		//above
		
		p.setValues( position.getRow() - 1, position.getColumn() );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//below
		
		p.setValues( position.getRow() + 1, position.getColumn() );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//left

		p.setValues( position.getRow(), position.getColumn() - 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	

		//right

		p.setValues( position.getRow(), position.getColumn() + 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}			
		
		//northwest

		p.setValues( position.getRow() - 1, position.getColumn() - 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//northeast

		p.setValues( position.getRow() - 1, position.getColumn() + 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//south-west

		p.setValues( position.getRow() + 1, position.getColumn() - 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//south-east

		p.setValues( position.getRow() + 1, position.getColumn() + 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		// # Specialmove castling
		
		if( getMoveCount() == 0 && !chessMatch.getCheck() ) {// parece q o rei nao pode ter sido movido para 
			// poder dar o Roque
			//OBS: nao sei pq precisa testar se o rei está em cheque acho q essa condição está errada
			
			// #Castling kingside rook (Roque para a direita)
			// é a mesma coisa para as duas cores de jogador
			
			Position posT1 = new Position( position.getRow(), position.getColumn() + 3 );// posição da torre da
			// direita
			
			if( testRookCastling(posT1) ) {
				//vamos testar se as duas casas a direita do rei estão vazias
				Position p1 = new Position( position.getRow(), position.getColumn() + 1 );
				Position p2 = new Position( position.getRow(), position.getColumn() + 2 );
				
				if( getBoard().piece(p1) == null && getBoard().piece(p2) == null ) {//as posições entre o rei e
					// a torre estão nulas
					mat[position.getRow()][position.getColumn() + 2 ] = true;
				}
			}
			
			
			// #Castling QueenSide rook (Roque para a esquerda)
			// é a mesma coisa para as duas cores de jogador
			
			Position posT2 = new Position( position.getRow(), position.getColumn() - 4 );// posição da torre da
			// esquerda
			
			if( testRookCastling(posT2) ) {
				//vamos testar se as três casas a esquerda do rei estão vazias
				Position p1 = new Position( position.getRow(), position.getColumn() - 1 );
				Position p2 = new Position( position.getRow(), position.getColumn() - 2 );
				Position p3 = new Position( position.getRow(), position.getColumn() - 3 );
				
				if( getBoard().piece(p1) == null && getBoard().piece(p2) == null && 
						getBoard().piece(p3)  == null ){//as posições entre o rei e
					// a torre estão nulas
					mat[position.getRow()][position.getColumn() - 2 ] = true;
				}
			}
		}
		
		return mat;
	}
}
