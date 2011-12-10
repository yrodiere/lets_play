package ui;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.Border;

public class TileCanvas extends Canvas {
	private static final long serialVersionUID = 2883691631822008214L;

	private TileViewDescription tileViewDescription;
	private PieceViewDescription pieceViewDescription;

	public void setTileView(TileViewDescription tileDesc) {
		this.tileViewDescription = tileDesc;
		setBackground(tileViewDescription.getBackgroundColor());
	}

	public void setPieceView(PieceViewDescription pieceDesc) {
		this.pieceViewDescription = pieceDesc;
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.setColor(tileViewDescription.getBorderColor());

		Border border = BorderFactory.createLineBorder(
				tileViewDescription.getBorderColor(), 2);
		border.paintBorder(this, getGraphics(), 0, 0, getWidth(), getHeight());

		if (pieceViewDescription != null) {
			
			Icon icon = pieceViewDescription.getIcon();
			
			icon.paintIcon(this, getGraphics(), (getWidth() - icon.getIconWidth())/2, (getHeight() - icon.getIconHeight())/2);
		}		
	}
}
