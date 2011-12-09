package implementation.noughts_crosses.ui;

import java.awt.Color;

import javax.swing.Icon;

import ui.PieceViewDescription;
import ui.TileViewDescription;

import implementation.noughts_crosses.PieceType;
import data.Piece;
import data.Player;
import data.Tile;

public class IconFactory extends ui.IconFactory {

	@Override
	public TileViewDescription createTileViewDescription(Tile model, boolean reachable, boolean selected) {
		return new TileViewDescription(Color.WHITE, Color.BLACK, 1);
	}

	@Override
	public final PieceViewDescription createPieceViewDescription(Piece model, int typeHashCode) {
		switch (PieceType.fromHash(typeHashCode)) {
		case MAIN:
			return new PieceViewDescription(createPieceIcon(model.getPlayer()));
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
