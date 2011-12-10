package ui;

import implementation.Application;
import logic.Rules;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Coordinates.DirectionOnBoard;

public class HostFrame extends JFrame {
	private static final long serialVersionUID = 4965549780427146070L;
	
	private JFormattedTextField userName = new JFormattedTextField();	
	private JFormattedTextField gameName = new JFormattedTextField();
	private JFormattedTextField password = new JFormattedTextField();
	private JLabel boardSizeL = new JLabel("Board size");
	
	private JComboBox game = new JComboBox(gameOptions);
	private JComboBox boardSize = new JComboBox(boardSizeOptions);

	private static final String[] gameOptions = { "Draughts", "X's and O's" };
	private static final String[] boardSizeOptions = { "3","4", "8", "10" };


	public HostFrame() {
		JFrame frame2 = new JFrame("Host Game");
		frame2.setVisible(true);
		frame2.setSize(500, 500);
		JPanel panel = new JPanel(new GridBagLayout());
		frame2.add(panel);
		frame2.getContentPane().add(panel, BorderLayout.NORTH);

		JLabel playerNameL = new JLabel("Player Name");
		JLabel gameNameL = new JLabel("Game Name");
		JLabel passwordL = new JLabel("Password");
		JLabel gameL = new JLabel("Game");		
		JButton hostGameB = new JButton("Host Game");
		JButton exitB = new JButton("Exit");

		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(10, 10, 10, 10);
		g.gridx = 0;
		g.gridy = 6;
		panel.add(playerNameL, g);

		g.gridx = 0;
		g.gridy = 12;
		panel.add(gameNameL, g);

		g.gridx = 0;
		g.gridy = 18;
		panel.add(passwordL, g);
		
		userName.setPreferredSize(new Dimension(100, 20));

		g.gridx = 2;
		g.gridy = 6;
		panel.add(userName, g);

		g.gridx = 2;
		g.gridy = 12;
		panel.add(gameName, g);
		gameName.setPreferredSize(new Dimension(100, 20));
		gameName.setEnabled(false);

		g.gridx = 2;
		g.gridy = 18;
		panel.add(password, g);
		password.setPreferredSize(new Dimension(100, 20));
		password.setEnabled(false);

		g.gridx = 0;
		g.gridy = 20;
		panel.add(gameL, g);

		g.gridx = 0;
		g.gridy = 22;
		panel.add(boardSizeL, g);

		g.gridx = 2;
		g.gridy = 20;
		panel.add(game, g);
		game.setPreferredSize(new Dimension(100, 20));

		g.gridx = 2;
		g.gridy = 22;
		panel.add(boardSize, g);
		boardSize.setPreferredSize(new Dimension(100, 20));
		

		g.gridx = 0;
		g.gridy = 100;

		panel.add(exitB, g);

		g.gridx = 2;
		g.gridy = 100;

		// Open correct board based on selection
		panel.add(hostGameB, g);

		hostGameB.addActionListener(new HostAction());
		game.addActionListener(new ChooseGameAction());

	}

	private class HostAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			Rules rules = null;
			
			switch(game.getSelectedIndex()){
			case 0:
				rules = new implementation.draughts.logic.Rules(Integer.parseInt(boardSize.getSelectedItem().toString()), DirectionOnBoard.DOWN);
				break;
			case 1:
				rules = new implementation.noughts_crosses.logic.Rules(3, DirectionOnBoard.DOWN);
				break;
			}
			HostFrame.this.dispose();
			Application.createAGame(rules,55,userName.getText());
			
		}

	}
	
	private class ChooseGameAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(game.getSelectedIndex()){
			case 0:
				boardSize.setVisible(true);	
				boardSizeL.setVisible(true);
				break;
			case 1:
				boardSize.setVisible(false);
				boardSizeL.setVisible(false);
				
			}
		}
	}
}
