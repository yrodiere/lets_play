package implementation.draughts.logic;

import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.Coordinates;
import data.Piece;
import data.Player;
import data.Tile;

public class Game extends logic.Game {
	Game(Rules myRules, Board myBoard, List<Player> myPlayers) throws Exception {
		super(myRules, myBoard, myPlayers);

		init();
	}

	@Override
	protected SelectionReturnCode specificSelect(Player actor,
			Coordinates location) {

		Tile selectedTile = board.findTileAt(location);

		if (selectedTile == null) {
			return null;
		}

		Piece piece = selectedTile.getPiece();

		if (piece != null) {

			if (piece.getPlayer() == actor) {

				resetTurn();

				if (piece.select()) {
					selectedPiece = piece;
					return SelectionReturnCode.SUCCESS;
				}
			}

			return SelectionReturnCode.INVALID_PIECE_SELECTION;

		} else {
			if (selectedPiece == null) {
				return SelectionReturnCode.INVALID_PIECE_SELECTION;
			}

			if (selectedPiece.tryMove(selectedTile.getCoordinates())) {

				if (selectedPiece.select()) {
					return SelectionReturnCode.MOVE_PENDING;
				} else {
					return SelectionReturnCode.SUCCESS;
				}
			}

			return SelectionReturnCode.INVALID_TARGET_SELECTION;
		}
	}

	@Override
	protected EndTurnReturnCode specificEndTurn(Player actor) {

		checkForPieceUpgrade(actor);

		selectedPiece = null;

		Player otherPlayer = players.get(0);

		if (otherPlayer == actor) {
			otherPlayer = players.get(1);
		}

		List<Piece> otherPlayerPieces = otherPlayer.findOnBoardPieces();

		if (otherPlayerPieces.size() == 0) {
			actor.win();
			otherPlayer.loose();
			return EndTurnReturnCode.SUCCESS;
		}

		for (Piece piece : otherPlayerPieces) {
			if (piece.canMove()) {
				actor.endTurn();
				otherPlayer.endTurn();
				return EndTurnReturnCode.SUCCESS;
			}
		}

		actor.win();
		otherPlayer.loose();
		return EndTurnReturnCode.SUCCESS;
	}

	@Override
	protected void init() {
		Rules myRules = (Rules) rules;

		List<Piece> player1Pieces = new ArrayList<Piece>();

		List<Piece> player2Pieces = new ArrayList<Piece>();

		for (int c = 0; c < myRules.getBoardColumnCount(); c++) {

			for (int r = 0; r < myRules.getNbRowsOfPiece(); r++) {

				Piece p1 = initPiece(players.get(0), r, c);

				if (p1 != null) {
					player1Pieces.add(p1);
				}

				Piece p2 = initPiece(players.get(1), myRules.getBoardRowCount()
						- r, c);

				if (p1 != null) {
					player2Pieces.add(p2);
				}
			}
		}

		players.get(0).init(player1Pieces);

		players.get(1).init(player1Pieces);
	}

	protected Piece initPiece(Player owner, int row, int column) {

		Tile tile = board.findTileAt(new Coordinates(row, column));

		Rules myRules = (Rules) rules;

		if (tile != null && myRules.isTileUsed(tile)) {

			Piece piece = new Piece(owner, tile);

			MoveStrategy ms = new ManMoveStrategy(piece, board, myRules);

			piece.setMoveStrategy(ms);

			return piece;
		}

		return null;
	}

	protected void checkForPieceUpgrade(Player actor) {
		int lastRow = 0;

		if (actor == players.get(0)) {
			lastRow = rules.getBoardRowCount();
		}

		if (selectedPiece.getCoordinates().getRow() == lastRow) {
			selectedPiece.setMoveStrategy(new KingMoveStrategy(selectedPiece,
					board, (Rules) rules));
		}
	}

	protected void resetTurn() {
		board.resetTurn();

		for (Player p : players) {
			p.resetTurn();
		}
	}
}
