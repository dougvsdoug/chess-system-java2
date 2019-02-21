package boardgame;

import chess.pieces.King;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Board(int rows, int columns) {
		
		if( rows < 1 || columns < 1 ) {// programação defensiva
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	//nao tem get para o pieces pois teremos métodos expecíficos para isso
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	//nao tem set para rows nem para columns pq nao queremos q o numero de linhas ou colunas sejam alterados 
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Piece piece( int row, int column) {//retorna a peça da posição
		
		if( !positionExists(row, column)) {//programação defensiva
			throw new BoardException("Position not on the board");
		}
	
		return pieces[row][column];
	}
	
	public Piece piece( Position position) {// retorna a peça da posiçãp
		// note q esse método está em sobrecarga
		
		if( !positionExists(position)) {//programação defensiva
			throw new BoardException("Position not on the board");
		}
				
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece( Piece piece, Position position ) {//esse método recebe uma peça e uma posição e
		//coloca a peça na posição
		
		if( thereIsAPiece(position)) {//programação defensiva
			throw new BoardException("There is already a piece on position " + position );
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}	
	
	public Piece removePiece( Position position ) {// remove uma peça do tabuleiro e retorna a peça removida
		
		if( !positionExists(position) ) {
			throw new BoardException("Position not on the board");
		}
		
		if( piece(position) == null ) {
			return null;
		}

		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists( int row, int column) {
		//método auxiliar do positionExists
		
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {//esse método informa se a posição existe ou não
		
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {//esse método informa se existe uma peça na posição
		
		if(!positionExists(position)) {//programação defensiva
			throw new BoardException("Position not on the board");
		}
		
		return piece(position) != null;
	}
	
	
	
	
	
}
