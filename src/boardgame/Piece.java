package boardgame;

public abstract class Piece {
	//note q Piece precisa ser uma classe abstrata pois possui um m�todo abstrato
	
	protected Position position;
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Piece(Board board) {
		// n�o tem position no construtor porque uma pe�a recem criada possui uma posi��o nula
		this.board = board;
		position = null;// note q por padrao a posi�ao j� � nula mas coloquei para enfatizar
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	protected Board getBoard() {// protected pq nao queremos q o tabuleiro (Board) seja acess�vel pela camada 
		//de xadrez
		return board;
	}
	
	//s� tem o getBoard pq nao queremos q o tabuleiro seja alterado 
	
	/*------------------------------------------------------------------------------------------------------*/
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position ) {//recebe uma posi��o e retorna verdadeiro ou falso se � 
		//poss�vel mover a pe�a para essa posi��o
		
		return possibleMoves()[position.getRow()][position.getColumn()];//hook method, � um m�todo q faz um 
		// gancho com a subclasse. Note q esse m�todo pode ser concreto pq ele est� chamando uma poss�vel 
		// implementa��o de alguma subclasse concreta da classe Piece (usando o m�todo possibleMove )
		// existe um padr�o de projeto com esse nome q � o template method, vc cria uma implementa��o 
		// padr�o( no caso o possibleMove) de um m�todo q depende de um m�todo abstrato
		// o m�todo concreto possibleMove s� vai fazer sentido quando existir uma classe concreta q implemetar
		// o m�todo abstrato possibleMoves
		// OBS: isso � parecido com os m�todos padr�o das interfaces
		
	}
	
	public boolean isThereAnyPossibleMove() {// diz se existe pelo menos um movimento poss�vel para a pe�a
		
		//procura na matriz se existe pelo menos um posi��o verdadeira
		
		boolean mat[][] = possibleMoves();
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {// estamos presumindo q a matriz � quadrada
				if( mat[i][j] ) {//entra no if se mat[i][j] for verdadeira
					return true;
				}
			}
		}
		
		return false;
	}
	
}
