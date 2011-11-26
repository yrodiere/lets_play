package implementation.draughts.ui;

import implementation.draughts.PieceType;
import data.Piece;
import data.Player;
import data.Tile;

//TODO
public class IconFactory extends ui.IconFactory {

	@Override
	public Object createTileIcon(Tile model) {
		// TODO create a tile icon
		return null;
	}

	@Override
	public final Object createPieceIcon(Piece model, int typeHashCode) {
		switch (PieceType.fromHash(typeHashCode)) {
		case MAN:
			return createManIcon(model.getPlayer());
		case KING:
			return createKingIcon(model.getPlayer());
		default:
			throw new IllegalArgumentException(
					"PieceType corresponding to typeHashCode is unknown");
		}
	}

	protected Object createManIcon(Player owner) {
		switch (owner.getBoardSide()) {
		case UP:
			// TODO: create a man icon for the player on the upper edge
			return null;
		case DOWN:
			// TODO: create a man icon for the player on the lower edge
			return null;
		default:
			throw new IllegalArgumentException(
					"The player side must be UP or DOWN");
		}
	}

	protected Object createKingIcon(Player owner) {
		switch (owner.getBoardSide()) {
		case UP:
			// TODO: create a king icon for the player on the upper edge
			return null;
		case DOWN:
			// TODO: create a king icon for the player on the lower edge
			return null;
		default:
			throw new IllegalArgumentException(
					"The player side must be UP or DOWN");
		}
	}
}
