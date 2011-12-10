package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -8808883923263763897L;
	
	public MainFrame() {
		// Create Opening Window
		JFrame frame = new JFrame("Let's Play");
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a Panel and a Label
		JLabel label = new JLabel("Let's Play Game");
		JPanel panel = new JPanel(new GridBagLayout());

		// Put the panel in the frame.
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		// Add buttons
		JButton joinGame = new JButton("Join Game");
		JButton hostGame = new JButton("Host Game");
		joinGame.setPreferredSize(new Dimension(150, 70));
		hostGame.setPreferredSize(new Dimension(150, 70));

		// Create grid
		GridBagConstraints c = new GridBagConstraints();

		// Assign items to grid
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 10);
		panel.add(label, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 10;
		c.gridwidth = 10;
		panel.add(joinGame, c);
		joinGame.addActionListener(new JoinGameAction());
		c.gridx = 0;
		c.gridy = 29;
		panel.add(hostGame, c);
		hostGame.addActionListener(new HostGameAction());
	}

	// What happens if Host Game is pressed
	private static class HostGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new HostFrame();
		}
	}

	// What happens if Join Game is pressed
	private static class JoinGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new JoinFrame();
		}
	}
}
