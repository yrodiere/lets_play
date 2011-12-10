package ui;

import java.net.URL;
import java.util.MissingResourceException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import data.Piece;
import data.Tile;

public abstract class IconFactory {
	public abstract PieceViewDescription createPieceViewDescription(
			Piece model, int newTypeHashCode);

	public abstract TileViewDescription createTileViewDescription(Tile model, boolean reachable, boolean selected);

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
