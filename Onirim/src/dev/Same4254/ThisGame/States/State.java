package dev.Same4254.ThisGame.States;

import java.awt.Graphics;

import dev.Same4254.ThisGame.Game;

public abstract class State {
	protected Game game;
	
	private static State currentState = null;
	
	public static State getCurrentState(){return currentState;}
	public static void setCurrentState(State currentState){State.currentState = currentState;}

	public abstract void update();
	public abstract void render(Graphics g);
	
	public State(Game game){
		this.game = game;
	}
}
