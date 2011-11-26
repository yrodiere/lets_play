package data;

//TODO
public interface GameData {
	/**
	 * Resets the game data to the state it had at the beginning of the current
	 * turn.
	 */
	public void resetTurn();

	/**
	 * Performs necessary actions on the game data at the end of a turn, and
	 * sets it to the state it should have at the beginning of a turn.
	 */
	public void endTurn();
}
