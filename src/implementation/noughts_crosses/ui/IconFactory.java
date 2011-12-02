package implementation.noughts_crosses.ui;

import javax.swing.Icon;

import implementation.noughts_crosses.PieceType;
import data.Piece;
import data.Player;
import data.Tile;

public class IconFactory extends ui.IconFactory {

	@Override
	public Icon createTileIcon(Tile model) {
		return createImageIcon("/noughts_crosses/tile.png");
	}

	@Override
	public final Icon createPieceIcon(Piece model, int typeHashCode) {
		switch (PieceType.fromHash(typeHashCode)) {
		case MAIN:
			return createPieceIcon(model.getPlayer());
		default:
			throw new IllegalArgumentException(
					"PieceType corresponding to typeHashCode is unknown");
		}
	}

	protected Icon createPieceIcon(Player owner) {
		switch (owner.getBoardSide()) {
		case UP:
			return createImageIcon("/noughts_crosses/cross.png");
		case DOWN:
			return createImageIcon("/noughts_crosses/nought.png");
		default:
			throw new IllegalArgumentException(
					"The player side must be UP or DOWN");
		}
	}

}
