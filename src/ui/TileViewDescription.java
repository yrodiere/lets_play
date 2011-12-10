package ui;

import java.awt.Color;

public class TileViewDescription {
	private final Color backgroundColor;
	private final Color borderColor;
	private final int borderWidth;

	public TileViewDescription(Color backgroundColor, Color borderColor, int borderWidth) {
		super();
		if (backgroundColor == null || borderColor == null) {
			throw new IllegalArgumentException("Tile colors cannot be null");
		}
		if (borderWidth < 0) {
			throw new IllegalArgumentException("Border width must be positive");
		}
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}
	
	public double getBorderWidth() {
		return borderWidth;
	}
}