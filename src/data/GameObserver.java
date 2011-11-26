package data;

import java.util.Observable;
import java.util.Observer;

import data.Piece.PieceModification;
import data.Player.PlayerModification;
import data.Tile.TileModification;

public abstract class GameObserver implements Observer {

	/**
	 * Dispatcher method allowing game observers not to care about the casts.
	 */
	@Override
	public final void update(Observable observable, Object modification) {
		if (observable instanceof Piece
				&& modification instanceof PieceModification) {
			update((Piece) observable, (PieceModification) modification);
		} else if (observable instanceof Tile
				&& modification instanceof TileModification) {
			update((Tile) observable, (TileModification) modification);
		} else if (observable instanceof Player
				&& modification instanceof PlayerModification) {
			update((Player) observable, (PlayerModification) modification);
		} else {
			throw new IllegalArgumentException(
					"Only notifications related to the game should be sent to this observer.");
		}
	}

	/**
	 * Called when an observed {@link Piece} is about to be modified.
	 * @param modifiedPiece The {@link Piece}, still unmodified.
	 * @param modification The modification that is about to occur.
	 */
	protected abstract void update(Piece modifiedPiece,
			PieceModification modification);
	
	/**
	 * Called when an observed {@link Tile} is about to be modified.
	 * @param modifiedTile The {@link Tile}, still unmodified.
	 * @param modification The modification that is about to occur.
	 */
	protected abstract void update(Tile modifiedTile,
			TileModification modification);
	
	/**
	 * Called when an observed {@link Player} is about to be modified.
	 * @param modifiedPlayer The {@link Player}, still unmodified.
	 * @param modification The modification that is about to occur.
	 */
	protected abstract void update(Player modifiedPlayer,
			PlayerModification modification);
}
