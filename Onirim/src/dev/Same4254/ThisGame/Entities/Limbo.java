package dev.Same4254.ThisGame.Entities;

import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;

public class Limbo extends Entity{

	private Slot[] slots;
	private Game game;
	
	public Limbo(Game game, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		slots = new Slot[10];
		
		int vSpace = 0;
//		System.out.println(width);
//		System.out.println(height);
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, game.getGameState(), x, y + vSpace, width, 158);
			vSpace += 60;
			if(y+vSpace >= 600 - (slots[i].height * 1.5)){
				x+= width;
				vSpace = 0;
			}
		}
	}

	public void addCard(Card c){
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard == null){
				slots[i].addCard(c);
				break;
			}
		}
	}
	
	public void shuffleToDeck(){
		for(Slot s : slots){
			if(s.storedCard == null){
				return;
			}
			s.storedCard.setInSlot(null);
			s.storedCard.inDeck = true;
			game.getGameState().getDeck().getCards().add(s.storedCard);
			game.getGameState().getCardsOutOfDeck().remove(s.storedCard);
			s.storedCard = null;
		}
		game.getGameState().getDeck().shuffle();
	}
	
	public void update() {
		if(Hand.handSize == 5){
			shuffleToDeck();
		}
	}

	public void render(Graphics g) {
//		g.setColor(Color.CYAN);
		g.drawRect(x, y, width*2, height);
		
		for(int i = 0; i < slots.length; i++){
//			g.setColor(Color.getHSBColor((float)i/slots.length, 1, 1));
			slots[i].render(g);
		}
	}

	public Slot[] getSlots() {
		return slots;
	}
}
