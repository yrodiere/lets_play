package implementation.draughts.logic;

import java.util.Iterator;

import data.Board;
import data.Coordinates;
import data.LimitedLinearBoardIterator;
import data.LinearBoardIterator;
import data.Coordinates.DirectionOnBoard;
import data.Piece;
import data.Tile;

abstract class MoveStrategy extends logic.MoveStrategy {
	protected final DirectionOnBoard FORWARD_LEFT;
	protected final DirectionOnBoard FORWARD_RIGHT;
	protected final DirectionOnBoard BACKWARD_LEFT;
	protected final DirectionOnBoard BACKWARD_RIGHT;

	private boolean hasMovedThisTurn;
	private boolean hasCapturedThisTurn;

	public MoveStrategy(Piece controlledPiece, Board board, Rules rules) {
		super(controlledPiece, board, rules, new MoveStrategyFactory(board,
				rules));

		onTurnBeginning();

		DirectionOnBoard playerSide = this.controlledPiece.getPlayer()
				.getBoardSide();
		if (playerSide.equals(DirectionOnBoard.UP)) {
			FORWARD_LEFT = DirectionOnBoard.DOWN_RIGHT;
			FORWARD_RIGHT = DirectionOnBoard.DOWN_LEFT;
			BACKWARD_LEFT = DirectionOnBoard.UP_RIGHT;
			BACKWARD_RIGHT = DirectionOnBoard.UP_LEFT;
		} else {
			assert (playerSide.equals(DirectionOnBoard.DOWN));
			FORWARD_LEFT = DirectionOnBoard.UP_LEFT;
			FORWARD_RIGHT = DirectionOnBoard.UP_RIGHT;
			BACKWARD_LEFT = DirectionOnBoard.DOWN_LEFT;
			BACKWARD_RIGHT = DirectionOnBoard.DOWN_RIGHT;
		}
	}

	private void onTurnBeginning() {
		hasMovedThisTurn = false;
		hasCapturedThisTurn = false;
	}

	@Override
	public void endTurn() {
		onTurnBeginning();
		super.endTurn();
	}

	@Override
	public void resetTurn() {
		onTurnBeginning();
		super.resetTurn();
	}

	/**
	 * Set the tiles reachable without capture all along the path in the given
	 * <code>direction</code>, up to the edge of the board or the first
	 * non-empty tile, excluded.<br>
	 * Takes the Draughts rules into account, i.e. if the piece already moved
	 * once, this method won't have any effect.
	 * 
	 * @param direction
	 *            The direction in which to "walk"
	 * @param maxMove
	 *            The maximum number of moves ; maxMove <= 0 won't do anything.
	 */
	protected boolean setReachablesNoCaptureTowards(DirectionOnBoard direction,
			int maxMove, boolean markThem) {
		boolean foundAtLeastOne = false;

		if (!hasMovedThisTurn) {
			int moveCount = 0;
			// We must stop just before a tile with a piece was reached.
			Iterator<Tile> it = new LimitedLinearBoardIterator(board,
					controlledPiece.getPosition(), direction) {
				@Override
				protected boolean mustStop() {
					return !nextTile.isEmpty();
				}
			};

			while (moveCount < maxMove && it.hasNext()) {
				Tile currentTile = it.next();
				++moveCount;
				if (markThem) {
					setReachable(currentTile, true);
				}
				foundAtLeastOne = true;
			}
		}

		return foundAtLeastOne;
	}

	/**
	 * Set the tile reachable with capture in the given <code>direction</code>,
	 * if it exists. <br>
	 * Takes the Draughts rules into account, i.e. if the piece already moved
	 * once without capturing, this method won't have any effect.
	 * 
	 * @param direction
	 *            The direction in which to "walk"
	 * @param maxMoveBeforeCapture
	 *            The maximum number of moves before the effective capture ;
	 *            maxMoveBeforeCapture <= 0 won't do anything.
	 */
	protected boolean setReachableWithCaptureTowards(
			DirectionOnBoard direction, int maxMoveBeforeCapture,
			boolean markThem) {
		boolean foundAtLeastOne = false;

		if (!hasMovedThisTurn || hasCapturedThisTurn) {
			int moveCount = 0;
			Tile lastTile = null;
			Piece lastPiece = null;
			Iterator<Tile> it = new LinearBoardIterator(board,
					controlledPiece.getPosition(), direction);

			// Try to find a reachable piece to capture
			while (lastPiece == null && moveCount < maxMoveBeforeCapture
					&& it.hasNext()) {
				lastTile = it.next();
				lastPiece = lastTile.getPiece();
				++moveCount;
			}

			if (lastPiece != null
					&& !lastPiece.getPlayer().equals(
							controlledPiece.getPlayer())
					&& !lastPiece.getOffBoardFlag()) {
				// A piece has been found, and it can be captured provided
				// there is some space to "land in"
				if (it.hasNext()) {
					lastTile = it.next();
					if (lastTile.isEmpty()) {
						if (markThem) {
							setReachable(lastTile, true);
						}
						foundAtLeastOne = true;
					}
				}
			}
		}

		return foundAtLeastOne;
	}

	protected abstract boolean scanReachables(boolean markThem);

	@Override
	public boolean select() {
		return scanReachables(true);
	}

	@Override
	public boolean canMove() {
		return scanReachables(false);
	}

	@Override
	public boolean tryMove(Coordinates target) {
		final Tile targetTile = board.findTileAt(target);

		if (!targetTile.isReachable()) {
			return false;
		}

		DirectionOnBoard direction = controlledPiece.getCoordinates()
				.directionTo(target);
		assert (direction != null);

		// Capture the pieces until "targetTile" is reached.
		runThroughPath(new LimitedLinearBoardIterator(board,
				controlledPiece.getPosition(), direction) {
			@Override
			protected boolean mustStop() {
				return targetTile.equals(nextTile);
			}
		});

		if (!hasMovedThisTurn) {
			hasMovedThisTurn = true;
		}

		movePiece(controlledPiece, targetTile);

		return true;
	}

	/**
	 * Runs through the path specified by "it", capturing pieces if necessary.
	 * 
	 * @param it
	 *            An iterator specifying the path
	 */
	private void runThroughPath(Iterator<Tile> it) {
		// Capture pieces on the way to the target
		while (it.hasNext()) {
			Tile currentTile = it.next();
			Piece currentPiece = currentTile.getPiece();

			if (currentPiece != null) {
				assert (!currentPiece.getPlayer().equals(
						controlledPiece.getPlayer()));
				setOffBoardFlag(currentPiece);
				hasCapturedThisTurn = true;
			}
		}
	}

}
