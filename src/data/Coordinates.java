package data;

//TODO
public class Coordinates {
	
	public enum DirectionOnBoard{
		UP,
		UP_RIGHT,
		RIGHT,
		DOWN_RIGHT,
		DOWN,
		DOWN_LEFT,
		LEFT,
		UP_LEFT;
	}
	
	/*
	 *  -----------------------------------------------Attributes------------------------------------------------------------
	 */
	protected int row;	
	
	protected int column;
	
	/*
	 *  -----------------------------------------------Methodes------------------------------------------------------------
	 */
	
	public Coordinates(int myRow, int myColumn){
		
		row = myRow;
		
		column = myColumn;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public Coordinates translationTowards(DirectionOnBoard direction){
		
		switch(direction){
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

}
