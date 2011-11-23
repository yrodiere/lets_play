package data;

import java.util.Observable;

//TODO
public final class Tile extends Observable implements GameData {
	/*
	 *  -----------------------------------------------Attributes------------------------------------------------------------
	 */
	protected Piece piece;
	
	/*
	 *  -----------------------------------------------Methodes------------------------------------------------------------
	 */

	public static final class TileModification {
		//TODO
	}

	@Override
	public void resetTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}
	
	public Coordinates getCoordinates(){
		// TODO
		return null;
	}
	
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece newPiece) {
		piece = newPiece;
	}
	
	public void setSelected(boolean selected) {
		//TODO
	}
	
	public void setReachable(boolean selected) {
		//TODO
	}
}
