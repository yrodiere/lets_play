package ui;

import javax.swing.Icon;

public class PieceViewDescription {
	private final Icon icon;

	public PieceViewDescription(Icon icon) {
		super();
		if (icon == null) {
			throw new IllegalArgumentException("A piece icon cannot be null");
		}
		this.icon = icon;
	}

	public Icon getIcon() {
		return icon;
	}
}