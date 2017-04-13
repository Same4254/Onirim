package dev.Same4254.ThisGame.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.Same4254.ThisGame.Game;

public class SpellBook extends Entity{

	private Rectangle paradoxProBox, doorSwitchBox, nightmareDestroyBox;
	
	private Game game;
	
	public SpellBook(Game game, int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.game = game;
		
		paradoxProBox = new Rectangle(x, y, width, height/3);
		doorSwitchBox = new Rectangle(x, y + (height/3), width, height/3);
		nightmareDestroyBox = new Rectangle(x, y + ((height/3) * 2), width, height/3);
	}

	public void update() {
		
	}

	public void render(Graphics g) {
//		g.drawImage(Assets.spellBook, x, y, width, height, null);
		g.setColor(Color.RED);
		g.fillRect((int) paradoxProBox.getX(), (int) paradoxProBox.getY(), (int) paradoxProBox.getWidth(), (int) paradoxProBox.getHeight());
		
		g.setColor(Color.BLACK);
		g.fillRect((int) doorSwitchBox.getX(), (int) doorSwitchBox.getY(), (int) doorSwitchBox.getWidth(), (int) doorSwitchBox.getHeight());
		
		g.setColor(Color.CYAN);
		g.fillRect((int) nightmareDestroyBox.getX(), (int) nightmareDestroyBox.getY(), (int) nightmareDestroyBox.getWidth(), (int) nightmareDestroyBox.getHeight());
	}

}
