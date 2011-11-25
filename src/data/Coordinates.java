package data;

import java.io.Serializable;

//TODO
public class Coordinates implements Serializable {
	private static final long serialVersionUID = 6931752803665991178L;

	public enum DirectionOnBoard {
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
		// TODO Auto-generated method stub
		return null;
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
}
