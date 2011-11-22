package ui;

import data.Piece;
import data.Tile;

public interface IconFactory {
	/* TODO: change return type to Swing icon */
	public void createPieceIcon(Piece model);
	public void createTileIcon(Tile model);
}
