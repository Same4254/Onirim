package dev.Same4254.ThisGame.Entities;

import java.awt.Graphics;

public abstract class Entity {

	protected int x, y;
	protected int width, height;
	
	public Entity(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Entity(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public float getX(){return x;}
	public void setX(int x){this.x = x;}
	public float getY(){return y;}
	public void setY(int y){this.y = y;}
	public int getWidth(){return width;}
	public void setWidth(int width){this.width = width;}
	public int getHeight(){return height;}
	public void setHeight(int height){this.height = height;}

	public abstract void update();
	public abstract void render(Graphics g);
}
