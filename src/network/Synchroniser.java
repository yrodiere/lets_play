package network;

import java.util.ArrayList;
import java.util.List;

import network.NetworkCommunicator.IdentifiedModification;
import network.NetworkCommunicator.IdentifiedPieceModification;
import network.NetworkCommunicator.IdentifiedPlayerModification;

import data.Board;
import data.Coordinates;
import data.Coordinates.DirectionOnBoard;
import data.GameObserver;
import data.Piece;
import data.Piece.PieceModification;
import data.Piece.PieceModificationType;
import data.Player;
import data.Player.PlayerModification;
import data.Tile;
import data.Tile.TileModification;

public class Synchroniser extends GameObserver {

	private Board board;

	private List<Player> players;

	private NetworkCommunicator networkCommunicator;

	private List<IdentifiedModification> pendingModification;

	public Synchroniser(Board board, List<Player> players, NetworkCommunicator networkCommunicator) {
		
		this.board = board;
		this.players = players;
		this.networkCommunicator = networkCommunicator;
		this.pendingModification = new ArrayList<IdentifiedModification>();
		
		this.networkCommunicator.setSynchronizer(this);
	}

	public void handleRemotePieceNotification(Coordinates identifier,
			PieceModification modif) {

		Tile pieceTile = board.findTileAt(identifier);

		Piece modifiedPiece;

		if (pieceTile == null)
			throw new NullPointerException();

		modifiedPiece = pieceTile.getPiece();

		if (modifiedPiece == null)
			throw new NullPointerException();

		modifiedPiece.update(modif);
	}

	public void handleRemotePlayerNotification(DirectionOnBoard identifier,
			PlayerModification modif) {

		Player modifiedPlayer = null;

		for (Player player : players) {
			if (player.getBoardSide() == identifier) {
				modifiedPlayer = player;
				break;
			}
		}

		if (modifiedPlayer == null)
			throw new NullPointerException();

		modifiedPlayer.update(modif);
	}

	@Override
	protected void update(Piece modifiedPiece, PieceModification modification) {
		
		if(modification.getType() == PieceModificationType.INITIALISATION)		
			return;
		
		IdentifiedPieceModification modif = new IdentifiedPieceModification(
				modifiedPiece.getCoordinates(), modification);		
		
		pendingModification.add(modif);
	}

	@Override
	protected void update(Tile modifiedTile, TileModification modification) {
		// Nothing to do here
	}

	@Override
	protected void update(Player modifiedPlayer, PlayerModification modification) {		
	
		switch(modification.getType()){
			case TURN_RESET:
				pendingModification.clear();
				break;
			case STATUS_CHANGE:
				break;
			case INITIALISATION:
				return;
		}
		
		IdentifiedPlayerModification modif = new IdentifiedPlayerModification(
				modifiedPlayer.getBoardSide(), modification);
		
		pendingModification.add(modif);
		
		networkCommunicator.notifyModification(pendingModification);
	}
}
