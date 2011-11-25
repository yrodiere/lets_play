package implementation.draughts.ui;

import implementation.draughts.PieceType;
import data.Piece;
import data.Tile;

//TODO
public class IconFactory extends ui.IconFactory {

	@Override
	public void createTileIcon(Tile model) {
		// TODO create a tile icon
	}

	@Override
	public void createPieceIcon(Piece model, int typeHashCode) {
		if (typeHashCode == PieceType.MAN.hashCode()) {
			// TODO: create a man icon
		} else if (typeHashCode == PieceType.KING.hashCode()) {
			// TODO: create a king icon
		} else {
			throw new IllegalArgumentException("typeHashCode is invalid");
		}
	}

}
