package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.States.GameState;

public class DoorsCompleted extends Entity{

	private Slot[] slots;
	
	public DoorsCompleted(Game game, GameState gameState, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		slots = new Slot[8];
		int x1 = 0;
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, gameState, x+x1, y, 100, 158);
			x1+=110;
		}
	}
	
	public void addDoor(Card c){
		/*
		 * check to see that the card is a door
		 */
		if(c.getType() != Card.CardTypes.DOOR){
			System.out.println("TRIED TO DISCARD A KEY FOR A NON DOOR!");
			return;
		}
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard == null){
				slots[i].addCard(c);
			}
		}
	}

	public void update() {
		for(int i = 0; i < slots.length; i++){
			slots[i].update();
		}
	}

	public void render(Graphics g) {
		g.drawLine(0, y+5, width, y+5);
		for(int i = 0; i < slots.length; i++){
			slots[i].render(g);
		}
//		g.setColor(Color.WHITE);
//		g.fillRect(x, y, width, height);
	}
	
}
