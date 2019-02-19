package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	/*-------------------------------------------------------------------------------------------------------*/
	
	public ChessPosition( char column, int row) {
		
		//CUIDADO!!!!!!!! note que nesse construtor primeiro vem a coluna e depois a linha
		
		if( column < 'a' || column > 'h' || row < 1 || row > 8 ) {
			throw new ChessException("Error instantiating ChessPosition. Valid values from a1 to h8.");
		}
		
		this.column = column;
		this.row = row;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	public int getRow() {
		return row;
	}

	public char getColumn() {
		return column;
	}
	
	//tirei os set row e column para q esse atributos nao possam ser alterados
	
	/*--------------------------------------------------------------------------------------------------------*/
	
	protected Position toPosition() {// converte uma ChessPosition(posi��o no xadrez) para Position(posi��o na matriz)
		
		return new Position( 8 - row, column -'a' );
	}
	
	protected static ChessPosition fromPosition( Position position ) {// converte uma Position(posi��o de matriz) para
		// ChessPosition(posi��o de xadrez)
		// Note q a nota��o na UML para membros est�ticos � o sublinhado
		
		
		return new ChessPosition( (char)( 'a' + position.getColumn() ), 8 - position.getRow() ); 
		
	}
	
	@Override
	public String toString() {
		
		return "" + column + row;// essas aspas servem para o compilador entender q isso � uma concatena��o de
		// strings
		
		//Obs: no jogo primeiro mostramos a coluna e depois a linha
	}
}
