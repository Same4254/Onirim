package dev.Same4254.ThisGame.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.Same4254.ThisGame.Game;

public class KeyManager implements KeyListener{

	public boolean[] keys;
	public static boolean up, down, left, right, space, upArrow, downArrow, leftArrow, rightArrow, enter, backspace;
	private Game game;
	
	public KeyManager(Game game){
		keys = new boolean[256];
		this.game = game;
	}
	
	public void update(){
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
		
		upArrow = keys[KeyEvent.VK_UP];
		downArrow = keys[KeyEvent.VK_DOWN];
		leftArrow = keys[KeyEvent.VK_LEFT];
		rightArrow = keys[KeyEvent.VK_RIGHT];
		
		backspace = keys[KeyEvent.VK_BACK_SPACE];
		enter = keys[KeyEvent.VK_ENTER];
		
		if(enter)
			System.out.println("ENTER");
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		update();
		game.update();
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		System.out.println("released");
		update();
		game.update();
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
