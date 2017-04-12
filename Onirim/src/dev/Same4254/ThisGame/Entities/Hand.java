package dev.Same4254.ThisGame.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardSymbols;
import dev.Same4254.ThisGame.Entities.Card.CardTypes;
import dev.Same4254.ThisGame.States.GameState;

public class Hand extends Entity{
	private Slot[] slots;
	public static int handSize;
	private boolean checked;
	private Rectangle hitBox;
	private Game game;
	private GameState gameState;
	private Deck deck;
	
	public Hand(Game game, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		this.gameState = game.getGameState();
		
//		System.out.println(this.game);
		
		hitBox = new Rectangle(x, y, width, height);
		
		slots = new Slot[5];
		
		int x1 = 19;
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, gameState, x + x1, y + (height-158) / 2, 100, 158);
			x1+=110;
		}
	}

	public void update() {
		int temp = 0;
		checked = false;
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard != null){
				if(!game.isFirstTurn())
					slots[i].update();
				temp++;
			}
		}
		
		handSize = temp; 
		for(int i = 0; i < slots.length; i++)
			if(slots[i].storedCard != null)
				slots[i].storedCard.setMoveable(handSize==5);
		
		if(handSize == 5){
//			game.getGameState().getLimbo().shuffleToDeck();
			game.setFirstTurn(false);
		}
		
		checkForDoorToKeyMatch();
	}

	public void checkForDoorToKeyMatch(){
		if(Limbo.currentDrawnCard != null && Limbo.currentDrawnCard.getType() == CardTypes.DOOR && !game.isFirstTurn()){
//			System.out.println("MADDEE ITTTT");
//			System.out.println(Limbo.currentDrawnCard);
			for(int i = 0; i < slots.length; i++){
				if(slots[i].storedCard != null){
					if(game.isLostFound()){
						if(slots[i].storedCard.getSymbol() == CardSymbols.KEY && slots[i].storedCard.getColor() == Limbo.currentDrawnCard.getColor() && game.getGameState().getDoorsCompleted().getOrder().get(0) == Limbo.currentDrawnCard.getColor()){
							game.setGetByKey(true);
							game.getCompleteDoor().setEnabled(true);
							break;
						}
					}
					else{
						if(slots[i].storedCard.getSymbol() == CardSymbols.KEY && slots[i].storedCard.getColor() == Limbo.currentDrawnCard.getColor()){
	//						System.out.println("MREH");
							game.setGetByKey(true);
							game.getCompleteDoor().setEnabled(true);
							break;
						}
						
						else{
							game.getCompleteDoor().setEnabled(false);
						}
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		Slot tempSlot = null;
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard!=null && slots[i].storedCard.isSelected()){
				tempSlot = slots[i];
				continue;
			}
			slots[i].render(g);
		}
		if(tempSlot!= null)
			tempSlot.render(g);
//		g.setColor(Color.BLACK);
//		g.drawRect(x, y, width, height);
	}

	public Slot[] getSlots() {
		return slots;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public void clear(){
		for(Slot s : slots){
			if(s.storedCard == null)
				continue;
			game.getGameState().getDiscard().addCardWithoutCheck(s.storedCard);
		}
	}
	
	public void refill(){
		game.getGameState().getProphecy().clearAllProphecy();
		ArrayList<Card> tempDeck = game.getGameState().getDeck().getCards();
		Collections.reverse(tempDeck);
		
		mark:
		for(int i = 0; i < tempDeck.size(); i++){
			if(tempDeck.get(i).getType() == CardTypes.LOCATION){
				for(int k = 0; k < slots.length; k++){
					if(slots[k].storedCard == null){
						slots[k].addCard(tempDeck.remove(i));
						i--;
						break;
					}
					else if(k == slots.length-1 && slots[k].storedCard != null){
						break mark;
					}
				}
			}
			else if(i == tempDeck.size()-1 && tempDeck.get(i).getType() != CardTypes.LOCATION){
				game.lose();
			}
		}
		
//		for(int i = 0; i < slots.length; i++){
//			if(tempDeck.get(0).getType() == CardTypes.LOCATION){
//				Card toRemove = tempDeck.remove(0);
//				addCard(toRemove);
//				game.getGameState().getDeck().getCards().remove(toRemove);
//				
//			}
//			else{
//				game.getGameState().getLimbo().addCard(temp.remove(0));
//				i--;
//			}
//		}
		Collections.reverse(tempDeck);
	}
	
	public void addCard(Card c){
		if(game.getCompleteDoor().isEnabled()){
			game.getCompleteDoor().setEnabled(false);
		}
		
		for(int i = slots.length - 1; i >= 0; i--){
			if(slots[i].storedCard == null){
				for(int k = 0; k < game.getGameState().getPlayArea().getSlots().length; k++){
					if(c.getInSlot() == game.getGameState().getPlayArea().getSlots()[i]){
						if(PlayArea.inRow > 0)
							PlayArea.inRow--;
					}
				}
				slots[i].addCard(c);
				c.setInPlayArea(false);
				c.setInProphecy(false);
			}
		}
	}
}
