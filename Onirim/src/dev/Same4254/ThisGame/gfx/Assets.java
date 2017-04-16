package dev.Same4254.ThisGame.gfx;

import java.awt.Graphics;
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
	
	public static BufferedImage spellBook;
	
	public static BufferedImage nightmare, cardBack;
	
	public static BufferedImage logo;
	
	public static BufferedImage buttonActive, buttonDisabled, buttonClicked;
	
	public static BufferedImage boardCover, woodWithCover, wood;
	
	public static BufferedImage lostText, returnButtonArmed, returnButton, winText;
	
	public static BufferedImage menuImage;
	
	public static String[] musicPaths = {new File("res/sound/bounce.wav").toURI().toString(),
				new File("res/sound/Lunar Outpost.mp3").toURI().toString(), 
				new File("res/sound/Thunder Dreams.mp3").toURI().toString(),
				new File("res/sound/Arabesque.mp3").toURI().toString(),
				new File("res/sound/Nightmare.mp3").toURI().toString(),
				new File("res/sound/Terror.mp3").toURI().toString()};
	
	public static void init(){
		greenCardKey = ImageLoader.loadImage("/textures/Green Card Key.png");
		greenCardSun = ImageLoader.loadImage("/textures/Green Card Sun.png");
		greenCardMoon = ImageLoader.loadImage("/textures/Green Card Moon.png");
		
		redCardKey = ImageLoader.loadImage("/textures/Red Card Key.png");
		redCardSun = ImageLoader.loadImage("/textures/Red Card Sun.png");
		redCardMoon = ImageLoader.loadImage("/textures/Red Card Moon.png");
		
		tanCardKey = ImageLoader.loadImage("/textures/Tan Card Key.png");
		tanCardSun = ImageLoader.loadImage("/textures/Tan Card Sun.png");
		tanCardMoon = ImageLoader.loadImage("/textures/Tan Card Moon.png");
		
		blueCardKey = ImageLoader.loadImage("/textures/Blue Card Key.png");
		blueCardSun = ImageLoader.loadImage("/textures/Blue Card Sun.png");
		blueCardMoon = ImageLoader.loadImage("/textures/Blue Card Moon.png");
		
		greenDoor = ImageLoader.loadImage("/textures/Green Door.png");
		redDoor = ImageLoader.loadImage("/textures/Red Door.png");
		tanDoor = ImageLoader.loadImage("/textures/Tan Door.png");
		blueDoor = ImageLoader.loadImage("/textures/Blue Door.png");
		
		nightmare = ImageLoader.loadImage("/textures/Nightmare Card.png");
		cardBack = ImageLoader.loadImage("/textures/Card Back.png");
		blankDoor = ImageLoader.loadImage("/textures/Door Blank.png");
		
		wood = ImageLoader.loadImage("/textures/Wood.png");
		woodWithCover = ImageLoader.loadImage("/textures/Wood.png");
		boardCover = ImageLoader.loadImage("/textures/Board Cover.png");
		
		buttonActive = ImageLoader.loadImage("/textures/Button Active.png");
		buttonClicked = ImageLoader.loadImage("/textures/Button Clicked.png");
		buttonDisabled = ImageLoader.loadImage("/textures/Button Disabled.png");
		
		logo = ImageLoader.loadImage("/textures/Icon.png");
		
		spellBook = ImageLoader.loadImage("/textures/SpellBook.png");
		
		lostText = ImageLoader.loadImage("/textures/Lost Text.png");
		returnButtonArmed = ImageLoader.loadImage("/textures/Return-Button Armed.png");
		returnButton = ImageLoader.loadImage("/textures/Return-Button.png");
		winText = ImageLoader.loadImage("/textures/Win Text.png");
				
		menuImage = ImageLoader.loadImage("/textures/menuImage.jpg");
		
		Graphics g = woodWithCover.getGraphics();
		g.drawImage(boardCover, 0, 0, woodWithCover.getWidth(), woodWithCover.getHeight(), null);
		g.dispose();
	}
}
