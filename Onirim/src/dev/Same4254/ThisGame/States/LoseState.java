package dev.Same4254.ThisGame.States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;

public class LoseState extends State{

	private Font myFont;
	
	public LoseState(Game game) {
		super(game);
		
		myFont = new Font("MyFont", Font.PLAIN, 24);
	}

	public void update() {

	}

	public void render(Graphics g) {
		g.setFont(myFont);
		g.setColor(Color.BLACK);
		g.drawString("YOU LOSE :P           git gud scrub", 5, 50);
	}

}
