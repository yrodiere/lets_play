package ui;

import static org.junit.Assert.*;

import javax.swing.Icon;

import org.junit.Test;

import data.Coordinates;
import data.Tile;

public class IconFactoriesTest {

	@Test
	public void testDraughts() {
		IconFactory factory = new implementation.draughts.ui.IconFactory();
		Tile tileModels[] = new Tile[] { new Tile(new Coordinates(0, 0)),
				new Tile(new Coordinates(1, 0)),
				new Tile(new Coordinates(0, 1)),
				new Tile(new Coordinates(12, 23)),
				new Tile(new Coordinates(5, 7)) };

		for (Tile tile : tileModels) {
			Icon icon = factory.createTileIcon(tile);
			assertNotNull(icon);
			assertTrue(icon.getIconHeight() > 0);
			assertTrue(icon.getIconWidth() > 0);
		}

		// TODO: test piece icons (much more complex)
	}

	@Test
	public void testNoughtsCrosses() {
		IconFactory factory = new implementation.noughts_crosses.ui.IconFactory();
		Tile tileModels[] = new Tile[] { new Tile(new Coordinates(0, 0)),
				new Tile(new Coordinates(1, 0)),
				new Tile(new Coordinates(0, 1)),
				new Tile(new Coordinates(12, 23)),
				new Tile(new Coordinates(5, 7)) };

		for (Tile tile : tileModels) {
			Icon icon = factory.createTileIcon(tile);
			assertNotNull(icon);
			assertTrue(icon.getIconHeight() > 0);
			assertTrue(icon.getIconWidth() > 0);
		}

		// TODO: test piece icons (much more complex)
	}

}
