package data;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

public class Board implements GameData {
	private final Map<Coordinates, Tile> tiles = new HashMap<Coordinates, Tile>();

	public Board(int rowCount, int columnCount) {
		if (rowCount <= 0 || columnCount <= 0) {
			throw new IllegalArgumentException(
					"rowCount and columnCount must be strictly positive");
		}

		for (int i = 0; i < rowCount; ++i) {
			for (int j = 0; j < columnCount; ++j) {
				Coordinates coord = new Coordinates(i, j);
				tiles.put(coord, new Tile(coord));
			}
		}
	}

	@Override
	public void resetTurn() {
		for (Map.Entry<Coordinates, Tile> entry : tiles.entrySet()) {
			entry.getValue().resetTurn();
		}
	}
	
	public void clearReachables() {
		for (Map.Entry<Coordinates, Tile> entry : tiles.entrySet()) {
			entry.getValue().setReachable(false);
		}
	}

	@Override
	public void endTurn() {
		for (Map.Entry<Coordinates, Tile> entry : tiles.entrySet()) {
			entry.getValue().endTurn();
		}
	}

	public Tile findTileAt(Coordinates coords) {
		return tiles.get(coords);
	}

	public void addTileObserver(Observer observer) {
		for (Map.Entry<Coordinates, Tile> entry : tiles.entrySet()) {
			entry.getValue().addObserver(observer);
		}
	}
}
