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
	private boolean check;//Obs: boolean pro padrão começa com false
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		/*check = false;// poderia fazer isso só para enfatizar, o check já começa com false por padrão*/
		initialSetup();//note q quando cria a partida já faz o initial setup
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
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	/*public void setTurn( int turn ) {
		this.turn = turn;
	}
	
	public void setCurrentPlayer( Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}*/
	
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
		//também teste e altera se o rei está em cheque
		
		Position source = sourcePosition.toPosition();// estamos convertendo a ChessPosition para Position(posição
		// de matriz )
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);// valida a posição de origem, se essa posição não existir lança uma exceção
		// acho q seria melhor colocar o validate antes de receber o targetPosition
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);// makeMove realiza o movimento da peça
		
		// testa se o jogador não se colocou em cheque, ou se o jogador continua em cheque
		if( testCheck( currentPlayer )){// testa se o jogador não se colocou em cheque
			undoMove(source, target, capturedPiece);// desfaz a jogada
			throw new ChessException("You can´t put yourself in check");
			//note q se entrar nesse if o método não executa o nextTurn
		}
		
		
		// testa se a jogada colocou o oponente em cheque
		check = testCheck( opponent(currentPlayer) ) == true ? true: false;// testa se a jogada colocou o 
		// oponente em cheque, caso sim check = true caso nao check = false
		
		
		// testa se a jogada colcou o oponente em cheque-mate
		if( testCheckMate(opponent(currentPlayer)) ) {//caso o adversário esteja em cheque-mate
			checkMate = true;
		}else {
			nextTurn();// continua o jogo
		}
		
		return (ChessPiece)capturedPiece;	
	}
	
	private Piece makeMove( Position source, Position target ) {// makeMove realiza o movimento da peça
		
		Piece p = board.removePiece(source);//remove do tabuleiro a peça na posição de origem
		Piece capturedPiece = board.removePiece(target);//remove do tabuleiro a peça da posição de destino
		// note q o removePiece pode retornar null
		// agora q removemos uma peça da posição de origem e também uma possível peça da posição de destino
		// vamos colocar mover a peça da posição de origem para a posição de destino
		// note q a posição de destino deve ser validada antes, esse método só realiza o movimento
		board.placePiece(p, target);
		
		if( capturedPiece != null ) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece;
		
	}
	
	private void undoMove( Position source, Position target, Piece capturedPiece ) {// desfaz um movimento
		// q acabou acabou de ser realizado =, é utilizado no caso do rei entrar em cheque
		
		Piece p =  board.removePiece(target);
		board.placePiece(p, source);
		
		if( capturedPiece != null ) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
	}
	
	private void validateSourcePosition( Position position ) {// valida a posição de origem, se essa posição não existir lança uma exceção
		
		if( !board.thereIsAPiece(position) ) {
			throw new ChessException("There is no piece on source position");
			// note q como ChessException é uma subclasse de BoardException, quando captura uma ChessException
			// também captura uma possível BoardException
		}
		if( currentPlayer != ( (ChessPiece)board.piece(position) ).getColor()  ) {//caso o jogador tente jogar
			// com a peça o adversário, note q foi necessário fazer um downcasting para acessar o .getColor
			throw new ChessException("The chosen piece is not yours");
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
	
	private void nextTurn() {// acrescenta um turno e troca de jogador
		turn++;
		currentPlayer = ( currentPlayer == Color.WHITE ) ? Color.BLACK : Color.WHITE;// expressão condicional 
		// ternária, se currentPlayer == Color.WHITE então CurrentPlayer recebe Color.Black
		// caso contrário currentPlayer = Color.BLACK
	}
	
	private Color opponent( Color color ) {
		return ( color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king( Color color ) {// localiza e retorna o rei de uma cor
		List<Piece> list = piecesOnTheBoard.stream().filter( x -> ((ChessPiece)x).getColor() == color )
				.collect(Collectors.toList() );
		for( Piece p: list) {//para cada peça p na minha lista list
			if( p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board!!!");// caso o for não
		// encotre nenhum rei, note q se isso acontecer então o sistema está com
		// problema. Por isso nem vamos tratar essa exceção
	}
	
	private boolean testCheck(Color color) {//testa se o rei está em cheque
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter
				( x -> ((ChessPiece)x).getColor() == opponent(color) ).collect(Collectors.toList() );
		// filtra as peças do oponente
		for( Piece p: opponentPieces ) {// para cada peça p na minha lista opponentPieces
			boolean mat[][] = p.possibleMoves();
			if( mat[kingPosition.getRow()][kingPosition.getColumn()] ) {// se na posição do rei a mat for true
				// então entra no if
				
				return true; //O rei está em cheque
			}
		}
		
		return false;// O rei não está em cheque
	}
	
	private boolean testCheckMate(Color color) {// O rei está em cheque-mate quando ele está em cheque 
		//e não existe nenhum movimento dele ou de uma peça de sua cor q possa tirar o rei do cheque
		
		if( !testCheck(color) ) {
			return false;
		}
		
		//rodar todos os movimentos das peças da cor do rei e testar se para todos esses movimentos o 
		// rei continua em cheque, caso isso seja verdadeiro o rei está em cheque-mate
		
		List<Piece> list = piecesOnTheBoard.stream().filter
				( x -> ((ChessPiece)x).getColor() == color ).collect(Collectors.toList() );
		
		for( Piece p: list) {//vamos testar peça por peça
			boolean mat [][] = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if( mat[i][j] ) {//caso seja possível realizar o movimento
						Position source = ((ChessPiece)p).getChessPosition().toPosition();// é necessário fazer 
						//isso pq o atributo position é protected, note q estamos em uma classe (ChessMatch ) 
						//q não é subclasse de Piece
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);// vamos testando e desfazendo os movimentos
						
						if( !testCheck ) {
							return false; //encontramos um movimento q tira o rei do cheque
						}
					}				
				}		
			}
		}
		
		return true;
		
	}
	
	private void placeNewPiece( char column, int row, ChessPiece piece ) {// recebe uma peça, uma linha e uma
		//coluna(char) em coordenadas de xadrez e nao de matriz, então ele coloca a peça na posição
		//note que o método precisa converter a posição de xadrez(ChessPosition) para a posição de matriz
		//para isso ele chama o toPosition
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition() );
		piecesOnTheBoard.add(piece);
		
	}
	
	private void initialSetup() {
		// esse método inicia a partida de xadrez, colocando as peças no tabuleiro
		
		placeNewPiece('h', 7, new Rook(board, Color.BLACK));
        placeNewPiece('d', 1, new Rook(board, Color.BLACK));
        placeNewPiece('e', 1, new King(board, Color.BLACK));

        placeNewPiece('b', 8, new Rook(board, Color.WHITE));
        placeNewPiece('a', 8, new King(board, Color.WHITE));
	}
	
}
