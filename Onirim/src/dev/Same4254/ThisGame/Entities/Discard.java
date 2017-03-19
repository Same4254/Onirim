package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.States.GameState;

public class Discard extends Entity{
	private Slot slot;
	private Rectangle hitBox;
	private Game game;
	private GameState gameState;
	
	public Discard(Game game, GameState gameState, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		this.gameState = gameState;
		
		slot = new Slot(game, game.getGameState(), x + (width - 100) / 2, y + (height-158) / 2, 100, 158);
		hitBox = new Rectangle(x, y, width, height);
	}

	public void update() {
		slot.update();
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(x, y, width, height);
		slot.render(g);
	}
	
	//Method for just simply adding a card to the discard without checking with other components
	public void addCardWithoutCheck(Card c){
		slot.addCard(c);
	}
	
	public void addCard(Card c){
		slot.addCard(c);
		
		//The card in Limbo is still in play, and the player just discarded a key, check if limbo card is door or nightmare
		if(c.getSymbol() == Card.CardSymbols.KEY && Limbo.currentDrawnCard != null){
			if(Limbo.currentDrawnCard.getType() == Card.CardTypes.DREAM){
				addCardWithoutCheck(Limbo.currentDrawnCard);
			}
			else if(Limbo.currentDrawnCard.getType() == Card.CardTypes.DOOR && Limbo.currentDrawnCard.getColor() == c.getColor()){
				gameState.getDoorsCompleted().addDoor(Limbo.currentDrawnCard);
			}
			else{
				gameState.getProphecy().clearAllProphecy();
				gameState.getProphecy().restockProphecy(); 
				System.out.println("Start Pro");
			}
			Limbo.currentDrawnCard = null;
		}
		
		//Key drawn while not prophosizing, and limbo card is not in play, starts the prophecy
		else if(c.getSymbol() == Card.CardSymbols.KEY && !Prophecy.prophosizing){
			gameState.getProphecy().clearAllProphecy();
			gameState.getProphecy().restockProphecy(); 
			System.out.println("Start Pro");
		}
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}

	public Slot getSlot() {
		return slot;
	}
}
