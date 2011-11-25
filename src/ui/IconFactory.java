package ui;

import data.Piece;
import data.Tile;

public abstract class IconFactory {
	/* TODO: change return types to Swing icon */
	public final void createPieceIcon(Piece model) {
		createPieceIcon(model, model.getTypeHashCode());
	}

	public abstract void createPieceIcon(Piece model, int newTypeHashCode);

	public abstract void createTileIcon(Tile model);
}
