package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.States.GameState;

public class Prophecy extends Entity{
	
	private Game game;
	private GameState gameState;
	
	private Slot[] slots;
	
	private Point wordLoc;
	
	public static boolean prophosizing = false;
	public static boolean prophecyFull = false;
	
	public Prophecy(Game game, GameState gameState, int x, int y, int width, int height) {
		super(x,y,width,height);
		
		this.game = game;
		this.gameState = gameState;
		
		slots = new Slot[5];
		
		int x1 = 110;
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, gameState, x1 + x, y, 100, 158);
			x1+=110;
		}
		
		wordLoc = new Point(x + 2, y + (height/2));
		
		/**
		 * TEMP CODE
		 */
//		restockProphecy();
	}
	
//	public void end(){
//		ArrayList<Card> deckCards = gameState.getDeck().getCards();
//		System.out.println(deckCards.size());
//		
////		int count = 0;
//		for(int i = 0; i < slots.length; i++){
//			if(slots[i].storedCard != null){
//				deckCards.remove(deckCards.size()-1);
//			}
//		}
//		
////		for(int i = deckCards.size() - 1; i >= deckCards.size() - 5; i--){
////			
////		}
//		for(int i = slots.length-1; i >= 0; i--){
//			if(slots[i].storedCard != null)
//				deckCards.add(new Card(game, gameState, slots[i].storedCard, false));
//		}
//	}
	
	public void restockProphecy(){
		ArrayList<Card> deckCards = gameState.getDeck().getCards();
		for(int i = 0; i < 5; i++){
//			slots[i].getACopyCard(gameState.getDeck().getCards().get(i), true);
//			slots[i].storedCard.setMoveable(false);
//			slots[i].storedCard.getHitBox().setLocation(slots[i].x, slots[i].y);
			slots[i].addCard(deckCards.remove(deckCards.size()-1));
			slots[i].storedCard.setInProphecy(true);
			slots[i].storedCard.setMoveable(false);
		}
	}
	
	public void clearAllProphecy(){
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard != null){
				slots[i].storedCard = null;
			}
		}
	}
	
	public void update() {
		boolean temp = true;
		int temp2 = 0;
		for(int i = 0; i < slots.length; i++){
			slots[i].update();
			if(slots[i].storedCard == null){
				temp = false;
			}
			else{
				temp2++;
			}
		}
		if(temp2 == 5){
			prophosizing = true;
		}
		else{
			prophosizing = false;
		}
		prophecyFull = temp;
	}
	
	public void render(Graphics g) {
//		g.fillRect(x, y, width, height);
		Card toRender = null;
		for(int i = 0; i < slots.length; i++){
//			g.setColor(Color.getHSBColor((float)i/slots.length, 1, 1));
			if(slots[i].storedCard != null && slots[i].storedCard.isSelected()){
				toRender = slots[i].storedCard;
				continue;
			}
			slots[i].render(g);
		}
		
		if(toRender != null){
			toRender.render(g);
			toRender = null;
		}
		
//		g.fillRect(x, y, width, height);
//		g.drawString("Top", (int)wordLoc.getX(), (int)wordLoc.getY());
	}

	public Slot[] getSlots() {
		return slots;
	}
}
