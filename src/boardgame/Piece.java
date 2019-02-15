package boardgame;

public class Piece {
	
	protected Position position;
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Piece(Board board) {
		// n�o tem position no construtor porque uma pe�a recem criada possui uma posi��o nula
		this.board = board;
		position = null;// note q por padrao a posi�ao j� � nula mas coloquei para enfatizar
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	protected Board getBoard() {// protected pq nao queremos q o tabuleiro (Board) seja acess�vel pela camada de xadrez
		return board;
	}
	
	//s� tem o getBoard pq nao queremos q o tabuleiro seja alterado 
}
