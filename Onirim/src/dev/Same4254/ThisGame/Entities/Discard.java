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

	public void addCard(Card c){
		slot.addCard(c);
		
		if(c.getSymbol() == Card.CardSymbols.KEY && !Prophecy.prophosizing){
			gameState.getProphecy().clearAllProphecy();
			gameState.getProphecy().restockProphecy(); 
			Prophecy.prophosizing = true;
//			for(int i = 0; i < gameState.getLimbo().getSlots().length; i++){
//				if(gameState.getLimbo().getSlots()[i].storedCard.getType() == Card.CardTypes.DOOR && c.getColor() == gameState.getLimbo().getSlots()[i].storedCard.getColor()){
//					
//				}
//			}
		}
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}

	public Slot getSlot() {
		return slot;
	}
}
