package dev.Same4254.ThisGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import dev.Same4254.ThisGame.Entities.Card;
import dev.Same4254.ThisGame.Entities.DoorsCompleted;
import dev.Same4254.ThisGame.Entities.PlayArea;
import dev.Same4254.ThisGame.Entities.Prophecy;
import dev.Same4254.ThisGame.Entities.Slot;
import dev.Same4254.ThisGame.Input.KeyManager;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.Sound.Music;
import dev.Same4254.ThisGame.States.GameState;
import dev.Same4254.ThisGame.States.MenuState;
import dev.Same4254.ThisGame.States.State;
import dev.Same4254.ThisGame.dis.Display;
import dev.Same4254.ThisGame.gfx.Assets;

public class Game extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;

	private Display display;
	
	private Graphics g;
	
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
	
	private JButton completeDoor;
	
	/*
	 * This is the image that the paint component will draw to then will get scaled
	 */
	private BufferedImage field;
	
	private float heightOffSet, widthOffSet;
	
	/**
	 * TODO LIST
	 * 
	 * Nightmare
	 *	 Send a Played Door to Limbo to discard Nightmare
	 *	 Draw 5 cards (locations go to discard, dreams & doors go to limbo)
	 * Helper Menu
	 * 
	 * Clean up/Comments 
	 * TEST TEST TEST
	 * 
	 * EXTRA:
	 * Menu
	 * Music
	 * 
	 * EXTRA EXTRA:
	 * Saving/Loading
	 * Multiplayer
	 */
	
	/**
	 * Qs
	 * 
	 * Trigger prophecy while Prophesizing?
	 * Draw 5 cards from hand or deck?
	 * Multiple nightmares in the reshuffle to the deck?
	 *  
	 */
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
		Assets.init();
		completeDoor = new JButton();
		field = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		display = new Display(this, title, width, height);
		addMouseListener(mouseManager);
		addMouseMotionListener(mouseManager);
		addKeyListener(keyManager);
		
		gameState = new GameState(this);
		menuState = new MenuState(this);
		State.setCurrentState(gameState);
		
		completeDoor.setVisible(true);
		completeDoor.setEnabled(false);
		completeDoor.addActionListener(this);
		setLayout(null);
		add(completeDoor);
		completeDoor.setSize(200,40);
		completeDoor.setLocation((int)gameState.getLimbo().getX(), (int)gameState.getLimbo().getY()+ gameState.getLimbo().getHeight());
//		completeDoor.setFont(new Font("myFont",0, 16));
		completeDoor.setDisabledIcon((Icon) Assets.buttonDisabled);
		completeDoor.setPressedIcon((Icon) Assets.buttonClicked);
		completeDoor.setSelectedIcon((Icon) Assets.buttonActive);
		
		/*
		 * starts the game
		 * don't be concerned about this....
		 */
		for(int i = 0; i < 50; i++)
			update();
	}
	
	public void update(){
		width = display.getFrame().getWidth();
		height = display.getFrame().getHeight();
		
		keyManager.update();
//		System.out.println(keyManager.space);
//		
		if(keyManager.space){
			System.out.println("---------------------------------------------------" + "\nTOP");
			for(int i = gameState.getDeck().getCards().size()-1; i >= 0; i--){
				System.out.println(gameState.getDeck().getCards().get(i));
			}
			System.out.println("---------------------------------------------------");
		}
		
		if(State.getCurrentState() != null)
			State.getCurrentState().update();
		repaint();
//		render();
//		System.out.println("Pro: " + Prophecy.prophosizing);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = field.createGraphics();
		g2.setBackground(new Color(255, 255, 255, 0));
		g2.clearRect(0, 0, width, height);
		
		g.drawImage(Assets.wood, 0, 0, display.getFrame().getWidth(), display.getFrame().getHeight(), null);
		
		heightOffSet = widthOffSet = 0;
//		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		
		g2.drawImage(Assets.boardCover, 0, 0, field.getWidth(), field.getHeight(), null);
		
		if(State.getCurrentState() != null)
			State.getCurrentState().render(g2);
		
		float ratio = (float)field.getWidth() / field.getHeight();
		
		if((float)getWidth() / field.getWidth() < (float)getHeight() / field.getHeight()){
			float height = getWidth() / ratio;
			heightOffSet = getHeight() - height;
			heightOffSet /= 2;
			g.drawImage(field, 0, (int)heightOffSet, getWidth(), (int)(height), null);
		}
		
		else{
			float width = getHeight() * ratio;
			widthOffSet = getWidth() - width;
			widthOffSet /= 2;
			g.drawImage(field, (int)widthOffSet, 0, (int)width, getHeight(), null);
		}
		if(gameState != null){
			float percentX = (float)gameState.getLimbo().getX() / field.getWidth();
			float percentY = ((float)gameState.getLimbo().getY() + gameState.getLimbo().getHeight()) / field.getHeight();
			completeDoor.setLocation((int)(percentX * (getWidth() - widthOffSet*2) + widthOffSet), (int)(percentY * (getHeight() - heightOffSet*2) + heightOffSet) + 16);
			
			
			float percentWidth = (float)gameState.getLimbo().getWidth() / field.getWidth();
			float percentHeight = ((float)40 / field.getHeight());
			completeDoor.setSize((int)(percentWidth * (getWidth() - widthOffSet*2)), (int)(percentHeight * (getHeight() - heightOffSet*2)));
		}
//		System.out.println("Width: " + getWidth() + " Height: " + getHeight());
	}
	
	public void lose(){
		System.out.println("YOU LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOSSSSSSSSSSSSSSSSSSSEEEEEEEEEEEEe");
	}
	
	public void win(){
		System.out.println("YYYYYYYYYYYYYYYYYYYYOOOOOOOOOOOOUUUUUUUUUUUU WWWWWWWWOOOOOOOOOONNNNNNNNNNNN");
	}

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

	public JButton getCompleteDoor() {
		return completeDoor;
	}

	public BufferedImage getField() {
		return field;
	}

	public float getHeightOffSet() {
		return heightOffSet;
	}

	public float getWidthOffSet() {
		return widthOffSet;
	}
	
	

	public void actionPerformed(ActionEvent e) {
		/*
		 * This button is only enabled when the 3 cards get matched up
		 * So, if this is pressed, that must mean that the last card is the door that is going to be completed
		 * Searches through limbo, starting from the end, and finds the first slot with a card, adds to completed doors
		 */
		if(e.getSource() == completeDoor){
//			Slot[] slots = gameState.getLimbo().getSlots();
//			for(int i = slots.length - 1; i >= 0; i--){
//				if(slots[i].storedCard != null){
//					gameState.getDoorsCompleted().addDoor(slots[i].storedCard);
//				}
//			}
			Slot[] slots = gameState.getPlayArea().getSlots();
			Card.CardColors color = null;
			for(int i = slots.length - 1; i >= 0; i--){
				if(slots[i].storedCard != null){
					color = slots[i].storedCard.getColor();
					break;
				}
			}
		
			ArrayList<Card> deck = gameState.getDeck().getCards();
			for(Card c : deck){
				if(c.getType() == Card.CardTypes.DOOR && c.getColor() == color){
					deck.remove(c);
					gameState.getDoorsCompleted().addDoor(c);
					break;
				}
			}
			
			for(int i = slots.length - 1; i >= 0; i--){
				if(slots[i].storedCard != null){
					slots[i].storedCard.setUsed(true);
					slots[i].storedCard.setMoveable(false);
				}
			}
		
			completeDoor.setEnabled(false);
			update();
			update();
		}
	}
}
