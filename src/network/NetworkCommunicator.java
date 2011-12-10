package network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import logic.Rules;

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
	
	public static final class ConnectToReturn implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1372587827073256727L;
		
		private final Rules rules;
		private final HashMap<DirectionOnBoard,String> remotePlayers;		
		private final DirectionOnBoard nextDoBAvailable;
		
		public ConnectToReturn(Rules rules, HashMap<DirectionOnBoard, String> remotePlayers, DirectionOnBoard nextDoBAvailable) {
			super();
			this.rules = rules;
			this.remotePlayers = remotePlayers;
			this.nextDoBAvailable = nextDoBAvailable;
		}

		public Rules getRules() {
			return rules;
		}
		
		public HashMap<DirectionOnBoard, String> getRemotePlayers() {
			return remotePlayers;
		}

		public DirectionOnBoard getNextDoBAvailable() {
			return nextDoBAvailable;
		}				
	}
	
	public static final class WaitForConnectionReturn implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2528922586421883747L;
	
		private final String newPlayerName;
		
		public WaitForConnectionReturn(String newPlayerName) {
			super();
			this.newPlayerName = newPlayerName;

		}

		public String getNewPlayerName() {
			return newPlayerName;
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

	public WaitForConnectionReturn waitForConnection(Rules rules, HashMap<DirectionOnBoard,String> players, DirectionOnBoard nextDoBAvailable) {
		
		WaitForConnectionReturn waitForConnectionReturn = null;
		
		try {
			ServerSocket ss = new ServerSocket(port);
			socket = ss.accept();
			
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);

			oos.writeObject(new ConnectToReturn(rules, players,nextDoBAvailable));

			oos.close();
			os.close();		
			
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);

			waitForConnectionReturn = (WaitForConnectionReturn) ois.readObject();

			ois.close();
			is.close();
			
		} catch (Exception e) {
			return null;
		}

		reader.start();

		return waitForConnectionReturn;
	}

	public ConnectToReturn connectTo(InetAddress address, String playerName) {
		
		ConnectToReturn connectToReturn = null;
		
		try {
			socket = new Socket(address, port);
			
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);

			connectToReturn = (ConnectToReturn) ois.readObject();

			ois.close();
			is.close();
			
			ServerSocket ss = new ServerSocket(port);
			socket = ss.accept();
			
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);

			oos.writeObject(new WaitForConnectionReturn(playerName));

			oos.close();
			os.close();		
		} catch (Exception e) {
			return null;
		}

		reader.start();

		return connectToReturn;
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
	
	public void setSynchronizer(Synchroniser synchroniser){
		this.synchroniser = synchroniser;
	}
}
