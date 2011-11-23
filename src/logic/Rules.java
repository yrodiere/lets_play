package logic;

public abstract class Rules {
	
	/*
	 *  -----------------------------------------------Attributes------------------------------------------------------------
	 */
	protected int boardRowCount ;
	
	protected int boardColumnCount;
	
	protected int nbPlayer;
	
	/*
	 *  -----------------------------------------------Methodes------------------------------------------------------------
	 */
	
	public int getBoardRowCount(){
		return boardRowCount;
	}

	public int getBoardColumnCount(){
		return boardColumnCount;
	}
	
	public int getNbPlayer(){
		return nbPlayer;
	}
}
