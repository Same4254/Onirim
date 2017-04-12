package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardColors;
import dev.Same4254.ThisGame.States.GameState;

public class DoorsCompleted extends Entity{

	private Slot[] slots;
	private Game game;
	private ArrayList<CardColors> order;
	private boolean lostFound;
	private ArrayList<CardColors> originalOrder;
	
	public DoorsCompleted(Game game, GameState gameState, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		lostFound = game.isLostFound();
		slots = new Slot[8];
		int x1 = 12;
		for(int i = 0; i < slots.length; i++){
			slots[i] = new Slot(game, gameState, x+x1, y+12, 100, 158);
			x1+=101;
		}
		
		if(lostFound){
			order = new ArrayList<>();
			
			ArrayList<CardColors> choices = new ArrayList<>();
			
			for(int i = 0; i < 2; i++){
				choices.add(CardColors.BLUE);
				choices.add(CardColors.GREEN);
				choices.add(CardColors.RED);
				choices.add(CardColors.TAN);
			}
			
			Random randy = new Random();
			for(int i = 0; i < 8; i++){
				int r = randy.nextInt(choices.size());
				order.add(choices.remove(r));
//				System.out.println(r);
			}
			
			originalOrder = new ArrayList<>(order);
			System.out.println(order);
		}
	}
	
	public boolean addDoor(Card c){
		if(lostFound){
			if(c.getColor() == order.get(0)){
				System.out.println("Removed");
				order.remove(0);
				game.getGameState().getDeck().getCards().remove(c);
				for(int i = 0; i < slots.length; i++){
					if(slots[i].storedCard == null){
						slots[i].addCard(c);
						c.clearAllDependencies();
						c.setCompleted(true);
						break;
					}
				}
			}
			else{
				return false;
			}
		}
		else{
			game.getGameState().getDeck().getCards().remove(c);
			for(int i = 0; i < slots.length; i++){
				if(slots[i].storedCard == null){
					slots[i].addCard(c);
					c.clearAllDependencies();
					c.setCompleted(true);
					break;
				}
			}
			return true;
		}
		return true;
	}

	public void update() {
		for(int i = 0; i < slots.length; i++){
			slots[i].update();
		}
	}

	public void render(Graphics g) {
		if(lostFound){
			for(int i = 0; i < slots.length; i++){
				Color c = null;
				
				if(originalOrder.get(i) == CardColors.BLUE){
					c = Color.BLUE;
				}
				else if(originalOrder.get(i) == CardColors.RED){
					c = Color.RED;
				}
				else if(originalOrder.get(i) == CardColors.GREEN){
					c = Color.GREEN;
				}
				else if(originalOrder.get(i) == CardColors.TAN){
					c = Color.YELLOW;
				}
				slots[i].renderWithCheck(g, c);
			}
		}
		else{
			for(int i = 0; i < slots.length; i++){
				slots[i].render(g);
			}
		}
//		g.setColor(Color.WHITE);
//		g.fillRect(x, y, width, height);
	}
	
	public ArrayList<CardColors> getOrder() {
		return order;
	}

	public Slot[] getSlots() {
		return slots;
	}
}
