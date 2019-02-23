package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{
	//OBS: CÓDIGO COPIADO DA CLASSE KING TALVEZ OS COMENTÁRIOS ESTAJAM ESTRANHOS

	public Knight( Board board, Color color ) {
		super(board, color);
	}
	
	/*------------------------------------------------------------------------------------------------------*/
	
	@Override
	public String toString() {
		return "N";
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
		// note q quando instancia uma matriz de boolean, as posiões são preenchidas com false por padrão
	
		Position p = new Position(0, 0);// p é uma var auxiliar, ela começa com 0 pq é um valor qualquer
		//eles serão mudados depois, pelo método setValues
		
		//above-left
		
		p.setValues( position.getRow() - 2, position.getColumn() - 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//above-right
		
		p.setValues( position.getRow() - 2, position.getColumn() + 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//bellow-left

		p.setValues( position.getRow() + 2, position.getColumn() - 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	

		//bellow-right

		p.setValues( position.getRow() + 2, position.getColumn() + 1 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}			
		
		//left-above

		p.setValues( position.getRow() - 1, position.getColumn() - 2 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//left-bellow

		p.setValues( position.getRow() + 1, position.getColumn() -2  );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//right-above

		p.setValues( position.getRow() - 1, position.getColumn() + 2 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//right-bellow

		p.setValues( position.getRow() + 1, position.getColumn() + 2 );// position.get é a posição de origem da peça
		
		if( getBoard().positionExists(p) && canMove(p) ){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		return mat;
	}
}
