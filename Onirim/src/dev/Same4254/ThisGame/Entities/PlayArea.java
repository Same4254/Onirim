package dev.Same4254.ThisGame.Entities;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.States.GameState;

public class PlayArea extends Entity{
	private Slot[] slots;
	private Rectangle hitBox;
	public static int inRow;
	private Game game;
	private GameState gameState;
	
	public PlayArea(Game game, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		this.gameState = game.getGameState();
		
		slots = new Slot[5];
		
		int hSpace = 0;
		inRow = 0;
		
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, gameState, x + hSpace, y, 100, height);
			hSpace += 110;
		}
		
		hitBox = new Rectangle(x, y, width, height);
	}

	public void update() {
		for(int i = 0; i < slots.length; i++){
			slots[i].update();
		}
		
		if(slots[4].storedCard != null){
			slots[0].storedCard = slots[1].storedCard;
			slots[1].storedCard = slots[2].storedCard;
			slots[2].storedCard = slots[3].storedCard;
			slots[3].storedCard = slots[4].storedCard;
			slots[4].storedCard = null;
			
			slots[0].storedCard.update();
			slots[1].storedCard.update();
			slots[2].storedCard.update();
			slots[3].storedCard.update();
		}
		
		if(Hand.handSize==5){
			for(Slot s : slots){
				if(s.storedCard != null){
					s.storedCard.setMoveable(false);
					break;
				}
			}
		}
		
		int temp = 0;
		for(int i = slots.length - 1; i >= 0; i--){
			if(i == 0  && slots[i].storedCard != null){
				if(slots[i+1].storedCard != null && slots[i].storedCard.getColor() == slots[i+1].storedCard.getColor())
					temp++;
				else if(slots[i+1].storedCard == null){
					temp = 1;
				}
			}
			
			else if(slots[i].storedCard != null && i != 0){
//				System.out.println("CHeck");
				if(slots[i - 1].storedCard.getColor() == slots[i].storedCard.getColor() && temp != 3 && !slots[i].storedCard.isUsed()){
					temp++;
//					System.out.println("ADD");
				}
				else{
					temp++;
					break;
				}
			}
		}
		
		if(temp % 3 != 0)
			inRow = temp % 3;
		else{
			inRow = temp;
		}
		
		if(inRow == 3){
//			mark:
//			for(int i = slots.length - 1; i >= 0; i--){
//				if(slots[i].storedCard != null){
//					ArrayList<Card> deck = game.getGameState().getDeck().getCards();
//					for(Card c : deck){
//						if(c.getType() == Card.CardTypes.DOOR && c.getColor() == slots[i].storedCard.getColor()){
//							
//							break mark;
//						}
//					}
//				}
//			}
			game.getCompleteDoor().setEnabled(true);
			inRow = 0;
		}
		else{
			game.getCompleteDoor().setEnabled(false);
		}
	}

	/*
	 * The player is an idiot that needs help
	 * This is called when the player does something other than complete the door
	 */
	public void overRide(){
		for(int i = slots.length - 1; i >= 0; i--){
			if(slots[i].storedCard != null){
				slots[i].storedCard.setUsed(true);
				slots[i].storedCard.setMoveable(false);
				break;
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < slots.length; i++){
			slots[i].render(g);
		}
//		g.setColor(Color.red);
		g.drawRect(x, y, width, height);
		g.setFont(new Font("myFont", Font.PLAIN, 18));
		g.drawString("In Row: " + String.valueOf(inRow), x + 150, y - 20);
	}
	
	public void addCard(Card c){
		for(int i = 0; i < slots.length; i++){
			if(i >= 1){
				if(slots[i].storedCard == null && slots[i - 1].storedCard != null && slots[i - 1].storedCard.getSymbol() != c.getSymbol()){
					slots[i - 1].storedCard.setMoveable(false);
					slots[i].addCard(c);
					c.setInPlayArea(true);
					c.setInProphecy(false);
					System.out.println("Card Added");
					break;
				}
			}
			else if(slots[i].storedCard == null){
				slots[i].addCard(c);
				System.out.println("Card Added");
				break;
			}
		}
	}

	public Slot[] getSlots() {
		return slots;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}
}
