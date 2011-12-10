package ui;


import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import ui.BoardView.MouseListenerFactory;

import data.Coordinates;
import data.Piece;
import data.Piece.PieceModification;
import data.Player;
import data.Player.PlayerModification;
import data.Tile;
import data.Tile.TileModification;
import logic.Game;
import logic.Rules;
import data.GameObserver;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 3468997536530897034L;

	private static final Dimension BOARD_SIZE = new Dimension(500, 500);
	private final Game game;
	private final Rules rules;
	private final IconFactory iconFactory;
	private final Player actor;

	private BoardView board;

	GameObserver viewObserver = new ViewGameObserver();

	public GameFrame(Game game, Rules rules, IconFactory iconFactory,
			Player actor) {
		super("Game");
		if (game == null || rules == null || iconFactory == null
				|| actor == null) {
			throw new IllegalArgumentException("No argument should be null");
		}

		this.game = game;
		this.rules = rules;
		this.iconFactory = iconFactory;
		this.actor = actor;

		setResizable(false);
		setSize(BOARD_SIZE);
		setLocationRelativeTo(null);

		board = new BoardView(BOARD_SIZE, rules.getBoardRowCount(), rules.getBoardColumnCount(),
				actor.getBoardSide(), new TileClickListenerFactory());
		add(board);
		
		setVisible(true);
		
		game.addObserver(viewObserver);
	}

	private class TileClickListener extends MouseAdapter {
		private final Coordinates coordinates;

		private TileClickListener(Coordinates coordinates) {
			this.coordinates = coordinates;
		}

		@Override
		public void mouseReleased(MouseEvent event) {			
			System.err.println("You clicked at " + coordinates);
			//TODO
			switch (game.select(actor, coordinates)) {
			case SUCCESS:
				game.endTurn(actor);				
				break;
			case INVALID_PIECE_SELECTION:
				JOptionPane.showMessageDialog(GameFrame.this, "Invalid piece selection !");
				break;
			case INVALID_TARGET_SELECTION:
				JOptionPane.showMessageDialog(GameFrame.this, "Invalid target selection !");
				break;
			case NOT_PLAYING:	
				JOptionPane.showMessageDialog(GameFrame.this, "Be patient it is not your turn !");
				break;		
			case MOVE_PENDING:
			default:

			};
		}
	}

	private class TileClickListenerFactory implements MouseListenerFactory {
		@Override
		public MouseListener createListener(Coordinates coord) {
			return new TileClickListener(coord);
		}
	}

	private void redrawPiece(Piece piece, int typeHashCode) {
		Tile position = piece.getPosition();
		if (position != null) {
			redraw(position, position.isReachable(), position.isSelected(),
					piece, typeHashCode);
		}
	}

	private void redrawTile(Tile tile, boolean reachable, boolean selected,
			Piece piece) {
		redraw(tile, reachable, selected, piece,
				(piece != null ? piece.getTypeHashCode() : 0));
	}

	private void redraw(Tile tile, boolean reachable, boolean selected,
			Piece piece, int typeHashCode) {
		TileViewDescription tileDesc = iconFactory.createTileViewDescription(tile, reachable, selected);
		PieceViewDescription pieceDesc = (piece == null ? null :iconFactory.createPieceViewDescription(piece, typeHashCode));
		board.repaint(tile.getCoordinates(), tileDesc, pieceDesc);
	}

	private class ViewGameObserver extends GameObserver {
		protected void update(Piece modifiedPiece,
				PieceModification modification) {
			switch (modification.getType()) {
			case CHANGED_STRATEGY:
			case FLAGGED_OFFBOARD:
				assert (modifiedPiece.getCoordinates().equals(modification
						.getNewCoordinates()));
				if (modifiedPiece.getPosition() != null) {
					redrawPiece(modifiedPiece,
							modification.getNewTypeHashCode());
				}
				break;
			case INITIALISATION:
			case MOVED_OFFBOARD:
			case MOVED_ONBOARD:
			case TURN_RESET:
				// Handled when the tile the piece is/was on is reset
				// Do nothing
				break;

			}
		}

		protected void update(Tile modifiedTile, TileModification modification) {
			switch (modification.getType()) {
			case INITIALISATION:
			case FLAGS_CHANGED:
			case PIECE_CHANGED:
			case TURN_RESET:
				redrawTile(modifiedTile, modification.isNowReachable(),
						modification.isNowSelected(),
						modification.getNewPiece());
				break;
			}

		}

		protected void update(Player modifiedPlayer,
				PlayerModification modification) {
			if (actor.equals(modifiedPlayer)) {
				switch (modification.getNewStatus()) {
				case PLAYING:
					board.setEnabled(true);
					break;
				case WAITING:
					board.setEnabled(true);
					break;
				case DRAW:
					board.setEnabled(false);
					JOptionPane.showMessageDialog(GameFrame.this, "Draw!");
					dispose();
					break;
				case LOSS:
					board.setEnabled(false);
					JOptionPane.showMessageDialog(GameFrame.this, "You loose!");
					dispose();
					break;
				case VICTORY:
					board.setEnabled(false);
					JOptionPane.showMessageDialog(GameFrame.this, "You win!");
					dispose();
					break;
				}
			}

		}
	};

}
