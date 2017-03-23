package dev.Same4254.ThisGame.States;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card;
import dev.Same4254.ThisGame.Entities.Deck;
import dev.Same4254.ThisGame.Entities.Discard;
import dev.Same4254.ThisGame.Entities.DoorsCompleted;
import dev.Same4254.ThisGame.Entities.Hand;
import dev.Same4254.ThisGame.Entities.Limbo;
import dev.Same4254.ThisGame.Entities.PlayArea;
import dev.Same4254.ThisGame.Entities.Prophecy;
import dev.Same4254.ThisGame.Input.KeyManager;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.dis.Display;

public class GameState extends State{
	
	private Deck deck;
	private ArrayList<Card> cardsOutOfDeck;
	private Limbo limbo;
	private PlayArea playArea;	
	private Hand hand;
	private Discard discard;
	private Font myFont;
	private Prophecy prophecy;
	private DoorsCompleted doorsCompleted;
	
	public GameState(Game game){
		super(game);
		
		cardsOutOfDeck = new ArrayList<Card>();
		
		discard = new Discard(game, this, 400, 10, 110, 160);
		hand = new Hand(game, 10, 420, 560, 170);
		playArea = new PlayArea(game, 10, 180, 550, 158);
		limbo = new Limbo(game, 585, 10, 200, 550);
		deck = new Deck(game, this, 10, 10, 100, 158);
		myFont = new Font("myFont", Font.PLAIN, 18);
		prophecy = new Prophecy(game, this, 0, 620, game.getDisplay().getFrame().getWidth(), 170);
		doorsCompleted = new DoorsCompleted(game, this, 0, 790, game.getDisplay().getFrame().getWidth(), 170);
	}
	
	public void update() {
		prophecy.update();
		deck.update();
		hand.update();
		limbo.update();
		playArea.update();
		discard.update();
		doorsCompleted.update();
		
		game.getMouseManager();
		//		for(int i = 0; i < cardsOutOfDeck.size(); i++){
//			cardsOutOfDeck.get(i).update();
//		}
		if(MouseManager.justReleased){
			game.getMouseManager();
			MouseManager.justReleased = false;
		}
		for(int i = 0; i < cardsOutOfDeck.size(); i++){
			if(cardsOutOfDeck.get(i).isSelected())
				cardsOutOfDeck.get(i).update();
		}
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(6));
		
		g2.setColor(Color.GRAY);
		g.fillRect(0, 0, game.getField().getWidth(), game.getField().getHeight());
		
		g2.setColor(Color.RED);
		g2.drawLine(0, 610, game.getDisplay().getFrame().getWidth(), 610);
		
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.WHITE);
		
		deck.render(g2);
		limbo.render(g2);
		playArea.render(g2);
		hand.render(g2);
		discard.render(g2);
		prophecy.render(g2);
		doorsCompleted.render(g2);
//		for(int i = 0; i < playerHandSlots.length; i++){
//			playerHandSlots[i].render(g2);
//		}
		
//		for(int i = 0; i < cardsOutOfDeck.size(); i++){
//			cardsOutOfDeck.get(i).render(g2);
//		}
		
		for(int i = 0; i < cardsOutOfDeck.size(); i++){
			if(cardsOutOfDeck.get(i).isSelected())
				cardsOutOfDeck.get(i).render(g2);
		}
		
//		g.setFont(myFont);
//		g.drawString(inRow, x, y);
	}
	
	public KeyManager getKeyManager(){return game.getKeyManager();}
	public Display getDisplay(){return this.game.getDisplay();}

	public Prophecy getProphecy() {
		return prophecy;
	}

	public Deck getDeck() {
		return deck;
	}

	public Limbo getLimbo() {
		return limbo;
	}

	public PlayArea getPlayArea() {
		return playArea;
	}

	public Hand getHand() {
		return hand;
	}

	public Discard getDiscard() {
		return discard;
	}

	public DoorsCompleted getDoorsCompleted() {
		return doorsCompleted;
	}

	public ArrayList<Card> getCardsOutOfDeck() {
		return cardsOutOfDeck;
	}
}