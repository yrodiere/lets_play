package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
	private static final long serialVersionUID = 6931752803665991178L;

	public static enum DirectionOnBoard {
		UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT
	}

	/*
	 * -----------------------------------------------Attributes------------------
	 * ------------------------------------------
	 */
	protected final int row;

	protected final int column;

	/*
	 * -----------------------------------------------Methods--------------------
	 * ----------------------------------------
	 */

	public Coordinates(int myRow, int myColumn) {
		row = myRow;
		column = myColumn;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Coordinates translationTowards(DirectionOnBoard direction) {

		switch (direction) {
		case UP:
			return new Coordinates(row + 1, column);
		case UP_RIGHT:
			return new Coordinates(row + 1, column + 1);
		case RIGHT:
			return new Coordinates(row, column + 1);
		case DOWN_RIGHT:
			return new Coordinates(row - 1, column + 1);
		case DOWN:
			return new Coordinates(row - 1, column);
		case DOWN_LEFT:
			return new Coordinates(row - 1, column - 1);
		case LEFT:
			return new Coordinates(row, column - 1);
		case UP_LEFT:
			return new Coordinates(row + 1, column - 1);
		}

		return null;
	}

	public DirectionOnBoard directionTo(Coordinates target) {
		if (equals(target)) {
			return null;
		} else {
			int rowDiff = target.row - row;
			int colDiff = target.column - column;

			// Straight directions
			if (rowDiff == 0) {
				return (colDiff > 0 ? DirectionOnBoard.RIGHT
						: DirectionOnBoard.LEFT);
			}
			if (colDiff == 0) {
				return (rowDiff > 0 ? DirectionOnBoard.UP
						: DirectionOnBoard.DOWN);
			}

			// Unsupported directions
			if (Math.abs(rowDiff) != Math.abs(colDiff)) {
				return null;
			}

			// Diagonal directions
			if (rowDiff > 0) {
				return (colDiff > 0 ? DirectionOnBoard.UP_RIGHT
						: DirectionOnBoard.UP_LEFT);
			} else {
				return (colDiff > 0 ? DirectionOnBoard.DOWN_RIGHT
						: DirectionOnBoard.DOWN_LEFT);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Coordinates)) {
			return false;
		}

		Coordinates other = (Coordinates) obj;
		if (other.row != row || other.column != column) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + row;
		hash = hash * 31 + column;
		return hash;
	}
}
