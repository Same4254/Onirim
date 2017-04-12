package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.States.GameState;

public class Card extends Entity{

	private BufferedImage texture;
	private Rectangle hitBox;
	public boolean inDeck;
//	private int slotNum = -1;
	
	private int preX, preY;
	
	private boolean cardSelected;
	
	private boolean moveable;
	private boolean inProphecy;
	private boolean inPlayArea;
	private boolean used;
	private boolean completed;
	
	public static enum CardSymbols{
		SUN, MOON, KEY, NIGHTMARE, DOOR
	}
	
	public static enum CardTypes{
		LOCATION, DOOR, DREAM
	}
	
	
	public static enum CardColors{
		BLUE, GREEN, RED, TAN, BLACK
	}
	
	private CardColors cardColor;
	private CardTypes cardType;
	private CardSymbols cardSymbol;
	
//	public int slotX, slotY;
	
	private PlayArea playArea;
	private Slot[] playAreaSlots;
	private Slot inSlot;
//	private ArrayList<Card> cardsOutOfDeck;
	private Discard discard;
	private Hand hand;
	private GameState gameState;
	private Game game;
	private int discardIndex;
	
	public Card(Game game, GameState gameState, BufferedImage img, int x, int y, CardTypes cardType, CardSymbols cardSymbol, CardColors cardColor, int discardIndex){
		super(x, y, 100, 158);
		texture = img;
		hitBox = new Rectangle(x, y, width, height);
		inDeck = true;
		
		if(cardType == CardTypes.LOCATION)
			moveable = true;
		
		this.gameState = gameState;
		this.game = game;
		this.discardIndex = discardIndex;
		
		this.cardType = cardType;
		this.cardColor = cardColor;
		this.cardSymbol = cardSymbol;
		
		this.playArea = gameState.getPlayArea();
		this.discard = gameState.getDiscard();
		this.hand = gameState.getHand();
		
//		cardsOutOfDeck = gameState.getCardsOutOfDeck();
		playAreaSlots = playArea.getSlots();
	}
	
	public Card(Game game, GameState gameState, Card card, boolean inPro){
		super(card.x, card.y, card.getTexture().getWidth(), card.getTexture().getWidth());
		
		this.gameState = gameState;
		
		inProphecy = inPro;
		
		texture = card.getTexture();
		hitBox = card.getHitBox();
		
		cardColor = card.getColor();
		cardSymbol = card.getSymbol();
		cardType = card.getType();

		this.playArea = gameState.getPlayArea();
		this.discard = gameState.getDiscard();
		this.hand = gameState.getHand();
		this.discardIndex = card.getDiscardIndex();
		
//		cardsOutOfDeck = gameState.getCardsOutOfDeck();
		playAreaSlots = playArea.getSlots();
	}

	public String toString(){
		return String.valueOf(cardColor) + " " + String.valueOf(cardSymbol);
	}
	
	public void update() {
		hitBox.setLocation(x, y);
		
		if(cardSymbol == CardSymbols.NIGHTMARE && !inProphecy && MouseManager.rightPressed && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY)){
			discard.addCard(this);
			hand.clear();
			hand.refill();
			game.getGameState().getLimbo().shuffleToDeck();
			Limbo.currentDrawnCard = null;
			
			MouseManager.rightPressed = false;
		}
		
		/*****************************************************************************************************************
		 * Card in Full Hand not Prophosizing 
		 */
		else if(moveable && !Prophecy.prophosizing && !inPlayArea){
			if(MouseManager.rightPressed && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY) && !MouseManager.mouseDragged){
//				System.out.println("end");
				discard.addCard(this);
				hitBox.setLocation(x, y);
				MouseManager.rightPressed = false;
			 }
			
			else if(MouseManager.mouseDragged && cardSelected){ 
				x = preX + MouseManager.mouseX;
				y = preY + MouseManager.mouseY;
			}
			else if(MouseManager.justEntered && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY)){
				preX = x - MouseManager.mouseX;
				preY = y - MouseManager.mouseY;
				MouseManager.justEntered = false;
				cardSelected = true;
				System.out.println("1");
			}	
			
			else if(MouseManager.justReleased && cardSelected){
				cardSelected = false;

				if(hitBox.intersects(hand.getHitBox())){
					hand.addCard(this);
				}
				
				else if(hitBox.intersects(playArea.getHitBox())){
					playArea.addCard(this);
					
				}
				
				else if(hitBox.intersects(discard.getHitBox())){
					discard.addCard(this);
				}
				
				x = (int) inSlot.getX();
				y = (int) inSlot.getY();
			}
			
			hitBox.setLocation(x, y);
		}
		
		/***************************************************************************
		 * Card In Prophecy and Prophosizing 
		 */
		else if(inProphecy && Prophecy.prophosizing){
			Slot[] proSlots = gameState.getProphecy().getSlots();
//			System.out.println(cardSelected);
			
			if(Prophecy.prophosizing && cardType != CardTypes.DOOR &&  MouseManager.rightPressed && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY) && !MouseManager.mouseDragged){
//				System.out.println("Pro: " + Prophecy.prophosizing);
				discard.addCard(this);
				hitBox.setLocation(x, y);
				MouseManager.rightPressed = false;
			 }
			
			else if(MouseManager.justEntered && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY)){
				preY = y - MouseManager.mouseY;
				MouseManager.justEntered = false;
				cardSelected = true;
				System.out.println("2");
			}
			
			else if(MouseManager.mouseDragged && cardSelected){
				y = preY + MouseManager.mouseY;
				
				for(int i = 0; i < proSlots.length; i++){
					if(proSlots[i].getHitBox().contains(x, y) && proSlots[i] != inSlot){
						inSlot.swapCardsWith(proSlots[i], true);
						break;
					}
				}
			}
			 
			else if(MouseManager.justReleased && cardSelected){
				cardSelected = false;
				x = (int) inSlot.getX();
				y = (int) inSlot.getY();
			}
			
			hitBox.setLocation(x, y);
		}

		else if(inProphecy && !Prophecy.prophosizing){
			
		}
		
		else if(completed && MouseManager.rightPressed && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY) && !MouseManager.mouseDragged && !Prophecy.prophosizing){
			if(Limbo.currentDrawnCard.getType() == CardTypes.DREAM){
				if(game.isLostFound()){
					gameState.getDoorsCompleted().getOrder().add(0, cardColor);
					discard.addCard(Limbo.currentDrawnCard);
					gameState.getLimbo().addCard(this);
					Limbo.currentDrawnCard = this;
				}
				else{
					discard.addCard(Limbo.currentDrawnCard);
					gameState.getLimbo().addCard(this);
					Limbo.currentDrawnCard = this;
				}
			}
		}
		
		/*
		 * Card from the Play Area
		 */
		else if(moveable && inPlayArea && !Prophecy.prophosizing){
			if(MouseManager.mouseDragged && cardSelected){ 
				x = preX + MouseManager.mouseX;
				y = preY + MouseManager.mouseY;
			}
			else if(MouseManager.justEntered && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY)){
				preX = x - MouseManager.mouseX;
				preY = y - MouseManager.mouseY;
				MouseManager.justEntered = false;
				cardSelected = true;
				System.out.println("3");
			}	
			
			else if(MouseManager.justReleased && cardSelected){
				cardSelected = false;

				if(hitBox.intersects(hand.getHitBox())){
					hand.addCard(this);
//					PlayArea.inRow--;
				}
				else if(hitBox.intersects(playArea.getHitBox())){
					playArea.addCard(this);
//					PlayArea.inRow++;
				}
				
				else if(hitBox.intersects(discard.getHitBox())){
					discard.addCard(this);
//					PlayArea.inRow--;
				}
				
				x = (int) inSlot.getX();
				y = (int) inSlot.getY();
			}
			
			hitBox.setLocation(x, y);
		}
		
		/******************************************************************************************************************
		 * Card in Non-Full Hand Not Prophosizing
		 */
		else if(!moveable && !Prophecy.prophosizing && cardSymbol == CardSymbols.KEY){
			if(MouseManager.rightPressed && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY) && !MouseManager.mouseDragged){
				if(Limbo.currentDrawnCard != null && (Limbo.currentDrawnCard.getColor() == cardColor || Limbo.currentDrawnCard.getSymbol() == CardSymbols.NIGHTMARE)){
					discard.addCard(this);
					Limbo.currentDrawnCard = null;
				}
				
				hitBox.setLocation(x, y);
				MouseManager.rightPressed = false;
			 }
		}
		
			/*****************************************************************************************************************
			 * Selectable Door in Limbo(Comes from getting 3 in a row)
			 */
	//		if(selectableDoor){
	//			gameState.getDoorsCompleted().addDoor(this);
	//			System.out.println("GGGGLSDFSDFASDFASDGAr");
	//		}
			hitBox.setLocation(x, y);
	}

	public void render(Graphics g) {
//		if(inProphecy && Prophecy.prophosizing){
//			g.setColor(Color.GREEN);
//		}
//		else if(!moveable && !inProphecy){
//			g.setColor(Color.RED);
//		}
//		else if(moveable && !Prophecy.prophosizing){
//			g.setColor(Color.PINK);
//		}
//		else if(inProphecy && !Prophecy.prophosizing){
//			g.setColor(Color.MAGENTA);
//		}
		if(used){
			g.setColor(Color.RED);
			g.fillRect(x-5, y-5, 110, 168);
		}
		g.drawImage(texture, x, y, 100, 158, null);
		
//		g.fillRect((int)hitBox.getX(), (int)hitBox.getY(), (int)hitBox.getWidth(), (int)hitBox.getHeight());
	}
	
	public void clearAllDependencies(){
		inDeck = false;
		inPlayArea = false;
		inProphecy = false;
		completed = false;
		used = false;
	}
	
	public void makeVisible(){
		inDeck = false;
	}

	public boolean isSelected(){return cardSelected;}
	public Rectangle getHitBox() {
		return hitBox;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public boolean isInProphecy() {
		return inProphecy;
	}

	public void setInProphecy(boolean inProphecy) {
		this.inProphecy = inProphecy;
	}

	public boolean isInPlayArea() {
		return inPlayArea;
	}

	public void setInPlayArea(boolean inPlayArea) {
		this.inPlayArea = inPlayArea;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	
	public int getDiscardIndex() {
		return discardIndex;
	}

	public CardColors getColor(){return cardColor;}
	public CardTypes getType(){return cardType;}
	public CardSymbols getSymbol(){return cardSymbol;}
	public Slot getInSlot(){return inSlot;}
	public void setInSlot(Slot inSlot){this.inSlot = inSlot;}
}
