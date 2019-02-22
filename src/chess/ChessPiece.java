package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	// note q ChessPiece deve ser uma classe abstrata ou implementar os m�todos abstratos da classe
	//Piece pois ChessPiece � uma subclasse de Piece
	
	private Color color;
	private int moveCount;// note q int � iniciado por padr�o com zero
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	//tiramos o setColor pq nao queremos q a cor seja alterada
	/*-----------------------------------------------------------------------------------------------------*/
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	protected boolean isThereOpponentPiece( Position position ) {// verifica se existe uma pe�a advers�ria
		// em uma determinada posi��o
		
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		
		return  p != null && color != p.color ;// D�VIDA: ser� q pode dar problema caso p aponte para null e 
		// o compilador tente acessar p.color
		
	}
}
