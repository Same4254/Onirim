package dev.Same4254.ThisGame.States;

import java.awt.Font;
import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.gfx.Assets;

public class LoseState extends State{

	private Game game;
	
	public LoseState(Game game) {
		super(game);
		
		this.game = game;
	}

	public void update() {

	}

	public void render(Graphics g) {
		g.drawImage(Assets.wood, 0, 0, game.getDisplay().getWidth(), game.getDisplay().getHeight(), null);
		g.drawImage(Assets.lostText, 0,10, 1000, 500, null);
	}

}
