package dev.Same4254.ThisGame.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardSymbols;
import dev.Same4254.ThisGame.Input.KeyManager;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.gfx.Assets;

public class SpellBook extends Entity{

	private Rectangle paradoxProBox, doorSwitchBox, nightmareDestroyBox;
	
	private Game game;
	
	private boolean hardDifficulty;
	
	private int[] requirements; 
	
	public SpellBook(Game game, boolean hardDifficulty, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		this.hardDifficulty = hardDifficulty;
		
		requirements = new int[3];
		
		paradoxProBox = new Rectangle(x, y, width, height/3);
		doorSwitchBox = new Rectangle(x, y + (height/3), width, height/3);
		nightmareDestroyBox = new Rectangle(x, y + ((height/3) * 2), width, height/3);
		
		if(!hardDifficulty){
			requirements[0] = 5;
			requirements[1] = 7;
			requirements[2] = 10;
		}
		else if(hardDifficulty){
			requirements[0] = 6;
			requirements[1] = 9;
			requirements[2] = 12;
		}
	}

	public void update() {
		if(MouseManager.justEntered && paradoxProBox.contains(MouseManager.mouseX, MouseManager.mouseY)){
			triggerParadox();
		}
		
		else if(MouseManager.justEntered && doorSwitchBox.contains(MouseManager.mouseX, MouseManager.mouseY)){
			switchDoors();
		}
		
		else if(MouseManager.justEntered && nightmareDestroyBox.contains(MouseManager.mouseX, MouseManager.mouseY)){
			destroyNightmare();
		}
	}

	public void triggerParadox(){
		if(Discard.size >= requirements[0]){
			Discard.size -= requirements[0];
			
			
		}
	}
	
	public void switchDoors(){
		if(Discard.size >= requirements[1]){
			Discard.size -= requirements[1];
			
			game.getGameState().setComponents(false);
			DoorsCompleted.enabled = true;
			game.getGameState().getDoorsCompleted().startSwitching();
		}
	}
	
	public void destroyNightmare(){
		if(Discard.size >= requirements[2]){
			if(Limbo.currentDrawnCard.getSymbol() == CardSymbols.NIGHTMARE){
				game.getGameState().getDiscard().addCard(Limbo.currentDrawnCard);
				Discard.size -= requirements[2];
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.spellBook, x, y, width, height, null);
//		g.setColor(Color.RED);
//		g.fillRect((int) paradoxProBox.getX(), (int) paradoxProBox.getY(), (int) paradoxProBox.getWidth(), (int) paradoxProBox.getHeight());
//		
//		g.setColor(Color.BLACK);
//		g.fillRect((int) doorSwitchBox.getX(), (int) doorSwitchBox.getY(), (int) doorSwitchBox.getWidth(), (int) doorSwitchBox.getHeight());
//		
//		g.setColor(Color.CYAN);
//		g.fillRect((int) nightmareDestroyBox.getX(), (int) nightmareDestroyBox.getY(), (int) nightmareDestroyBox.getWidth(), (int) nightmareDestroyBox.getHeight());
	}

}
