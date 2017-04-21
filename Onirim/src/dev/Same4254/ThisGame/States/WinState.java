package dev.Same4254.ThisGame.States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.gfx.Assets;

public class WinState extends State{

	private Font myFont;
	
	public WinState(Game game) {
		super(game);
		
		myFont = new Font("MyFont", Font.PLAIN, 24);
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		g.drawImage(Assets.wood, 0, 0, game.getDisplay().getWidth(), game.getDisplay().getHeight(), null);
		g.drawImage(Assets.winText, 0,10, 1000, 500, null);
	}

}
