package dev.Same4254.ThisGame.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dev.Same4254.ThisGame.Game;

public class MouseManager implements MouseListener, MouseMotionListener{

	public static boolean leftPressed, rightPressed;
	public static boolean mouseDragged;
	public static int mouseX, mouseY;
	
	public static boolean justEntered, justReleased;

	private Game game;
	
	public MouseManager(Game game){
		this.game = game;
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouseDragged = true;
		game.update();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			leftPressed = true;
			justEntered = true;
			justReleased = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			System.out.println("RIGHT");
			rightPressed = true;
			justEntered = true;
			justReleased = false;
		}
		game.update();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		leftPressed = false;
		rightPressed = false;
		mouseDragged = false;
		justEntered = false;
		justReleased = true;
		game.update();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
