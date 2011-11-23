package implementation.draughts.logic;

import data.Coordinates;
import data.Tile;
public class Rules extends logic.Rules {
	
	/*
	 *  -----------------------------------------------Attributes------------------------------------------------------------
	 */
	protected int nbRowsOfPiece;	
	
	protected Coordinates initPieceStartPosition;
	
	Rules(){
		
		boardColumnCount = 10;
		
		boardRowCount = 10;
		
		nbRowsOfPiece = 4;		
	}

	public int getNbRowsOfPiece() {
		return nbRowsOfPiece;
	}
	
	public boolean isTileUsed(Tile tile){
		return ((tile.getCoordinates().getRow() + tile.getCoordinates().getColumn())%2 == 0);
	}
}
