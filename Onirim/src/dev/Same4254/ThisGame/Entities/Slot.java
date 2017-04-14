package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardSymbols;
import dev.Same4254.ThisGame.States.GameState;

public class Slot extends Entity{

	public boolean filled;
	private Rectangle hitBox;
	public Card storedCard;
	
	private CardSymbols cardSymbol;
	
	private Game game;
	private GameState gameState;
	
	private boolean half;
	
	private Color color;
	
	public Slot(Game game, GameState gameState, int x, int y, int width, int height) {
		super(x, y, width, height);
		hitBox = new Rectangle(x, y, width - (width/10), height - (height/10));
		this.game = game;
		this.gameState = gameState;
	}

	public void update() {
		if(storedCard != null){
			filled = true;
			storedCard.setInSlot(this);
			storedCard.update();
		}
		else{
			filled = false;
		}
	}

	public void render(Graphics g) {
		if(storedCard != null && storedCard.isSelected() == false){
			storedCard.x = x; 
			storedCard.y = y;
//			System.out.println("should've rendered");
			storedCard.render(g);
//			g.setColor(Color.RED);
//			g.drawRect(x-5, y-5, width, height);
		}
		else if(storedCard != null && storedCard.isSelected() == true){
			storedCard.render(g);
		}
//		else{
////			g.setColor(Color.WHITE);
//			g.fillRect(x, y, width, height);
//		}
	}
	
	/*
	 * Render Method with a check if card is in the slot
	 * Will draw a box if there is no card
	 */
	public void renderWithCheck(Graphics g){
		if(storedCard == null){
			g.setColor(color);
			if(half)
				g.fillRect(x + (width/4), y + (height/4), width/2, height/2);
			else
				g.fillRect(x, y, width, height);
		}
		else{
			storedCard.render(g);
		}
	}
	
	public void addCard(Card c){
		if(c.getInSlot() != null)
			c.getInSlot().storedCard = null;
		
		storedCard = c;
		c.setInSlot(this);
		
		c.x = x;
		c.y = y;
		c.getHitBox().setLocation(c.x, c.y);
		c.clearAllDependencies();
	}
	
	public void swapCardsWith(Slot s, boolean inPro){
		if(s.storedCard != null){
			Card temp = new Card(game, gameState, s.storedCard, inPro);
			s.storedCard = storedCard;
			storedCard = temp;
		}
	}
	
	public void addCardWithoutNull(Card c){
		storedCard = c;
		c.setInSlot(this);
	}
	
	public Card getACopyCard(Card c, boolean inPro){
		Card temp = new Card(game, gameState, c, inPro);
		storedCard = temp;
		temp.setInSlot(this);
		return temp; 
	}
	
	public void setHalf(boolean half) {
		this.half = half;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public CardSymbols getCardSymbol() {
		return cardSymbol;
	}

	public void setCardSymbol(CardSymbols cardSymbol) {
		this.cardSymbol = cardSymbol;
	}
}
