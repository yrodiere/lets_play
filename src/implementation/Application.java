package implementation;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import ui.GameFrame;
import ui.IconFactory;
import ui.MainFrame;

import network.NetworkCommunicator;
import network.NetworkCommunicator.ConnectToReturn;
import network.NetworkCommunicator.WaitForConnectionReturn;
import network.Synchroniser;

import data.Board;
import data.Coordinates.DirectionOnBoard;
import data.Player;

public class Application {
	
	private static logic.Rules rules = null;
	
	private static Board board = null;
	
	private static logic.Game game = null;
	
	private static List<Player> players;
	
	private static IconFactory iconFactory = null;
	
	private static HashMap<DirectionOnBoard,String> connectedPlayers = new HashMap<DirectionOnBoard, String>();
	
	private static NetworkCommunicator networkCommunicator = null;
	
	private static Synchroniser synchroniser = null;
	

	public static void main(String[] args) {
		new MainFrame();
	}	
	
	public static void createAGame(logic.Rules rules , int port, String playerName){
		
		Application.rules = rules;	
		
		if(rules == null)
			return;
		
		players = rules.createPlayers();
		
		connectedPlayers.put(rules.getHostPlayerSide(), playerName);
		
		if(initGame()){
//			networkCommunicator = new NetworkCommunicator(port);
//			
//			synchroniser = new Synchroniser(board, players, networkCommunicator);	
//			
//			for(Player p : players){
//				if(connectedPlayers.containsKey(p.getBoardSide())){
//					WaitForConnectionReturn r = networkCommunicator.waitForConnection(rules, connectedPlayers, p.getBoardSide());
//					
//					if(r != null){
//						connectedPlayers.put(p.getBoardSide(),r.getNewPlayerName());
//					}else{
//						JOptionPane.showMessageDialog(null, "Fail !");
//					}
//				}					
//			}
		}	
		
	}
	
	public static void joinAGame(InetAddress address, int port, String playerName){
		
			networkCommunicator = new NetworkCommunicator(port);
			
			ConnectToReturn r = networkCommunicator.connectTo(address,playerName);
			
			if(r != null){
				
				rules = r.getRules();			
				//r.get
				
				
				
				synchroniser = new Synchroniser(board, players, networkCommunicator);
			}
			
			

	}		
	
	public static void exit(){
		

		
	}
	
	private static boolean initGame() {	
		
		board = new Board(rules.getBoardRowCount(), rules.getBoardColumnCount());			
		
		try{		
			if(rules instanceof implementation.draughts.logic.Rules){				
					game = new implementation.draughts.logic.Game((implementation.draughts.logic.Rules) rules, board, players);
					iconFactory = new implementation.draughts.ui.IconFactory();
			}
			else if(rules instanceof implementation.noughts_crosses.logic.Rules){
					game = new implementation.noughts_crosses.logic.Game((implementation.noughts_crosses.logic.Rules) rules, board, players);
					iconFactory = new implementation.noughts_crosses.ui.IconFactory();
			}else{
				return false;
			}
			
			for(Player p : players){
				new GameFrame(game, rules, iconFactory, p);
			}
			
			players.get(0).endTurn();			
			
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error : " +  e.toString());
			e.printStackTrace();
			return false;
		}	
		
		return true;
	}	
}
