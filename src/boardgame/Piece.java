package boardgame;

public class Piece {
	
	protected Position position;
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Piece(Board board) {
		// não tem position no construtor porque uma peça recem criada possui uma posição nula
		this.board = board;
		position = null;// note q por padrao a posiçao já é nula mas coloquei para enfatizar
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	protected Board getBoard() {// protected pq nao queremos q o tabuleiro (Board) seja acessível pela camada de xadrez
		return board;
	}
	
	//só tem o getBoard pq nao queremos q o tabuleiro seja alterado 
}
