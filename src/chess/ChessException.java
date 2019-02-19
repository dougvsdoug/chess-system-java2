package chess;

import boardgame.BoardException;

public class ChessException extends BoardException{
	private static final long serialVersionUID = 1L;
	
	// note q como ChessException é uma subclasse de BoardException, quando captura uma ChessException
	// também captura uma possível BoardException
	
	public ChessException( String msg ) {
		super(msg);
	}
}
