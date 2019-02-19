package boardgame;

public abstract class Piece {
	//note q Piece precisa ser uma classe abstrata pois possui um método abstrato
	
	protected Position position;
	private Board board;
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	public Piece(Board board) {
		// não tem position no construtor porque uma peça recem criada possui uma posição nula
		this.board = board;
		position = null;// note q por padrao a posiçao já é nula mas coloquei para enfatizar
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	protected Board getBoard() {// protected pq nao queremos q o tabuleiro (Board) seja acessível pela camada 
		//de xadrez
		return board;
	}
	
	//só tem o getBoard pq nao queremos q o tabuleiro seja alterado 
	
	/*------------------------------------------------------------------------------------------------------*/
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position ) {//recebe uma posição e retorna verdadeiro ou falso se é 
		//possível mover a peça para essa posição
		
		return possibleMoves()[position.getRow()][position.getColumn()];//hook method, é um método q faz um 
		// gancho com a subclasse. Note q esse método pode ser concreto pq ele está chamando uma possível 
		// implementação de alguma subclasse concreta da classe Piece (usando o método possibleMove )
		// existe um padrão de projeto com esse nome q é o template method, vc cria uma implementação 
		// padrão( no caso o possibleMove) de um método q depende de um método abstrato
		// o método concreto possibleMove só vai fazer sentido quando existir uma classe concreta q implemetar
		// o método abstrato possibleMoves
		// OBS: isso é parecido com os métodos padrâo das interfaces
		
	}
	
	public boolean isThereAnyPossibleMove() {// diz se existe pelo menos um movimento possível para a peça
		
		//procura na matriz se existe pelo menos um posição verdadeira
		
		boolean mat[][] = possibleMoves();
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {// estamos presumindo q a matriz é quadrada
				if( mat[i][j] ) {//entra no if se mat[i][j] for verdadeira
					return true;
				}
			}
		}
		
		return false;
	}
	
}
