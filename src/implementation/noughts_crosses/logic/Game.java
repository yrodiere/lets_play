package implementation.noughts_crosses.logic;

import implementation.draughts.logic.Rules;

import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.Coordinates;
import data.Coordinates.DirectionOnBoard;
import data.Piece;
import data.Player;
import data.Tile;

//TODO
public class Game extends logic.Game {
	
	/*
	 *  -----------------------------------------------Attributes------------------------------------------------------------
	 */	
	
	List<List<Tile>> everyPossibleLigne = new ArrayList();
	
	/*
	 *  -----------------------------------------------Methodes------------------------------------------------------------
	 */

	public Game(Rules myRules, Board myBoard, List<Player> myPlayers) throws Exception {
		super(myRules, myBoard, myPlayers);
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public SelectionReturnCode specificSelect(Player actor, Coordinates location) {
		
	
		Tile selectedTile = board.findTileAt(location);
		
		if(selectedTile == null){		
			return null;
		}
		
		if( selectedPiece.tryMove( selectedTile.getCoordinates() ) ){
			return SelectionReturnCode.SUCCESS;
		}else{
			return SelectionReturnCode.INVALID_TARGET_SELECTION;
		}
	}

	@Override
	protected EndTurnReturnCode specificEndTurn(Player actor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void init() {
		Rules myRules = (Rules) rules;
		
		// TODO Init piece
		
		for(int r = 0 ; r < myRules.getBoardRowCount() ; r++ ){
			
			List<Tile> rowLigne = new ArrayList<Tile>();
			List<Tile> columnLigne = new ArrayList<Tile>();
			
			for(int c = 0 ; c < myRules.getBoardColumnCount() ; c++ ){
				rowLigne.add(board.findTileAt(new Coordinates(r, c)));
				columnLigne.add(board.findTileAt(new Coordinates(c, r)));
			}
			
			everyPossibleLigne.add(rowLigne);
			everyPossibleLigne.add(columnLigne);
		}
		
		List<Tile> diagLigne1 = new ArrayList<Tile>();
		List<Tile> diagLigne2 = new ArrayList<Tile>();
		
		Coordinates coords1 = new Coordinates(0, 0);
		Coordinates coords2 = new Coordinates(0, myRules.getBoardColumnCount()-1);
		
		diagLigne1.add(board.findTileAt(coords1));
		diagLigne2.add(board.findTileAt(coords2));
		
		for(int r = 0 ; r < myRules.getBoardRowCount() ; r++ ){
			diagLigne1.add(board.findTileAt(coords1.translationTowards(DirectionOnBoard.UP_RIGHT)));
			diagLigne2.add(board.findTileAt(coords2.translationTowards(DirectionOnBoard.UP_LEFT)));
		}		
		
		everyPossibleLigne.add(diagLigne1);
		everyPossibleLigne.add(diagLigne2);
	}
}
