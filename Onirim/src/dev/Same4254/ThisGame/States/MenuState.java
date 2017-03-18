package dev.Same4254.ThisGame.States;

import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Input.KeyManager;

public class MenuState extends State {

	public MenuState(Game game){
		super(game);
	}
	
	public void update(){
		if(game.getKeyManager().enter){
			game.endMenuState();
		}
	}
	public void render(Graphics g){
		g.drawString("This is Onirim! Press enter to start the game, or backspace to load the previous game. At any point you can save by pressing ctrl + s", 5, 50);
	}
}
