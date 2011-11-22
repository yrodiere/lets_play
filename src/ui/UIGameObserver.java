package ui;

import data.GameObserver;
import data.Piece;
import data.Player;
import data.Player.PlayerModification;
import data.Tile;
import data.Piece.PieceModification;
import data.Tile.TileModification;

public class UIGameObserver extends GameObserver {

	@Override
	protected void update(Piece modifiedPiece, PieceModification modification) {
		// TODO : handle a piece change

	}

	@Override
	protected void update(Tile modifiedPlayer, TileModification modification) {
		// TODO : handle a tile change

	}

	@Override
	protected void update(Player modifiedTile, PlayerModification modification) {
		// TODO : handle a player change

	}

}
