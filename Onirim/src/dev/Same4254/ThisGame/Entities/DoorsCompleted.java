package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardColors;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.States.GameState;

public class DoorsCompleted extends Entity{

	private Slot[] slots;
	private Game game;
	private ArrayList<CardColors> order;
	private boolean lostFound;
	public static boolean enabled;
	private boolean switching;
	private int[] swingers;
	
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
		
		enabled = true;
		
		swingers = new int[2];
		swingers[0] = swingers[1] = -1;
		
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
			
			for(int i = 0; i < slots.length; i++){
				Color c = null;
				
				if(order.get(i) == CardColors.BLUE){
					c = Color.BLUE;
				}
				else if(order.get(i) == CardColors.RED){
					c = Color.RED;
				}
				else if(order.get(i) == CardColors.GREEN){
					c = Color.GREEN;
				}
				else if(order.get(i) == CardColors.TAN){
					c = Color.YELLOW;
				}
				slots[i].setColor(c);
			}
//			System.out.println(order);
		}
	}
	
	public boolean addDoor(Card c){
		if(lostFound){
			for(int i = 0; i < slots.length; i++){
				if(order.get(i) != null){
					if(c.getColor() == order.get(i)){
//						System.out.println("Removed");
						order.set(i, null);
						game.getGameState().getDeck().getCards().remove(c);
						for(int k = 0; k < slots.length; k++){
							if(slots[k].storedCard == null){
								slots[k].addCard(c);
								c.clearAllDependencies();
								c.setCompleted(true);
								break;
							}
						}
					}
					else{
						return false;
					}
					break;
				}
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
		if(enabled){
			for(int i = 0; i < slots.length; i++){
				slots[i].update();
			}
		}
		
		if(switching){
			for(int i = 0; i < slots.length; i++){
				if(MouseManager.justEntered && slots[i].getHitBox().contains(MouseManager.mouseX, MouseManager.mouseY) && slots[i].storedCard == null){
					if(slots[i].isHalf()){
						slots[i].setHalf(false);
						swingers[0] = -1;
					}
					else{
						for(int k = 0; k < 2; k++){
							if(swingers[k] == -1){
								swingers[k] = i;
								slots[i].setHalf(true);
								break;
							}
						}
						if(swingers[0] != -1 && swingers[1] != -1){
//							System.out.println("Got to the if");
							CardColors temp = order.get(swingers[0]);
							order.set(swingers[0], order.get(swingers[1]));
							order.set(swingers[1], temp);
							
							Color temp2 = slots[swingers[0]].getColor();
							slots[swingers[0]].setColor(slots[swingers[1]].getColor());
							slots[swingers[1]].setColor(temp2);
							
							slots[swingers[0]].setHalf(false);
							slots[swingers[1]].setHalf(false);
							
							swingers[0] = swingers[1] = -1;
							switching = false;
							game.getGameState().setComponents(true);
//							System.out.println(order);
							game.update();
						}
					}
				}
			}
		}
	}

	public void startSwitching(){
		switching = true;
	}
	
	public void render(Graphics g) {
		if(lostFound){
			for(int i = 0; i < slots.length; i++){
				slots[i].renderWithCheck(g);
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
