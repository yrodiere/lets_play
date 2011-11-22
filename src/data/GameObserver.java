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
	public void update(Observable observable, Object modification) {
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
			// TODO: throw something
		}
	}

	protected abstract void update(Piece modifiedPiece,
			PieceModification modification);

	protected abstract void update(Tile modifiedTile,
			TileModification modification);

	protected abstract void update(Player modifiedPlayer,
			PlayerModification modification);
}
