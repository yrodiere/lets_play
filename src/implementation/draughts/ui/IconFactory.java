package implementation.draughts.ui;

import java.awt.Color;

import javax.swing.Icon;

import ui.TileViewDescription;

import implementation.draughts.PieceType;
import data.Coordinates;
import data.Piece;
import data.Player;
import data.Tile;

public class IconFactory extends ui.IconFactory {
	private static final Color DARK_COLOR = Color.DARK_GRAY;
	private static final Color LIGHT_COLOR = Color.LIGHT_GRAY;
	private static final Color REACHABLE_COLOR = Color.GREEN;
	private static final Color SELECTED_COLOR = Color.BLUE;
	private static final int BORDER_WIDTH = 2;

	@Override
	public TileViewDescription createTileViewDescription(Tile model, boolean reachable, boolean selected) {
		Color backgroundColor;
		Color borderColor;
		int borderWidth = BORDER_WIDTH;

		Coordinates tileCoord = model.getCoordinates();
		if ((tileCoord.getRow() + tileCoord.getColumn()) % 2 == 0) {
			backgroundColor = DARK_COLOR;
		} else {
			backgroundColor = LIGHT_COLOR;
		}

		if (reachable) {
			borderColor = REACHABLE_COLOR;
		} else if (selected) {
			borderColor = SELECTED_COLOR;
		} else {
			borderColor = DARK_COLOR;
			borderWidth = 0;
		}

		return new TileViewDescription(backgroundColor, borderColor,
				borderWidth);
	}

	@Override
	public final ui.PieceViewDescription createPieceViewDescription(
			Piece model, int typeHashCode) {
		switch (PieceType.fromHash(typeHashCode)) {
		case MAN:
			return new ui.PieceViewDescription(createManIcon(model.getPlayer()));
		case KING:
			return new ui.PieceViewDescription(
					createKingIcon(model.getPlayer()));
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
