package data;

import java.util.List;
import java.util.Observable;

//TODO
public final class Player extends Observable implements GameData {
	public static enum PlayerStatus {/*TODO*/ PLAYING};
	public static final class PlayerModification {
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
	
	public PlayerStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void init(List<Piece> ownedPieces){
		//TODO
	}
	
	public List<Piece> findOnBoardPieces(){
		//TODO
		return null;
	}
	
	public List<Piece> findOffBoardPieces(){
		//TODO
		return null;
	}

	public void win() {
		// TODO Auto-generated method stub
		
	}

	public void loose() {
		// TODO Auto-generated method stub
		
	}

}
