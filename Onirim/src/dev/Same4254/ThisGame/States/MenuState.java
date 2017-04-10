package dev.Same4254.ThisGame.States;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.glass.events.KeyEvent;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Input.KeyManager;

public class MenuState extends State {

	private Game game;
	
	public MenuState(Game game){
		super(game);
		
		this.game = game;
	}
	
	public void update(){
		if(KeyManager.enter){
			game.getCompleteDoor().setVisible(true);
			game.getDeckMenu().getDis().getFrame().setVisible(true);
			
			KeyManager.enter = false;
			game.getKeyManager().keys[KeyEvent.VK_ENTER] = false;
			
			game.endMenuState();
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.BLACK);
		g.drawString("This is Onirim!", 5, 50);
	}
}
