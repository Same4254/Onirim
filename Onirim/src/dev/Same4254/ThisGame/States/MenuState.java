package dev.Same4254.ThisGame.States;

import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.gfx.Assets;

public class MenuState extends State {

	private Game game;
	
	public MenuState(Game game){
		super(game);
		
		this.game = game;
	}
	
	public void update(){
		
	}
	
	public void render(Graphics g){
		g.drawImage(Assets.menuImage, 0, 0, game.getField().getWidth(), game.getField().getHeight(), null);
	}
}
