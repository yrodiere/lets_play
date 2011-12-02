package logic;

public abstract class Rules {

	/*
	 * -----------------------------------------------Attributes------------------
	 */
	private int nbPlayer;
	private int boardRowCount;
	private int boardColumnCount;

	/*
	 * -----------------------------------------------Methodes--------------------
	 */

	public Rules(int nbPlayer, int boardRowCount, int boardColumnCount) {
		super();
		this.nbPlayer = nbPlayer;
		this.boardRowCount = boardRowCount;
		this.boardColumnCount = boardColumnCount;
	}

	public int getBoardRowCount() {
		return boardRowCount;
	}

	public int getBoardColumnCount() {
		return boardColumnCount;
	}

	public int getNbPlayer() {
		return nbPlayer;
	}
}
