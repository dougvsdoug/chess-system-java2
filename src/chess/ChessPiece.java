package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece{
	// note q ChessPiece deve ser uma classe abstrata ou implementar os métodos abstratos da classe
	//Piece pois ChessPiece é uma subclasse de Piece
	
	private Color color;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Color getColor() {
		return color;
	}

	//tiramos o setColor pq nao queremos q a cor seja alterada
	/*-----------------------------------------------------------------------------------------------------*/
	
}
