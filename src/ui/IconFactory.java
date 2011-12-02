package ui;

import java.net.URL;
import java.util.MissingResourceException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import data.Piece;
import data.Tile;

public abstract class IconFactory {
	/* TODO: change return types to Swing icon */
	public final Icon createPieceIcon(Piece model) {
		return createPieceIcon(model, model.getTypeHashCode());
	}

	public abstract Icon createPieceIcon(Piece model, int newTypeHashCode);

	public abstract Icon createTileIcon(Tile model);

	protected final Icon createImageIcon(String path) {
		URL url = getClass().getResource(path);
		if (url != null) {
			return new ImageIcon(url);
		} else {
			throw new MissingResourceException("Could not find an image icon",
					getClass().getName(), path);
		}
	}
}
