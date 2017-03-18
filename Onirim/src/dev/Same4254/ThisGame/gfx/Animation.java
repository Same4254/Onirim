package dev.Same4254.ThisGame.gfx;

import java.awt.image.BufferedImage;

public class Animation {
	private int speed, index;
	private BufferedImage[] frames;
	private long lastTime, timer;
	
	public Animation(Animation other){
		this.speed = other.speed;
		this.frames = other.frames;
		index = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public Animation(int speed, BufferedImage[] frames){
		this.speed = speed;
		this.frames = frames;
		index = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public Animation(int speed, BufferedImage frame){
		this.speed = speed;
		this.frames = new BufferedImage[1];
		frames[0] = frame;
		index = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void update(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed){
			index++;
			timer = 0;
			if(index >= frames.length){
				index = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}

	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
	}
}
