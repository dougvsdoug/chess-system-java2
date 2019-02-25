package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	// essa classe possui as regras do jogo de xadrez
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;//Obs: boolean pro padr�o come�a com false
	private boolean checkMate;
	private ChessPiece enPassantVunerable;// por padr�o come�a com null
	private ChessPiece promoted;
	
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
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	/*public void setTurn( int turn ) {
		this.turn = turn;
	}
	
	public void setCurrentPlayer( Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}*/
	
	public ChessPiece getEnPassantVunerable() {
		return enPassantVunerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	/*-----------------------------------------------------------------------------------------------------*/
	
	public ChessPiece[][] getPieces(){// esse m�todo retorna uma matriz de pe�as de xadrez (ChessPiece) 
		// correspondentes a essa partida
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
		//tamb�m teste e altera se o rei est� em cheque
		
		Position source = sourcePosition.toPosition();// estamos convertendo a ChessPosition para Position(posi��o
		// de matriz )
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);// valida a posi��o de origem, se essa posi��o n�o existir lan�a uma exce��o
		// acho q seria melhor colocar o validate antes de receber o targetPosition
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);// makeMove realiza o movimento da pe�a e retorna a pe�a 
		// capturada
		
		// testa se o jogador n�o se colocou em cheque, ou se o jogador continua em cheque
		if( testCheck( currentPlayer )){// testa se o jogador n�o se colocou em cheque
			undoMove(source, target, capturedPiece);// desfaz a jogada
			throw new ChessException("You can�t put yourself in check");
			//note q se entrar nesse if o m�todo n�o executa o nextTurn
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);// ser� utilizado para o enPassant
		
		
		// #specialmove promotion
		// note q devemos fazer o promotion antes de testar se o advers�rio est� em cheque, pois a pe�a q o pe�o
		// se transformar pode mudar a situa��o de cheque do advers�rio
		
		promoted = null; // para assegurar q estamos fazendo um novo teste, tamb�m ser� usado no programa 
		// principal
		
		if( movedPiece instanceof Pawn ) {
			if( ( movedPiece.getColor() == Color.WHITE && target.getRow() == 0 ) || 
					movedPiece.getColor() == Color.BLACK && target.getRow() == 7 ) {
				 promoted = (ChessPiece)board.piece(target);// para n�o correr o risco de chamar o m�todo
				 // replacePromotedPiece com promoted = null
				 promoted = replacePromotedPiece("Q"); // ("Q") � um macete, colocamos o "Q" como padr�o para
				 // facilitar a programa��o da intera��o com o usu�rio
				 // ser� usado pelo programa principal
			}
		}
		
		// testa se a jogada colocou o oponente em cheque
		check = testCheck( opponent(currentPlayer) ) == true ? true: false;// testa se a jogada colocou o 
		// oponente em cheque, caso sim check = true caso nao check = false
		
		
		// testa se a jogada colcou o oponente em cheque-mate
		if( testCheckMate(opponent(currentPlayer)) ) {//caso o advers�rio esteja em cheque-mate
			checkMate = true;
		}else {
			nextTurn();// continua o jogo
		}
		
		//note q o pe�o s� fica vuner�vel ao enPassan depois do nextTurn
		// #special move enPassant
		
		if( movedPiece instanceof Pawn && ( target.getRow() == source.getRow() - 2  || 
				target.getRow() == source.getRow() + 2 ) ) {// j� faz o teste para o pe�o de ambos os jogadores
			enPassantVunerable = movedPiece;
		}else {
			enPassantVunerable = null;
		}
		
		
		return (ChessPiece)capturedPiece;	
	}
	
	public ChessPiece replacePromotedPiece( String type ) {// CUIDADO!!!!! a jogada promotion est� cheia de bugs
		// promove um pe�o a uma torre ou cavalo ou bispo ou 
		// rainha
		if( promoted == null ) {// programa��o defensiva
			throw new IllegalStateException("There is no piece to be promoted");
		}
		
		if( !type.equals("R") && !type.equals("N") && !type.equals("B") && !type.equals("Q") ) {
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);	
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor() );
		board.placePiece(newPiece, pos);// coloca a nova pe�a na posi��o da pe�a promovida
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece( String type, Color color ) {
		if( type.equals("B")) return new Bishop(board, color);
		if( type.equals("N")) return new Knight(board, color);
		if( type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color); // se n�o entrar nos outros ifs cai nessa linha, n�o precisa testar
		// pois esse caso j� foi tratado
	}
	
	private Piece makeMove( Position source, Position target ) {// makeMove realiza o movimento da pe�a
		
		ChessPiece p = (ChessPiece)board.removePiece(source);//remove do tabuleiro a pe�a na posi��o de origem
		//p foi criado como ChessPiece ao inv�s de Piece para  poder usar o increaseMovecount
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);//remove do tabuleiro a pe�a da posi��o de destino
		// note q o removePiece pode retornar null
		// agora q removemos uma pe�a da posi��o de origem e tamb�m uma poss�vel pe�a da posi��o de destino
		// vamos colocar mover a pe�a da posi��o de origem para a posi��o de destino
		// note q a posi��o de destino deve ser validada antes, esse m�todo s� realiza o movimento
		board.placePiece(p, target);
		
		if( capturedPiece != null ) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		
		// #specialmove castling kingside rook, roque da direita do rei
		//fa�o movimento da torre
		
		if( p instanceof King && target.getColumn() ==  source.getColumn() + 2) {//se moveu o rei duas casa para
			// a direita, ent�o foi realizado o kingside castling
			
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3 );
			Position targetT = new Position(source.getRow(), source.getColumn() + 1 );// posi��o de destino da
			// torre
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
				
		// #specialmove castling QueenSide rook, roque da esquerda do rei
		//fa�o movimento da torre
		
		if( p instanceof King && target.getColumn() ==  source.getColumn() - 2) {//se moveu o rei duas casa para
			// a esquerda, ent�o foi realizado o QueenSide castling
			
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4 );
			Position targetT = new Position(source.getRow(), source.getColumn() - 1 );// posi��o de destino da
			// torre
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		
		// #specialMove enPassant
		
		if( p instanceof Pawn ) {
			if( source.getColumn() != target.getColumn() && capturedPiece == null ) {// note q caso o pe�o tenha
				// movido na diagonal ele deveria ter comido uma pe�a ent�o ele realizou o enPassant
				
				Position pawnPosition;
				if( p.getColor() == Color.WHITE ) {//caso seja o pe�o branco q realiza o enPassant contra um pe�o
					// preto
					pawnPosition = new Position( target.getRow() + 1, target.getColumn() );// posi��o do pe�o q ser� 
					// capturado
				}else {//caso seja o pe�o preto q realiza o enPassant contra um pe�o branco
					pawnPosition = new Position( target.getRow() - 1, target.getColumn() );// posi��o do pe�o q ser� 
					// capturado
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
		
	}
	
	private void undoMove( Position source, Position target, Piece capturedPiece ) {// desfaz um movimento
		// q acabou acabou de ser realizado =, � utilizado no caso do rei entrar em cheque
		// note q como a pe�a foi movida agora ela est� no target e nao no source
		
		ChessPiece p =  (ChessPiece)board.removePiece(target);
		//p foi criado como ChessPiece ao inv�s de Piece para  poder usar o decreaseMovecount
		
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if( capturedPiece != null ) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		
		// specialmove castling kingside rook, roque da direita do rei
		//fa�o movimento da torre
		// note q aqui j� foi desfeito o movimento do Rei
		
		if( p instanceof King && target.getColumn() ==  source.getColumn() + 2) {//se moveu o rei duas casa para
			// a direita, ent�o foi realizado o kingside castling
			
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3 );
			Position targetT = new Position(source.getRow(), source.getColumn() + 1 );
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);// note a mudan�a
			board.placePiece(rook, sourceT);// note a mudan�a
			rook.decreaseMoveCount();// note a mudan�a
		}
				
		// specialmove castling QueenSide rook, roque da esquerda do rei
		//fa�o movimento da torre
		
		if( p instanceof King && target.getColumn() ==  source.getColumn() - 2) {//se moveu o rei duas casa para
			// a esquerda, ent�o foi realizado o QueenSide castling
			
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4 );
			Position targetT = new Position(source.getRow(), source.getColumn() - 1 );
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);// note a mudan�a
			board.placePiece(rook, sourceT);// note a mudan�a
			rook.decreaseMoveCount();// note a mudan�a
		}
		
		
		// #specialMove enPassant
		
		if( p instanceof Pawn ) {//CUIDADO!!!!!!!!  o enPassant n�o captura a pe�a na posi��o de target
			// note q o primeiro if deste m�todo ter� colocado o pe�o capturado na posi��o de target,
			// precisaremos corrigir isso
			// portanto teremos de fazer um tratamento especial, ele captura a pe�a em baixo do target no caso do
			// enPassant branco (contra o preto) e captura a pe�a em cima do target no caso do enPassant preto 
			// (contra o branco)
			// enPassant branco (contra o preto), o pe�o preto capturado volta para a linha 3
			// enPassant preto (contra o branco), o pe�o branco capturado volta para a linha 4
			if( source.getColumn() != target.getColumn() && capturedPiece == enPassantVunerable) {// CUIDADO!!!!
				// o programa pode entrar nesse if caso um pe�o capture, sem realizar um enPassant, o pe�o
				// do oponente. Por�m mesmo assim as linhas abaixo v�o colocar o pe�o capturado novamento na
				// posi��o correta
				
				// note q caso 
				// o pe�o tenha movido na diagonal e comido um pe�o vulner�vel ao enPassant ent�o ele PODE (talvez)
				// ter realizado o enPassant
				
				ChessPiece pawn = (ChessPiece)board.removePiece(target);// nessa posi��o pode ter sido colocado
				// erroneamente ( caso realmennte tenha ocorrido o enPassant ) o pe�o capturado
				Position pawnPosition;
				if( p.getColor() == Color.WHITE ) {//caso seja o pe�o branco q realiza o enPassant contra um pe�o
					// preto
					pawnPosition = new Position( 3, target.getColumn() );// note q independente de ter sido
					// realizado o enPassant o pe�o capturado volta para a posi��o correta
				}else {//caso seja o pe�o preto q realiza o enPassant contra um pe�o branco
					pawnPosition = new Position( 4, target.getColumn() );// posi��o do pe�o q ser� 
					// capturado
				}
				board.placePiece(pawn, pawnPosition);// note q caso n�o tenha sido realizado o enPassant o 
				// pe�o capturado simplesmente volta para a posi��o de target
				
				// a altera��o nas listas j� foi realizada no primeiro if desse m�todo
			}
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
	
	private void nextTurn() {// acrescenta um turno e troca de jogador
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
	
	private boolean testCheckMate(Color color) {// O rei est� em cheque-mate quando ele est� em cheque 
		//e n�o existe nenhum movimento dele ou de uma pe�a de sua cor q possa tirar o rei do cheque
		
		if( !testCheck(color) ) {
			return false;
		}
		
		//rodar todos os movimentos das pe�as da cor do rei e testar se para todos esses movimentos o 
		// rei continua em cheque, caso isso seja verdadeiro o rei est� em cheque-mate
		
		List<Piece> list = piecesOnTheBoard.stream().filter
				( x -> ((ChessPiece)x).getColor() == color ).collect(Collectors.toList() );
		
		for( Piece p: list) {//vamos testar pe�a por pe�a
			boolean mat [][] = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if( mat[i][j] ) {//caso seja poss�vel realizar o movimento
						Position source = ((ChessPiece)p).getChessPosition().toPosition();// � necess�rio fazer 
						//isso pq o atributo position � protected, note q estamos em uma classe (ChessMatch ) 
						//q n�o � subclasse de Piece
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
	
	private void placeNewPiece( char column, int row, ChessPiece piece ) {// recebe uma pe�a, uma linha e uma
		//coluna(char) em coordenadas de xadrez e nao de matriz, ent�o ele coloca a pe�a na posi��o
		//note que o m�todo precisa converter a posi��o de xadrez(ChessPosition) para a posi��o de matriz
		//para isso ele chama o toPosition
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition() );
		piecesOnTheBoard.add(piece);
		
	}
	
	private void initialSetup() {
		// esse m�todo inicia a partida de xadrez, colocando as pe�as no tabuleiro
		
		//white
		
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));// this faz auto-referencia ao pr�prio objeto
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
        
        // Black

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));// this faz auto-referencia ao pr�prio objeto
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
		 
	}
	
}
