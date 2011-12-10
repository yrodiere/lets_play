package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class JoinFrame extends JFrame {
	private static final long serialVersionUID = -5250033996009394034L;

	protected String PlayerName;

	public JoinFrame() {

		JFrame frame2 = new JFrame("Join Game");
		frame2.setVisible(true);
		frame2.setSize(500, 500);
		JPanel panel = new JPanel(new GridBagLayout());
		frame2.add(panel);
		frame2.getContentPane().add(panel, BorderLayout.NORTH);

		JLabel playerNameL = new JLabel("Player Name");
		JLabel ipAddressL = new JLabel("IP Address");
		JLabel passwordL = new JLabel("Password");
		JButton joinGameB = new JButton("Join Game");
		JButton exitB = new JButton("Exit");
		JFormattedTextField userName = new JFormattedTextField();
		JFormattedTextField ipAddress = new JFormattedTextField();

		JPasswordField password = new JPasswordField(9);

		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(10, 10, 10, 10);
		g.gridx = 0;
		g.gridy = 30;
		panel.add(playerNameL, g);

		g.gridx = 0;
		g.gridy = 60;
		panel.add(ipAddressL, g);

		g.gridx = 0;
		g.gridy = 90;
		panel.add(passwordL, g);

		userName.setPreferredSize(new Dimension(100, 20));

		g.gridx = 2;
		g.gridy = 30;
		panel.add(userName, g);

		g.gridx = 2;
		g.gridy = 60;
		panel.add(ipAddress, g);
		ipAddress.setPreferredSize(new Dimension(100, 20));

		g.gridx = 2;
		g.gridy = 90;
		panel.add(password, g);
		password.setPreferredSize(new Dimension(80, 20));
		password.setEchoChar('*');

		g.gridx = 0;
		g.gridy = 100;

		panel.add(exitB, g);

		g.gridx = 2;
		g.gridy = 100;

		panel.add(joinGameB, g);

	}
}
