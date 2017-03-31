package dev.Same4254.ThisGame.States;

import java.awt.Color;
import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Input.KeyManager;

public class MenuState extends State {

	public MenuState(Game game){
		super(game);
	}
	
	public void update(){
		if(game.getKeyManager().enter){
			game.getCompleteDoor().setVisible(true);
			game.getDiscarded().getDis().getFrame().setVisible(true);
			
			game.endMenuState();
		}
	}
	public void render(Graphics g){
		g.setColor(Color.BLACK);
		g.drawString("This is Onirim!", 5, 50);
	}
}
