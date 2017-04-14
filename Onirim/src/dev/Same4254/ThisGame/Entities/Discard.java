package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.States.GameState;

public class Discard extends Entity{
	private Slot slot;
	private Rectangle hitBox;
	private Game game;
	private GameState gameState;
	public static int size;
	
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
//		g.setColor(Color.MAGENTA);
//		g.drawRect(x, y, width, height);
		slot.render(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("MyFont", Font.PLAIN, 18));
		g.drawString("Size: " + size, x+20, y+height+15);
	}
	
	//Method for just simply adding a card to the discard without checking with other components
	public void addCardWithoutCheck(Card c){
//		System.out.println("------------------ \n Discard " + c.getType() + "\n----------------------------");
		slot.addCard(c);
		size++;
	}
	
	public void addCard(Card c){
		if(game.getCompleteDoor().isEnabled()){
			game.getCompleteDoor().setEnabled(false);
		}
		
		size++;
		
//		game.getDiscarded().addCard(c);
		
//		System.out.println(c);
//		gameState.getCardsOutOfDeck().remove(c);
		slot.addCard(c);
		c.setInPlayArea(false);
		c.setInProphecy(false);
		c.setMoveable(false);
		/*
		 * This is a key card, meaning the user is trying to continue, so the complete card button is disabled because it is 
		 * no imidietaely after that door was added to limbo
		 */
		if(c.getSymbol() == Card.CardSymbols.KEY){
			game.getCompleteDoor().setEnabled(false);
		}
		
		/*
		* The card in Limbo is still in play, and the player just discarded a key, check if limbo card is door or nightmare
		* While not prophosizing
		*/
		if(c.getSymbol() == Card.CardSymbols.KEY && Limbo.currentDrawnCard != null && !Prophecy.prophosizing){
			if(Limbo.currentDrawnCard.getType() == Card.CardTypes.DREAM){
				addCardWithoutCheck(Limbo.currentDrawnCard);
//				gameState.getCardsOutOfDeck().remove(Limbo.currentDrawnCard);
			}
			else if(Limbo.currentDrawnCard.getType() == Card.CardTypes.DOOR && Limbo.currentDrawnCard.getColor() == c.getColor()){
				gameState.getDoorsCompleted().addDoor(Limbo.currentDrawnCard);
			}
			else{
				gameState.getProphecy().clearAllProphecy();
				gameState.getProphecy().restockProphecy(); 
//				System.out.println("Start Pro");
			}
			Limbo.currentDrawnCard = null;
		}
		
		//Key drawn while not prophosizing, and limbo card is not in play, starts the prophecy
		else if(c.getSymbol() == Card.CardSymbols.KEY && !Prophecy.prophosizing){
			gameState.getProphecy().clearAllProphecy();
			gameState.getProphecy().restockProphecy(); 
//			System.out.println("Start Pro");
		}
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}

	public Slot getSlot() {
		return slot;
	}
}
