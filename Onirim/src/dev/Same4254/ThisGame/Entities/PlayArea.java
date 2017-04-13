package dev.Same4254.ThisGame.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardColors;
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
		inRow = 0;
		
		int x1 = 19;
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, gameState, x + x1, y + (height-158) / 2, 100, 158);
			x1+=110;
		}
		
		hitBox = new Rectangle(x, y, width, height);
	}

	public void update() {
		Slot tempSlot = null;
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard != null && slots[i].storedCard.isSelected()){
				tempSlot = slots[i];
				continue;
			}
			slots[i].update();
		}
		
		if(tempSlot!= null)
			tempSlot.update();
		
		if(slots[4].storedCard != null){
			slots[0].storedCard = slots[1].storedCard;
			slots[1].storedCard = slots[2].storedCard;
			slots[2].storedCard = slots[3].storedCard;
			slots[3].storedCard = slots[4].storedCard;
			slots[4].storedCard = null;
//			
//			game.update();
//			game.update();
//			game.update();
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
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard != null && slots[i+1].storedCard != null){
				if(slots[i].storedCard.isUsed()){
					continue;
				}
				if((i==0 || slots[i-1].storedCard.isUsed()) && slots[i].storedCard.getColor() == slots[i+1].storedCard.getColor()){
					temp+=2;
				}
				else if(slots[i].storedCard.getColor() == slots[i+1].storedCard.getColor()){
					temp++;
				}
				else{
					temp = 1;
				}
			}
			
//			if(slots[i].storedCard != null){
//				if(i!=0 && slots[i-1].storedCard.getColor() != slots[i].storedCard.getColor() && slots[i-1].storedCard.isUsed()){
//					temp = 1;
//					break;
//				}
//				
//				if(i!=0 && slots[i-1].storedCard.isUsed()){
//					temp++;
//					break;
//				}
//				
//				if(i - 1 == 0 && slots[i-1].storedCard.getColor() == slots[i].storedCard.getColor()){
//					temp+=2;
//				}
//				
//				else if(i!=0 && slots[i-1].storedCard.getColor() == slots[i].storedCard.getColor()){
//					temp++;
//				}
//			}
			
			
			
//			if(i == 0  && slots[i].storedCard != null){
//				if(slots[i+1].storedCard != null && slots[i].storedCard.getColor() == slots[i+1].storedCard.getColor())
//					temp++;
//				else if(slots[i+1].storedCard == null){
//					temp++;
//				}
//			}
//			
//			else if(slots[i].storedCard != null && i != 0){
////				System.out.println("CHeck");
//				if(slots[i - 1].storedCard.getColor() == slots[i].storedCard.getColor() && temp != 3 && !slots[i].storedCard.isUsed()){
//					temp++;
////					System.out.println("ADD");
//				}
//				else if(slots[i].storedCard.isUsed()){
////					temp++;
//					break;
//				}
//				else{
//					temp++;
//					break;
//				}
//			}
		}
		
		if(temp % 3 != 0)
			inRow = temp % 3;
		else{
			inRow = temp;
		}
		
		if(inRow == 3){
			boolean match = true;
			
			if(game.isLostFound()){
				for(int i = slots.length - 1; i >= 0; i--){
					if(slots[i].storedCard != null){
//						System.out.println(game.getGameState().getDoorsCompleted().getOrder());
						if(slots[i].storedCard.getColor() != game.getGameState().getDoorsCompleted().getOrder().get(0)){
							System.out.println(game.getGameState().getDoorsCompleted().getOrder());
							System.out.println(game.getGameState().getDoorsCompleted().getOrder().get(0));
							match = false;
							System.out.println("Now False");
						}
						else{
							System.out.println(game.getGameState().getDoorsCompleted().getOrder().get(0));
							System.out.println("Still true");
						}
						break;
					}
				}
			}
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
			
			//checks for the current color that is trying to be matched
			CardColors color = null;
			for(int i = slots.length - 1; i >= 0; i--){
				if(slots[i].storedCard != null){
					color = slots[i].storedCard.getColor();
					break;
				}
			}
			
			//checks to see how many of that color has already been played (door)
			int tempNum = 0;
			if(color != null){
				for(Slot s : game.getGameState().getDoorsCompleted().getSlots()){
					if(s.storedCard != null && s.storedCard.getColor() == color){
						tempNum++;                                         
					}
				}
			}
			
			//if there are less than 2 of those doors enable the button
			if(tempNum < 2 && match){
				game.getCompleteDoor().setEnabled(true);
			}
			else
				game.getCompleteDoor().setEnabled(false);
			inRow = 0;
		}
		else{
			if(!game.isGetByKey())
				game.getCompleteDoor().setEnabled(false);
		}
		
		if(slots[1].storedCard == null && slots[0].storedCard != null && Hand.handSize < 5){
			slots[0].storedCard.setMoveable(true);
		}
	}

	/*
	 * The player is an idiot that needs help
	 * This is called when the player does something other than complete the door when they could have
	 */
	public void overRide(){
		for(int i = slots.length - 1; i >= 0; i--){
			if(slots[i].storedCard != null){
				slots[i].storedCard.setUsed(true);
				slots[i].storedCard.setMoveable(false);
				game.getCompleteDoor().setEnabled(false);
			}
		}
	}
	
	public void render(Graphics g) {
		Slot tempSlot = null;
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard != null && slots[i].storedCard.isSelected()){
				tempSlot = slots[i];
				continue;
			}
			slots[i].render(g);
		}
		
		if(tempSlot!= null)
			tempSlot.render(g);
//		g.setColor(Color.BLUE);
//		g.drawRect(x, y, width, height);
//		g.setFont(new Font("myFont", Font.PLAIN, 18));
//		g.drawString("In Row: " + String.valueOf(inRow), x + 150, y - 20);
	}
	
	public void addCard(Card c){
		for(int i = 0; i < slots.length; i++){
			if(i >= 1){
				if(slots[i].storedCard == null && slots[i - 1].storedCard != null && slots[i - 1].storedCard.getSymbol() != c.getSymbol()){
					slots[i - 1].storedCard.setMoveable(false);
					slots[i].addCard(c);
					c.setInPlayArea(true);
//					System.out.println("Card Added");
					break;
				}
			}
			else if(slots[i].storedCard == null){
				slots[i].addCard(c);
				c.setInPlayArea(true);
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
