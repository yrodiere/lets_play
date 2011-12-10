package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import ui.utils.BoardBuildHelper;

import data.Coordinates;
import data.Coordinates.DirectionOnBoard;

public class BoardView extends JPanel {
	private static final long serialVersionUID = 8154656540938466952L;

	private final Map<Coordinates, TileCanvas> tiles = new HashMap<Coordinates, TileCanvas>();

	public interface MouseListenerFactory {
		public MouseListener createListener(Coordinates coord);
	}

	public BoardView(Dimension size, int nbRow, int nbColumn,
			DirectionOnBoard bottomSide, MouseListenerFactory factory) {

		BoardBuildHelper buildHelper = new BoardBuildHelper(bottomSide, nbRow,
				nbColumn);
		setLayout(new GridLayout(buildHelper.getRotatedNbRow(),
				buildHelper.getRotatedNbColumn()));
		setPreferredSize(size);
		setSize(size.width, size.height);

		int tilesize = Math.min(size.width / nbColumn, size.height / nbRow);

		while (buildHelper.hasNext()) {
			Coordinates coord = buildHelper.next();

			TileCanvas tile = new TileCanvas();
			tile.setSize(tilesize, tilesize);
			tile.addMouseListener(factory.createListener(coord));
			add(tile);
			tiles.put(coord, tile);
		}
	}

	public void repaint(Coordinates coord, TileViewDescription tileDesc) {
		repaint(coord, tileDesc, null);
	}

	public void repaint(Coordinates coord, TileViewDescription tileDesc,
			PieceViewDescription pieceDesc) {
		TileCanvas tile = tiles.get(coord);
		if (tile == null) {
			return;
		}

		tile.setTileView(tileDesc);
		tile.setPieceView(pieceDesc);
		tile.repaint();
	}
}
