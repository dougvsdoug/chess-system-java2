package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	// essa classe possui as regras do jogo de xadrez
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;//Obs: boolean pro padr�o come�a com false
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		/*check = false;// poderia fazer isso s� para enfatizar, o check j� come�a com false por padr�o*/
		initialSetup();//note q quando cria a partida j� faz o initial setup
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	/*public void setTurn( int turn ) {
		this.turn = turn;
	}
	
	public void setCurrentPlayer( Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}*/
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessPiece[][] getPieces(){
		// esse m�todo retorna uma matriz de pe�as de xadrez (ChessPiece) correspondentes a essa partida
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
	
	public boolean[][] possibleMoves( ChessPosition sourcePosition ){//retorna uma matriz de movimentos poss�veis
		// a partir de uma posi��o. Note q diferente do m�todo possibleMoves da classe piece, esse recebe uma 
		// posi��o como par�metro
		
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
		
	}
	
	public ChessPiece performChessMove( ChessPosition sourcePosition, ChessPosition targetPosition ) {
		// move uma pe�a, tira ela da posi��o de origem e coloca na posi��o de destino
		// se for o caso retorna uma pe�a capturada( q foi comida)
		
		Position source = sourcePosition.toPosition();// estamos convertendo a ChessPosition para Position(posi��o
		// de matriz )
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);// valida a posi��o de origem, se essa posi��o n�o existir lan�a uma exce��o
		// acho q seria melhor colocar o validate antes de receber o targetPosition
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);// makeMove realiza o realiza o movimento da pe�a
		
		// testa se o jogador n�o se colocou em cheque, ou se o jogador continua em cheque
		if( testCheck( currentPlayer )){// testa se o jogador n�o se colocou em cheque
			undoMove(source, target, capturedPiece);// desfaz a jogada
			throw new ChessException("You can�t put yourself in check");
			//note q se entrar nesse if o m�todo n�o executa o nextTurn
		}
		
		
		// testa se a jogada colocou o oponente em cheque
		check = testCheck( opponent(currentPlayer) ) == true ? true: false;// testa se a jogada colocou o 
		// oponente em cheque, caso sim check = true caso nao check = false
		
		
		nextTurn();
		return (ChessPiece)capturedPiece;	
	}
	
	private Piece makeMove( Position source, Position target ) {// makeMove realiza o realiza o movimento da pe�a
		
		Piece p = board.removePiece(source);//remove do tabuleiro a pe�a na posi��o de origem
		Piece capturedPiece = board.removePiece(target);//remove do tabuleiro a pe�a da posi��o de destino
		// note q o removePiece pode retornar null
		// agora q removemos uma pe�a da posi��o de origem e tamb�m uma poss�vel pe�a da posi��o de destino
		// vamos colocar a pe�a na posi��o de destino
		// note q a posi��o de destino deve ser validada antes, esse m�todo s� realiza o movimento
		board.placePiece(p, target);
		
		if( capturedPiece != null ) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece;
		
	}
	
	private void undoMove( Position source, Position target, Piece capturedPiece ) {// desfaz um movimento
		// q acabou acabou de ser realizado =, � utilizado no caso do rei entrar em cheque
		
		Piece p =  board.removePiece(target);
		board.placePiece(p, source);
		
		if( capturedPiece != null ) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
	}
	
	private void validateSourcePosition( Position position ) {// valida a posi��o de origem, se essa posi��o n�o existir lan�a uma exce��o
		
		if( !board.thereIsAPiece(position) ) {
			throw new ChessException("There is no piece on source position");
			// note q como ChessException � uma subclasse de BoardException, quando captura uma ChessException
			// tamb�m captura uma poss�vel BoardException
		}
		if( currentPlayer != ( (ChessPiece)board.piece(position) ).getColor()  ) {//caso o jogador tente jogar
			// com a pe�a o advers�rio, note q foi necess�rio fazer um downcasting para acessar o .getColor
			throw new ChessException("The chosen piece is not yours");
		}
		if( !board.piece(position).isThereAnyPossibleMove() ) { // testa se existe algum movimento poss�vel para
			//a pe�a, ou seja se ela est� presa. O m�todo piece() retorna uma pe�a, � como se fosse um
			// getPiece()
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition( Position source, Position target ) {// valida a posi��o de destino
		
		if( !board.piece(source).possibleMove(target) ) {
			throw new ChessException("The chosen piece can�t move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = ( currentPlayer == Color.WHITE ) ? Color.BLACK : Color.WHITE;// express�o condicional 
		// tern�ria, se currentPlayer == Color.WHITE ent�o CurrentPlayer recebe Color.Black
		// caso contr�rio currentPlayer = Color.BLACK
	}
	
	private Color opponent( Color color ) {
		return ( color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king( Color color ) {// localiza e retorna o rei de uma cor
		List<Piece> list = piecesOnTheBoard.stream().filter( x -> ((ChessPiece)x).getColor() == color )
				.collect(Collectors.toList() );
		for( Piece p: list) {//para cada pe�a p na minha lista list
			if( p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board!!!");// caso o for n�o
		// encotre nenhum rei, note q se isso acontecer ent�o o sistema est� com
		// problema. Por isso nem vamos tratar essa exce��o
	}
	
	private boolean testCheck(Color color) {//testa se o rei est� em cheque
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter
				( x -> ((ChessPiece)x).getColor() == opponent(color) ).collect(Collectors.toList() );
		// filtra as pe�as do oponente
		for( Piece p: opponentPieces ) {// para cada pe�a p na minha lista opponentPieces
			boolean mat[][] = p.possibleMoves();
			if( mat[kingPosition.getRow()][kingPosition.getColumn()] ) {// se na posi��o do rei a mat for true
				// ent�o entra no if
				
				return true; //O rei est� em cheque
			}
		}
		
		return false;// O rei n�o est� em cheque
	}
	
	private void placeNewPiece( char column, int row, ChessPiece piece ) {// recebe uma pe�a, uma linha e uma
		//coluna(char) em coordenadas de xadrez e nao de matriz, ent�o ele coloca a pe�a na posi��o
		//note que o m�todo precisa converter a posi��o de xadrez(ChessPosition) para a posi��o de matriz
		//para isso ele chama o toPosition
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition() );
		piecesOnTheBoard.add(piece);
		
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
