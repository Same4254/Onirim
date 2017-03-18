package dev.Same4254.ThisGame.Entities;

import java.awt.Graphics;

public class DoorsCompleted extends Entity{

	private Slot[] slots;
	
	public DoorsCompleted(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		slots = new Slot[8];
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
		for(int i = 0; i < slots.length; i++){
			slots[i].render(g);
		}
	}
	
}
