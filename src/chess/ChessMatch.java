package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	// essa classe possui as regras do jogo de xadrez
	
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();//note q quando cria a partida já faz o initial setup
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessPiece[][] getPieces(){
		// esse método retorna uma matriz de peças de xadrez (ChessPiece) correspondentes a essa partida
		//note q o programa (acho q o ChessMatch) só tera acesso ao ChessPiece e nao ao Piece
		//O programa irá conhecer apenas a camada de xadrez e nao a camada de tabuleiro
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i <board.getRows(); i++ ) {
			for(int j = 0; j<board.getColumns(); j++ ) {
				mat[i][j] = (ChessPiece) board.piece(i, j);// note que foi necessário fazer um cast para poder 
				//fazer o downcasting
			}
		}
		
		return mat;
	}
	
	public boolean[][] possibleMoves( ChessPosition sourcePosition ){//retorna uma matriz de movimentos possíveis
		// a partir de uma posição. Note q diferente do método possibleMoves da classe piece, esse recebe uma 
		// posição como parâmetro
		
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
		
	}
	
	public ChessPiece performChessMove( ChessPosition sourcePosition, ChessPosition targetPosition ) {
		// move uma peça, tira ela da posição de origem e coloca na posição de destino
		// se for o caso retorna uma peça capturada( q foi comida)
		
		Position source = sourcePosition.toPosition();// estamos convertendo a ChessPosition para Position(posição
		// de matriz )
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);// valida a posição de origem, se essa posição não existir lança uma exceção
		// acho q seria melhor colocar o validate antes de receber o targetPosition
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);// makeMove realiza o realiza o movimento da peça
		return (ChessPiece)capturedPiece;	
	}
	
	private Piece makeMove( Position source, Position target ) {// makeMove realiza o realiza o movimento da peça
		
		Piece p = board.removePiece(source);//remove do tabuleiro a peça na posição de origem
		Piece capturedPiece = board.removePiece(target);//remove do tabuleiro a peça da posição de destino
		// note q o removePiece pode retornar null
		// agora q removemos uma peça da posição de origem e também uma possível peça da posição de destino
		// vamos colocar a peça na posição de destino
		// note q a posição de destino deve ser validada antes, esse método só realiza o movimento
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition( Position position ) {// valida a posição de origem, se essa posição não existir lança uma exceção
		
		if( !board.thereIsAPiece(position) ) {
			throw new ChessException("There is no piece on source position");
			// note q como ChessException é uma subclasse de BoardException, quando captura uma ChessException
			// também captura uma possível BoardException
		}
		
		if( !board.piece(position).isThereAnyPossibleMove() ) { // testa se existe algum movimento possível para
			//a peça, ou seja se ela está presa. O método piece() retorna uma peça, é como se fosse um
			// getPiece()
			throw new ChessException("There is no possible moves for the chosen piece");
		}
		
	}
	
	private void validateTargetPosition( Position source, Position target ) {// valida a posição de destino
		
		if( !board.piece(source).possibleMove(target) ) {
			throw new ChessException("The chosen piece can´t move to target position");
		}
	}
	
	private void placeNewPiece( char column, int row, ChessPiece piece ) {// recebe uma peça, uma linha e uma
		//coluna(char) em coordenadas de xadrez e nao de matriz, então ele coloca a peça na posição
		//note que o método precisa converter a posição de xadrez(ChessPosition) para a posição de matriz
		//para isso ele chama o toPosition
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition() );
	}
	
	private void initialSetup() {
		// esse método inicia a partida de xadrez, colocando as peças no tabuleiro
		
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
