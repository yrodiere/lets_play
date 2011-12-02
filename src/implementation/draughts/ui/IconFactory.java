package implementation.draughts.ui;

import javax.swing.Icon;

import implementation.draughts.PieceType;
import data.Coordinates;
import data.Piece;
import data.Player;
import data.Tile;

public class IconFactory extends ui.IconFactory {

	@Override
	public Icon createTileIcon(Tile model) {
		Coordinates tileCoord = model.getCoordinates();
		if ((tileCoord.getRow() + tileCoord.getColumn()) % 2 == 0) {
			return createImageIcon("/draughts/dark_tile.png");
		} else {
			return createImageIcon("/draughts/light_tile.png");
		}
	}

	@Override
	public final Icon createPieceIcon(Piece model, int typeHashCode) {
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

	protected Icon createManIcon(Player owner) {
		switch (owner.getBoardSide()) {
		case UP:
			return createImageIcon("/draughts/dark_man.png");
		case DOWN:
			return createImageIcon("/draughts/light_man.png");
		default:
			throw new IllegalArgumentException(
					"The player side must be UP or DOWN");
		}
	}

	protected Icon createKingIcon(Player owner) {
		switch (owner.getBoardSide()) {
		case UP:
			return createImageIcon("/draughts/dark_king.png");
		case DOWN:
			return createImageIcon("/draughts/light_king.png");
		default:
			throw new IllegalArgumentException(
					"The player side must be UP or DOWN");
		}
	}
}
