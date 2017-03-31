package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;

public class Limbo extends Entity{

	private Slot[] slots;
	private Game game;
	public static Card currentDrawnCard;
	
	public Limbo(Game game, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		slots = new Slot[10];
		
		int vSpace = 12;
		int x1 = 14;
//		System.out.println(width);
//		System.out.println(height);
		for(int i = 0; i < slots.length; i++){
			if(i == 5){
				x1+=110;
				vSpace = 30;
			}
			slots[i] = new Slot(game, game.getGameState(), x + x1, y + vSpace, 100, 158);
			vSpace += 100;//49
			
		}
	}

	public void addCard(Card c){
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard == null){
				slots[i].addCard(c);
				currentDrawnCard = c;
				break;
			}
		}
	}
	/*
	 * Puts card into deck
	 * Clears Proph
	 * Shuffles deck
	 */
	public void shuffleToDeck(){
		for(Slot s : slots){
			if(s.storedCard == null){
				break;
			}
			s.storedCard.setInSlot(null);
			s.storedCard.inDeck = true;
			game.getGameState().getDeck().getCards().add(s.storedCard);
//			game.getGameState().getCardsOutOfDeck().remove(s.storedCard);
			s.storedCard = null;
			currentDrawnCard = null;
		}
//		System.out.println("SHUFFLE");
		game.getGameState().getDeck().shuffle();
	}
	
	public void update() {
		for(Slot s : slots){
			if(s.storedCard == null){
				break;
			}
			
			s.storedCard.update();
		}
//		System.out.println(currentDrawnCard);
//		if(Hand.handSize == 5){
//			shuffleToDeck();
//		}
	}
	
	public void render(Graphics g) {
//		g.setColor(Color.CYAN);	
		
		for(int i = 0; i < slots.length; i++){
//			g.setColor(Color.getHSBColor((float)i/slots.length, 1, 1));
//			g.setColor(Color.getHSBColor((float)i/slots.length, 1, 1));
			slots[i].render(g);
		}
	}

	public Slot[] getSlots() {
		return slots;
	}
}
