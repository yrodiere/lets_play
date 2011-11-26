package ui;

import data.Piece;
import data.Tile;

public abstract class IconFactory {
	/* TODO: change return types to Swing icon */
	public final Object createPieceIcon(Piece model) {
		return createPieceIcon(model, model.getTypeHashCode());
	}

	public abstract Object createPieceIcon(Piece model, int newTypeHashCode);

	public abstract Object createTileIcon(Tile model);
}
