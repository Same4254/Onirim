package dev.Same4254.ThisGame.Entities;

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
	private MouseManager mouse;
	public boolean inDeck;
//	private int slotNum = -1;
	
	private int preX, preY;
	
	private boolean cardSelected;
	
	private boolean moveable;
	private boolean inProphecy;
	
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
	private ArrayList<Card> cardsOutOfDeck;
	private Discard discard;
	private Hand hand;
	private GameState gameState;
	
	public Card(Game game, GameState gameState, BufferedImage img, int x, int y, CardTypes cardType, CardSymbols cardSymbol, CardColors cardColor) {
		super(x, y, img.getWidth(), img.getHeight());
		texture = img;
		hitBox = new Rectangle(x, y, width, height);
		mouse = game.getMouseManager();
		inDeck = true;
		
		if(cardType == CardTypes.LOCATION)
			moveable = true;
		
		this.gameState = gameState;
		
		this.cardType = cardType;
		this.cardColor = cardColor;
		this.cardSymbol = cardSymbol;
		
		this.playArea = gameState.getPlayArea();
		this.discard = gameState.getDiscard();
		this.hand = gameState.getHand();
		
		cardsOutOfDeck = gameState.getCardsOutOfDeck();
		playAreaSlots = playArea.getSlots();
	}
	
	public Card(Game game, GameState gameState, Card card, boolean inPro){
		super(card.x, card.y, card.getTexture().getWidth(), card.getTexture().getWidth());
		
		this.gameState = gameState;
		
		inProphecy = inPro;
		mouse = game.getMouseManager();
		
		texture = card.getTexture();
		hitBox = card.getHitBox();
		
		cardColor = card.getColor();
		cardSymbol = card.getSymbol();
		cardType = card.getType();

		this.playArea = gameState.getPlayArea();
		this.discard = gameState.getDiscard();
		this.hand = gameState.getHand();
		
		cardsOutOfDeck = gameState.getCardsOutOfDeck();
		playAreaSlots = playArea.getSlots();
	}

	public String toString(){
		return String.valueOf(cardColor) + " " + String.valueOf(cardSymbol);
	}
	
	public void update() {
//		System.out.println(moveable);
		if(inProphecy && Prophecy.prophosizing){
			Slot[] proSlots = gameState.getProphecy().getSlots();
//			System.out.println(mouse.rightPressed);
			
			 if(mouse.rightPressed && hitBox.contains(mouse.getMouseX(),mouse.getMouseY())){
				System.out.println("end");
				discard.addCard(this);
//				x = (int) inSlot.getX();
//				y = (int) inSlot.getY();
				hitBox.setLocation(x, y);
				Prophecy.prophosizing = false;
//				gameState.getProphecy().end();
				mouse.rightPressed = false;
			}
			
			 else if(mouse.isMouseDragged() && cardSelected){ //&& hitBox.contains(mouse.getMouseX(),mouse.getMouseY()) && cardSelected){
				x = preX + mouse.getMouseX();
				
				for(int i = 0; i < proSlots.length; i++){
					if(proSlots[i].getHitBox().contains(x, y) && proSlots[i] != inSlot){
						inSlot.swapCardsWith(proSlots[i], true);
						break;
					}
				}
			}
			else if(mouse.justEntered && hitBox.contains(mouse.getMouseX(),mouse.getMouseY())){
				preX = x - mouse.getMouseX();
				mouse.justEntered = false;
				cardSelected = true;
			}
			
			else if(mouse.justReleased && cardSelected){
				cardSelected = false;
				x = (int) inSlot.getX();
				y = (int) inSlot.getY();
			}
			
			
			hitBox.setLocation(x, y);
		}
		
		else if(moveable && !Prophecy.prophosizing){
			 if(mouse.rightPressed && hitBox.contains(mouse.getMouseX(),mouse.getMouseY())){
					System.out.println("end");
					discard.addCard(this);
					x = (int) inSlot.getX();
					y = (int) inSlot.getY();
					hitBox.setLocation(x, y);
					Prophecy.prophosizing = false;
//					gameState.getProphecy().end();
					mouse.rightPressed = false;
				}
			
			if(mouse.isMouseDragged() && cardSelected){ //&& hitBox.contains(mouse.getMouseX(),mouse.getMouseY()) && cardSelected){
				x = preX + mouse.getMouseX();
				y = preY + mouse.getMouseY();
			}
			else if(mouse.justEntered && hitBox.contains(mouse.getMouseX(),mouse.getMouseY())){
//				System.out.println("Hhoasdf");
//				if(slotNum >= 0){
//					slots[slotNum].filled = false;
//				}
//				for(int i = 0; i < slots.length; i++){
//					if(slots[i].getHitBox().intersects(hitBox)){
//						slots[i].filled = false;
//						break;
//					}
//				}
				
				preX = x - mouse.getMouseX();
				preY = y - mouse.getMouseY();
				mouse.justEntered = false;
				cardSelected = true;
			}
			
			else if(mouse.justReleased && cardSelected){
				cardSelected = false;
//				for(int i = 0; i < slots.length; i++){
//					if(hitBox.intersects(slots[i].getHitBox()) && slots[i].filled == false){
////						slotNum = i;
//						x = (int)slots[i].getX();
//						y = (int)slots[i].getY();
//						slots[i].filled = true;
//						return;
//					}
//					if(i == slots.length - 1 && cardSelected == false){
//						x = slotX;
//						y = slotY;
//					}
//				}
				if(hitBox.intersects(hand.getHitBox())){
					hand.addCard(this);
				}
				else if(hitBox.intersects(playArea.getHitBox())){
					playArea.addCard(this);
					
				}
				
				else if(hitBox.intersects(discard.getHitBox())){
					discard.addCard(this);
				}
				
//				for(int i = 0; i < playAreaSlots.length; i++){
//					if(hitBox.intersects(playAreaSlots[i].getHitBox()) && playAreaSlots[i].filled == false){
////						slotNum = i;
//						x = (int)playAreaSlots[i].getX();
//						y = (int)playAreaSlots[i].getY();
//						playAreaSlots[i].filled = true;
//						return;
//					}
//					if(i == playAreaSlots.length - 1 && cardSelected == false){
//						x = slotX;
//						y = slotY;
//					}
//				}
//				System.out.println(slotNum);
				x = (int) inSlot.getX();
				y = (int) inSlot.getY();
			}
			
			hitBox.setLocation(x, y);
		}
		
	}

	public void render(Graphics g) {
		g.drawImage(texture, x, y, null);
//		g.fillRect((int)hitBox.getX(), (int)hitBox.getY(), (int)hitBox.getWidth() + 10, (int)hitBox.getHeight() + 10);
	}
	
	public void makeVisible(){
		inDeck = false;
	}

//	public int getSlotNum() {
//		return slotNum;
//	}
	
	
	
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

	public CardColors getColor(){return cardColor;}
	public CardTypes getType(){return cardType;}
	public CardSymbols getSymbol(){return cardSymbol;}
	public Slot getInSlot(){return inSlot;}
	public void setInSlot(Slot inSlot){this.inSlot = inSlot;}
}
