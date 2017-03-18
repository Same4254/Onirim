package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.States.GameState;

public class Hand extends Entity{
	private Slot[] slots;
	public static int handSize;
	private boolean checked;
	private Rectangle hitBox;
	private Game game;
	private GameState gameState;
	
	public Hand(Game game, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		this.gameState = game.getGameState();
		
		hitBox = new Rectangle(x, y, width, height);
		
		slots = new Slot[5];
		
		int x1 = 10;
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
				slots[i].update();
				temp++;
			}
		}
		
		handSize = temp; 
		for(int i = 0; i < slots.length; i++)
			if(slots[i].storedCard != null)
				slots[i].storedCard.setMoveable(handSize==5);
	}

	public void render(Graphics g) {
		for(int i = 0; i < slots.length; i++){
			slots[i].render(g);
		}
		g.setColor(Color.RED);
		g.drawRect(x, y, width, height);
	}

	public Slot[] getSlots() {
		return slots;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public void addCard(Card c){
		for(int i = slots.length - 1; i >= 0; i--){
			if(slots[i].storedCard == null){
				for(int k = 0; k < game.getGameState().getPlayArea().getSlots().length; k++){
					if(c.getInSlot() == game.getGameState().getPlayArea().getSlots()[i]){
						if(PlayArea.inRow > 0)
							PlayArea.inRow--;
					}
				}
				slots[i].addCard(c);
			}
		}
	}
}
