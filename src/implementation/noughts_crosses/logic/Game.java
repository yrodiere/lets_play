package implementation.noughts_crosses.logic;

import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.Coordinates;
import data.Piece;
import data.Coordinates.DirectionOnBoard;
import data.Player;
import data.Tile;

public class Game extends logic.Game {
	
	/*
	 *  -----------------------------------------------Attributes------------------------------------------------------------
	 */	
	
	List<List<Tile>> everyPossibleLine = new ArrayList<List<Tile>>();
	
	/*
	 *  -----------------------------------------------Methodes------------------------------------------------------------
	 */

	public Game(Rules myRules, Board myBoard, List<Player> myPlayers) throws Exception {
		super(myRules, myBoard, myPlayers);

		init();
	}

	@Override
	protected SelectionReturnCode specificSelect(Player actor, Coordinates location) {
		Tile selectedTile = board.findTileAt(location);
		
		if( selectedPiece.tryMove( selectedTile.getCoordinates() ) ){
			currentPlayerHasPlayed = true;
			return SelectionReturnCode.SUCCESS;
		}else{
			return SelectionReturnCode.INVALID_TARGET_SELECTION;
		}
	}

	@Override
	protected EndTurnReturnCode specificEndTurn(Player actor) {

		Player otherPlayer = players.get(0);
		
		if(otherPlayer == actor){
			otherPlayer = players.get(1);
		}
		
		selectedPiece = otherPlayer.findOffBoardPieces().get(0);
		
		boolean boardFull = true;
		
		for(List<Tile> ligne : everyPossibleLine){
			
			int nbActorPiecesAligned = 0;
		
			for(Tile tile : ligne){
				Piece piece = tile.getPiece();
				
				if(piece == null){
					boardFull = false;
				}else{
					if(piece.getPlayer() == actor){
						nbActorPiecesAligned++;
					}
				}
			}
			
			if(nbActorPiecesAligned == rules.getBoardColumnCount()){
				actor.win();
				otherPlayer.loose();
				return EndTurnReturnCode.SUCCESS;
			}
		}
		
		if(boardFull){
			actor.draw();
			otherPlayer.draw();
		}else{
			actor.endTurn();
			otherPlayer.endTurn();
		}
		
		return EndTurnReturnCode.SUCCESS;
	}

	@Override
	protected void init() {
		Rules myRules = (Rules) rules;
		
		int nbTiles = myRules.getBoardRowCount()*myRules.getBoardColumnCount();
		
		List<Piece> player1Pieces = new ArrayList<Piece>();
		
		List<Piece> player2Pieces = new ArrayList<Piece>();
		
		for(int i = 0 ; i < nbTiles ; i++ ){	
			if(i%2 == 0){
				Piece piece = initPiece(players.get(0));
				player1Pieces.add(piece);
			}else{
				Piece piece = initPiece(players.get(1));
				player2Pieces.add(piece);
			}
		}		
		
		selectedPiece = player1Pieces.get(0);
		
		for(int r = 0 ; r < myRules.getBoardRowCount() ; r++ ){
			
			List<Tile> rowLine = new ArrayList<Tile>();
			List<Tile> columnLine = new ArrayList<Tile>();
			
			for(int c = 0 ; c < myRules.getBoardColumnCount() ; c++ ){
				rowLine.add(board.findTileAt(new Coordinates(r, c)));
				columnLine.add(board.findTileAt(new Coordinates(c, r)));
			}
			
			everyPossibleLine.add(rowLine);
			everyPossibleLine.add(columnLine);
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
		
		everyPossibleLine.add(diagLigne1);
		everyPossibleLine.add(diagLigne2);
	}
	
	
protected Piece initPiece(Player owner){	
	
		Piece piece = new Piece(owner);
		
		MoveStrategy ms = new MoveStrategy(piece,board,(Rules)rules);
		
		piece.setMoveStrategy(ms);
	
		return piece;	
	}
}
