package network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import data.Coordinates;
import data.Coordinates.DirectionOnBoard;
import data.Piece.PieceModification;
import data.Player.PlayerModification;

public class NetworkCommunicator {

	public interface IdentifiedModification {
		Object getIdentifier();

		Object getModification();
	}

	private class NetworkReader extends Thread {

		public void run() {

			while (true) {
				try {
					InputStream is = socket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);

					@SuppressWarnings("unchecked")
					List<IdentifiedModification> modifications = (List<IdentifiedModification>) ois
							.readObject();

					for (IdentifiedModification modification : modifications) {
						if (modifications instanceof IdentifiedPlayerModification) {

							synchroniser.handleRemotePlayerNotification(
									(DirectionOnBoard) modification
											.getIdentifier(),
									(PlayerModification) modification
											.getModification());

						} else if (modifications instanceof IdentifiedPieceModification) {

							synchroniser.handleRemotePieceNotification(
									(Coordinates) modification.getIdentifier(),
									(PieceModification) modification
											.getModification());
						}
					}
					ois.close();
					is.close();
				} catch (Exception e) {

				}
			}

		}
	}

	public static class IdentifiedPlayerModification implements
			IdentifiedModification {

		private DirectionOnBoard identifier;
		private PlayerModification modification;

		public IdentifiedPlayerModification(DirectionOnBoard identifier,
				PlayerModification modification) {

			this.identifier = identifier;
			this.modification = modification;
		}

		public Object getIdentifier() {
			return identifier;
		}

		public Object getModification() {
			return modification;
		}
	}

	public static class IdentifiedPieceModification implements
			IdentifiedModification {

		private Coordinates identifier;
		private PieceModification modification;

		public IdentifiedPieceModification(Coordinates identifier,
				PieceModification modification) {

			this.identifier = identifier;
			this.modification = modification;
		}

		public Object getIdentifier() {
			return identifier;
		}

		public Object getModification() {
			return modification;
		}
	}

	private int port;

	private Socket socket;

	private Synchroniser synchroniser;

	private NetworkReader reader;

	public NetworkCommunicator(int port) {
		this.port = port;
		this.reader = new NetworkReader();
	}

	public boolean waitForConnection() {
		try {
			ServerSocket ss = new ServerSocket(port);
			socket = ss.accept();
		} catch (Exception e) {
			return false;
		}

		reader.start();

		return true;
	}

	public boolean connectTo(InetAddress adress) {
		try {
			socket = new Socket(adress, port);
		} catch (Exception e) {
			return false;
		}

		reader.start();

		return true;
	}

	public boolean notifyModification(
			List<IdentifiedModification> modifications) {
		try {
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);

			oos.writeObject(modifications);

			oos.close();
			os.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	

}
