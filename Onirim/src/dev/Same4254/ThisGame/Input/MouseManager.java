package dev.Same4254.ThisGame.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Prophecy;

public class MouseManager implements MouseListener, MouseMotionListener{

	public static boolean leftPressed, rightPressed, middlePressed;
	public static boolean mouseDragged;
	public static int mouseX, mouseY;
	
	public static boolean justEntered, justReleased;

	private Game game;
	
	public MouseManager(Game game){
		this.game = game;
	}
	
	public void mouseDragged(MouseEvent e) {
		if(!(e.getX() < game.getWidthOffSet() || e.getX() > game.getWidth() - game.getWidthOffSet())){
			float percent = ((float)e.getX() - game.getWidthOffSet()) / (game.getWidth()-game.getWidthOffSet()*2);
			mouseX = (int) (percent * game.getField().getWidth());
		}
		
		if(!(e.getY() < game.getHeightOffSet() || e.getY() > game.getHeight() - game.getHeightOffSet())){
			float percent = ((float)e.getY() - game.getHeightOffSet()) / (game.getHeight()-game.getHeightOffSet()*2);
			mouseY = (int) (percent * game.getField().getHeight());
		}
		
//		System.out.println("Mouse X: " + mouseX + ", Mouse Y: " + mouseY);
		
		mouseDragged = true;
		justEntered = false;
		game.update();
	}

	public void mouseMoved(MouseEvent e) {
//		
//		System.out.println("X: " + mouseX + " Y: " + mouseY);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!(e.getX() < game.getWidthOffSet() || e.getX() > game.getWidth() - game.getWidthOffSet())){
			float percent = ((float)e.getX() - game.getWidthOffSet()) / (game.getWidth()-game.getWidthOffSet()*2);
			mouseX = (int) (percent * game.getField().getWidth());
		}
		
		if(!(e.getY() < game.getHeightOffSet() || e.getY() > game.getHeight() - game.getHeightOffSet())){
			float percent = ((float)e.getY() - game.getHeightOffSet()) / (game.getHeight()-game.getHeightOffSet()*2);
			mouseY = (int) (percent * game.getField().getHeight());
		}
		
		if(e.getButton() == MouseEvent.BUTTON1){
			leftPressed = true;
			justEntered = true;
			justReleased = false;
		}
		
		if(e.getButton() == MouseEvent.BUTTON2){
			middlePressed = true;
		}
		
		if(e.getButton() == MouseEvent.BUTTON3){
//			System.out.println("RIGHT");
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
		middlePressed = false;
		
		if(e.getButton() == MouseEvent.BUTTON2){
			if(!Prophecy.prophosizing && game.getcurrentState() == game.getGameState())
				game.getGameState().getDeck().deckPressed();
		}
		else{
			justReleased = true;
		}
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
