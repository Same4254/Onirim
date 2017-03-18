package dev.Same4254.ThisGame.dis;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dev.Same4254.ThisGame.Game;

public class Display {
	private JFrame frame;
	private JPanel panel;
	
	private int width, height;
	
	public Display(Game game, String title, int width, int height){
		this.height = height;
		this.width = width;
		
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		panel = game;
		panel.setPreferredSize(new Dimension(width, height));
		panel.setMaximumSize(new Dimension(width, height));
		panel.setMinimumSize(new Dimension(width, height));
		panel.setFocusable(false);
		panel.setBackground(Color.GRAY);
		
		panel.addKeyListener(game.getKeyManager());
		frame.add(panel);
		frame.pack();
	}
	
//	public Canvas getCanvas() {
//		return panel;
//	}

	public JFrame getFrame() {
		return frame;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public JPanel getPanel() {
		return panel;
	}
}
