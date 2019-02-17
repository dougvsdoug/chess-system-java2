package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	// essa classe possui as regras do jogo de xadrez
	
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();//note q quando cria a partida j� faz o initial setup
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessPiece[][] getPiece(){
		// esse m�todo retorna uma matriz de pe�as de xadrez correspondentes a essa partida
		//note q o programa (acho q o ChessMatch) s� tera acesso ao ChessPiece e nao ao Piece
		//O programa ir� conhecer apenas a camada de xadrez e nao a camada de tabuleiro
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i <board.getRows(); i++ ) {
			for(int j = 0; j<board.getColumns(); j++ ) {
				mat[i][j] = (ChessPiece) board.piece(i, j);// note que foi necess�rio fazer um cast para poder 
				//fazer o downcasting
			}
		}
		
		return mat;
	}
	
	private void placeNewPiece( char column, int row, ChessPiece piece ) {// recebe uma pe�a, uma linha e uma
		//coluna(char) em coordenadas de xadrez e nao de matriz, ent�o ele coloca a pe�a na posi��o
		//note que o m�todo precisa converter a posi��o de xadrez(ChessPosition) para a posi��o de matriz
		//para isso ele chama o toPosition
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition() );
	}
	
	private void initialSetup() {
		// esse m�todo inicia a partida de xadrez, colocando as pe�as no tabuleiro
		
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	
}
