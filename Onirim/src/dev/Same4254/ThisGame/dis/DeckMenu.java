package dev.Same4254.ThisGame.dis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card;
import dev.Same4254.ThisGame.Entities.Slot;

public class DeckMenu extends JPanel{
	private static final long serialVersionUID = 1L;

	private Game game;
	
	private Display dis;
	
	private int[] counts;
	private String[] cards = {"Tan Sun: ", "Tan Moon: ", "Tan Key: ", "Red Sun: ", "Red Moon: ", "Red Key: ", "Blue Sun: ", "Blue Moon: ", "Blue Key: ", "Green Sun: ", "Green Moon: ", "Green Key: ", "Nightmare: ", "Green Door: ", "Red Door: ", "Blue Door: ", "Tan Door: "}; 
	private Font myFont;
	
	private int width, height;
	
	public DeckMenu(Game game){
		this.game = game;

		counts = new int[17];
		myFont = new Font("myFont", Font.PLAIN, 18);
		
		width = 300; height = 525;
		dis = new Display(this, "Deck Menu", width, height);
//		dis.getFrame().setLocationRelativeTo(game);
		dis.getFrame().setVisible(false);
		dis.getFrame().setResizable(false);
//		dis.getPanel().setBackground(Color.GRAY);
		dis.getFrame().setLocation(0,0);
		dis.getFrame().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public void addCard(Card c){
		counts[c.getDiscardIndex()]++;
		repaint();
	}
	
	public void clear(){
		for(int i = 0; i < counts.length; i++){
			counts[i] = 0;
		}
	}
	
	public void update(){
		clear();
//		dis.getFrame().setLocation(game.getX() - width, game.getY());
		for(Card c : game.getGameState().getDeck().getCards()){
			addCard(c);
		}
		
		for(Slot s : game.getGameState().getProphecy().getSlots()){
			if(s.storedCard != null){
				addCard(s.storedCard);
			}
		}
	}
	
	public void render(Graphics g){
		int y = 30;
		for(int i = 0; i < counts.length; i++){
			g.drawString(cards[i] + String.valueOf(counts[i]), 10, y);
			y+=30;
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.clearRect(0, 0, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		g.setFont(myFont);
		g.setColor(Color.WHITE);
		render(g);
		
	}
	
	public Display getDis() {
		return dis;
	}
	
}
