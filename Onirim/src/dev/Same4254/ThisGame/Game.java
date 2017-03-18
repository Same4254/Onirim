package dev.Same4254.ThisGame;

import java.awt.Graphics;

import javax.swing.JPanel;

import dev.Same4254.ThisGame.Entities.Prophecy;
import dev.Same4254.ThisGame.Input.KeyManager;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.Sound.Music;
import dev.Same4254.ThisGame.States.GameState;
import dev.Same4254.ThisGame.States.MenuState;
import dev.Same4254.ThisGame.States.State;
import dev.Same4254.ThisGame.dis.Display;
import dev.Same4254.ThisGame.gfx.Assets;

public class Game extends JPanel{
	private static final long serialVersionUID = 1L;

	private Display display;
	
//	private BufferStrategy bs;
	private Graphics g;
	
//	private Thread thread;
	
//	public static boolean running;
	
	private String title;
	private int width;
	private int height;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//States
	private GameState gameState;
	private MenuState menuState;
	
	private Music music;
	private Prophecy prophecy;
	
	public Game(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
//		music = new Music(Assets.musicPaths[0]);
		
		keyManager = new KeyManager(this);
		mouseManager = new MouseManager(this);
		
		init();
	}

	public void init(){
		display = new Display(this, title, width, height);
		addMouseListener(mouseManager);
		addMouseMotionListener(mouseManager);
		Assets.init();
		
		gameState = new GameState(this);
		menuState = new MenuState(this);
		State.setCurrentState(gameState);
		
		
		
		update();
//		update();
//		update();
	}
	
	public void update(){
		width = display.getFrame().getWidth();
		height = display.getFrame().getHeight();
		
		keyManager.update();
//		System.out.println(keyManager.space);
//		
//		if(keyManager.space){
//			System.out.println("fff");
//		
//		}
		
		if(State.getCurrentState() != null)
			State.getCurrentState().update();
		repaint();
//		render();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(State.getCurrentState() != null)
			State.getCurrentState().render(g);
	}
	
//	private void render(){
//		bs = display.getCanvas().getBufferStrategy();
//		if(bs == null){
//			display.getCanvas().createBufferStrategy(2);
//			return;
//		}
//		g = bs.getDrawGraphics();
//		
//		g.clearRect(0, 0, width, height);
//		
//		//draw here
//		
//		if(State.getCurrentState() != null)
//			State.getCurrentState().render(g);
//		
//		//end draw
//		bs.show();
//		g.dispose();
//	}
	
//	public void run(){
//		init();
//		
//		int fps = 60;
//		double timePerTick =  1000000000 / fps;
//		double delta = 0;
//		long now;
//		long lastTime = System.nanoTime();
//		long timer = 0;
//		int ticks = 0;
//		boolean b2 = true;
//		
//		while(b2)
//			if(running){
//				now = System.nanoTime();
//				delta += (now - lastTime) / timePerTick;
//				timer += now - lastTime;
//				lastTime = now;
//				
//				if(delta >= 1){
//					update();
//					render();
//					ticks++;
//					delta--;
//				}
//				if(timer >= 1000000000){
//	//				System.out.println("Ticks and Frames: " + ticks);
//	//				display.getFrame().setTitle(display.getFrame().getTitle().substring(0, display.getFrame().getTitle().length() - 3) + ticks);
//					ticks = 0;
//					timer = 0;
//				}
//				
//			}
//		stop();
//	}
//	
//	public synchronized void start(){
//		if(running)
//			return;
//		
//		running = true;
//		thread = new Thread(this);
//		thread.start();
//	}
//	
//    public synchronized void stop(){
//    	if(!running)
//    		return;
//    	
//    	running = false;
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

    public void endMenuState(){
    	State.setCurrentState(gameState);
    }
    
	public GameState getGameState() {
		return gameState;
	}

	public Prophecy getProphecy() {
		return prophecy;
	}

	public KeyManager getKeyManager(){return keyManager;}
	public MouseManager getMouseManager(){return mouseManager;}
	public Display getDisplay(){return display;}
}
