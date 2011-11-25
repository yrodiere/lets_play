package data;

import java.io.Serializable;
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

	public static final class TileModification implements Serializable {
		private static final long serialVersionUID = 935932699665702904L;
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
	
	public boolean isReachable() {
		// TODO
		return false;
	}
	
	public boolean isSelected() {
		// TODO
		return false;
	}
	
	public boolean isEmpty() {
		//TODO: just return true if piece == null, false otherwise
		return false;
	}

	void setPiece(Piece newPiece) {
		piece = newPiece;
	}
	
	void setSelected(boolean selected) {
		//TODO
	}
	
	void setReachable(boolean selected) {
		//TODO
	}
}
