package chess;

import boardgame.BoardException;

public class ChessException extends BoardException{
	private static final long serialVersionUID = 1L;
	
	// note q como ChessException � uma subclasse de BoardException, quando captura uma ChessException
	// tamb�m captura uma poss�vel BoardException
	
	public ChessException( String msg ) {
		super(msg);
	}
}
