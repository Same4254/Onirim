package dev.Same4254.ThisGame.States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;

public class WinState extends State{

	private Font myFont;
	
	public WinState(Game game) {
		super(game);
		
		myFont = new Font("MyFont", Font.PLAIN, 24);
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(myFont);
		g.drawString("YOU WIN! :)", 5, 50);
	}

}
