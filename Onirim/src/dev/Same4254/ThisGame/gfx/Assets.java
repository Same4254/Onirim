package dev.Same4254.ThisGame.gfx;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

public class Assets {
	public static BufferedImage blueDoor, greenDoor, redDoor, tanDoor;

	public static BufferedImage greenCardKey, greenCardSun, greenCardMoon; 
	public static BufferedImage redCardKey, redCardSun, redCardMoon; 
	public static BufferedImage tanCardKey, tanCardSun, tanCardMoon; 
	public static BufferedImage blueCardKey, blueCardSun, blueCardMoon;
	
	public static BufferedImage blankDoor;
	
	public static BufferedImage nightmare, cardBack;
	
	public static BufferedImage logo;
	
	public static ImageIcon buttonActive, buttonDisabled, buttonClicked;
	
	public static BufferedImage boardCover, wood;
	
	public static String[] musicPaths = {new File("res/sound/8bitDungeonBoss.mp3").toURI().toString()};
	
	public static void init(){
		greenCardKey = ImageLoader.loadImage("res/textures/Green Card Key.png");
		greenCardSun = ImageLoader.loadImage("res/textures/Green Card Sun.png");
		greenCardMoon = ImageLoader.loadImage("res/textures/Green Card Moon.png");
		
		redCardKey = ImageLoader.loadImage("res/textures/Red Card Key.png");
		redCardSun = ImageLoader.loadImage("res/textures/Red Card Sun.png");
		redCardMoon = ImageLoader.loadImage("res/textures/Red Card Moon.png");
		
		tanCardKey = ImageLoader.loadImage("res/textures/Tan Card Key.png");
		tanCardSun = ImageLoader.loadImage("res/textures/Tan Card Sun.png");
		tanCardMoon = ImageLoader.loadImage("res/textures/Tan Card Moon.png");
		
		blueCardKey = ImageLoader.loadImage("res/textures/Blue Card Key.png");
		blueCardSun = ImageLoader.loadImage("res/textures/Blue Card Sun.png");
		blueCardMoon = ImageLoader.loadImage("res/textures/Blue Card Moon.png");
		
		greenDoor = ImageLoader.loadImage("res/textures/Green Door.png");
		redDoor = ImageLoader.loadImage("res/textures/Red Door.png");
		tanDoor = ImageLoader.loadImage("res/textures/Tan Door.png");
		blueDoor = ImageLoader.loadImage("res/textures/Blue Door.png");
		
		nightmare = ImageLoader.loadImage("res/textures/Nightmare Card.png");
		cardBack = ImageLoader.loadImage("res/textures/Card Back.png");
		blankDoor = ImageLoader.loadImage("res/textures/Door Blank.png");
		
		wood = ImageLoader.loadImage("res/textures/Wood.png");
		boardCover = ImageLoader.loadImage("res/textures/Board Cover.png");
		
		buttonActive = ImageLoader.loadIcon("res/textures/Button Active.png");
		buttonClicked = ImageLoader.loadIcon("res/textures/Button Clicked.png");
		buttonDisabled = ImageLoader.loadIcon("res/textures/Button Disabled.png");
		
		logo = ImageLoader.loadImage("res/textures/logo.jpg");
	}
}
