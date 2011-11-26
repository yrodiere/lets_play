package implementation.noughts_crosses.ui;

import implementation.noughts_crosses.PieceType;
import data.Piece;
import data.Player;
import data.Tile;

//TODO
public class IconFactory extends ui.IconFactory {

	@Override
	public Object createTileIcon(Tile model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final Object createPieceIcon(Piece model, int typeHashCode) {
		switch (PieceType.fromHash(typeHashCode)) {
		case MAIN:
			return createPieceIcon(model.getPlayer());
		default:
			throw new IllegalArgumentException(
					"PieceType corresponding to typeHashCode is unknown");
		}
	}

	protected Object createPieceIcon(Player owner) {
		switch (owner.getBoardSide()) {
		case UP:
			// TODO: create a piece icon for the player on the upper edge
			return null;
		case DOWN:
			// TODO: create a piece icon for the player on the lower edge
			return null;
		default:
			throw new IllegalArgumentException(
					"The player side must be UP or DOWN");
		}
	}

}
