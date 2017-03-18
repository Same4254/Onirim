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
		
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, gameState, x + hSpace, y, 100, height);
			hSpace += 110;
		}
		
//		width = slots[slots.length - 1].x + slots[slots.length - 1].width;
		
		hitBox = new Rectangle(x, y, width, height);
	}

	public void update() {
		for(int i = 0; i < slots.length; i++){
			slots[i].update();
		}
		
		if(slots[4].storedCard != null){
//			for(int i = 0; i < slots.length - 1; i++){
////				System.out.println(slots[i].storedCard);
//				slots[i].storedCard = slots[i+1].storedCard;
////				Slot temp = slots[i+1];
//			}
			
			slots[0].storedCard = slots[1].storedCard;
			slots[1].storedCard = slots[2].storedCard;
			slots[2].storedCard = slots[3].storedCard;
			slots[3].storedCard = slots[4].storedCard;
			slots[4].storedCard = null;
			
//			for(int i = 0; i < slots.length; i++){
//				System.out.println(slots[i].storedCard);
//			}
		}
//		int temp = 0;
//		for(int i = slots.length - 1; i >=0; i--){
//			if(slots[i].storedCard == null)
//				continue;
//			
//			if(slots[i].storedCard.getColor() == slots[i-1].storedCard.getColor() && slots[i].storedCard.getType() == slots[i-1].storedCard.getType()){
//				)
//			}
//			
//			if(temp == 1 && slots[i].storedCard.getColor() != slots[i-1].storedCard.getColor() && slots[i].storedCard.getType() != slots[i-1].storedCard.getType()){
//				inRow = temp;
//				break;
//			}
//		}
//		
		if(Hand.handSize==5){
			for(Slot s : slots){
				if(s.storedCard == null)
					break;
				s.storedCard.setMoveable(false);
			}
		} else { 
			for(int i = slots.length - 1; i >= 0; i--){
				if(slots[i].storedCard != null){
					slots[i].storedCard.setMoveable(true);
					break;
				}
			}
		}
		
		if(inRow == 2){
			mark:
			for(int i = slots.length - 1; i >= 0; i--){
				if(slots[i].storedCard != null){
					ArrayList<Card> deck = game.getGameState().getDeck().getCards();
					for(Card c : deck){
						if(c.getType() == Card.CardTypes.DOOR && c.getColor() == slots[i].storedCard.getColor()){
							deck.remove(c);
							game.getGameState().getLimbo().addCard(c);
							break mark;
						}
					}
				}
			}
			inRow = -1;
		}
	}

	public void render(Graphics g) {
		for(int i = 0; i < slots.length; i++){
			slots[i].render(g);
		}
//		g.setColor(Color.red);
		g.drawRect(x, y, width, height);
		g.setFont(new Font("myFont", Font.PLAIN, 18));
		g.drawString("In Row: " + String.valueOf(inRow + 1), x + 150, y - 20);
	}
	
	public void addCard(Card c){
		for(int i = 0; i < slots.length; i++){
			if(i >= 1){
				if(slots[i].storedCard == null && slots[i - 1].storedCard != null && slots[i - 1].storedCard.getSymbol() != c.getSymbol()){
					if(slots[i - 1].storedCard.getColor() == c.getColor()){
						PlayArea.inRow++;
					}
					else{
						PlayArea.inRow = 0;
					}
					slots[i - 1].storedCard.setMoveable(false);
					slots[i].addCard(c);
					System.out.println("Card Added");
					break;
				}
//				else if(i == slots.length - 1){
////					x = (int) c.getInSlot().getX();
////					y = (int) c.getInSlot().getY();
//					gameState.getHand();
//					System.out.println("Back To hand");
////					Hand.handSize++;
//					break;
//				}
			}
			else if(slots[i].storedCard == null){
				slots[i].addCard(c);
				System.out.println("Card Added");
//				gameState.getCardsOutOfDeck();
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
