package dev.Same4254.ThisGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import dev.Same4254.ThisGame.Entities.Card;
import dev.Same4254.ThisGame.Entities.Card.CardSymbols;
import dev.Same4254.ThisGame.Entities.Discard;
import dev.Same4254.ThisGame.Entities.Limbo;
import dev.Same4254.ThisGame.Entities.Prophecy;
import dev.Same4254.ThisGame.Entities.Slot;
import dev.Same4254.ThisGame.Input.KeyManager;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.Sound.Music;
import dev.Same4254.ThisGame.States.GameState;
import dev.Same4254.ThisGame.States.LoseState;
import dev.Same4254.ThisGame.States.MenuState;
import dev.Same4254.ThisGame.States.State;
import dev.Same4254.ThisGame.States.WinState;
import dev.Same4254.ThisGame.dis.DiscardedMenu;
import dev.Same4254.ThisGame.dis.Display;
import dev.Same4254.ThisGame.gfx.Assets;
import dev.Same4254.ThisGame.gfx.ButtonUI;

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
	private WinState winState;
	private LoseState loseState;
	
	private Music music;
	
	private DiscardedMenu discarded;
	private JButton completeDoor;
	private boolean firstTurn;
	
	/*
	 * This is the image that the paint component will draw to then will get scaled
	 */
	private BufferedImage field;
	
	private float heightOffSet, widthOffSet;
	
	private boolean getByKey;
	/**
	 * TODO LIST
	 * 
	 * Winning and losing
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
	 * Bugs
	 * 
	 * dragging back last card in play after a draw
	 * Hit boxes screw up when top is limited
	 */
	
	/**
	 * Qs
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
		winState = new WinState(this);
		loseState = new LoseState(this);
		State.setCurrentState(menuState);
		gameState.getDeck().shuffle();
		
		completeDoor.setVisible(false);
		completeDoor.setEnabled(false);
		completeDoor.addActionListener(this);
		setLayout(null);
		add(completeDoor);
		completeDoor.setSize(200,40);
		completeDoor.setLocation((int)gameState.getLimbo().getX(), (int)gameState.getLimbo().getY()+ gameState.getLimbo().getHeight());
//		completeDoor.setFont(new Font("myFont",0, 16));
		
		completeDoor.setUI(new ButtonUI());
		completeDoor.setOpaque(false);
		completeDoor.setBorderPainted(false);
		
		firstTurn = true;
		
		discarded = new DiscardedMenu(this);
		
		/*
		 * starts the game
		 * don't be concerned about this....
		 */
		for(int i = 0; i < 30; i++)
			update();
	}
	
	public DiscardedMenu getDiscarded() {
		return discarded;
	}

	public void update(){
//		if(keyManager.downArrow)
//			gameState.getDeck().dump5Cards();
		
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
		
//		System.out.println("PRINT PRINT __________________________________________");
		
		Graphics2D g2 = field.createGraphics();
		g2.setBackground(new Color(255, 255, 255, 0));
		g2.clearRect(0, 0, width, height);
		
//		g.drawImage(Assets.wood, 0, 0, display.getFrame().getWidth(), display.getFrame().getHeight(), null);
		
		heightOffSet = widthOffSet = 0;
//		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		
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
		completeDoor.setVisible(false);
		State.setCurrentState(loseState);
	}
	
	public void win(){
		completeDoor.setVisible(false);
		State.setCurrentState(winState);
	}

    public void endMenuState(){
    	State.setCurrentState(gameState);
    }
    
	public GameState getGameState() {
		return gameState;
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
			if(!getByKey){
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
				gameState.getLimbo().shuffleToDeck();
			}
			else{
				Slot[] slots = gameState.getHand().getSlots();
				for(int i = 0; i < slots.length; i++){
					if(slots[i].storedCard != null){
						if(slots[i].storedCard.getSymbol() == CardSymbols.KEY && slots[i].storedCard.getColor() == Limbo.currentDrawnCard.getColor()){
							gameState.getDiscard().addCard(slots[i].storedCard);
							break;
						}
					}
				}
			}
			
			getByKey = false;
			completeDoor.setEnabled(false);
			update();
			update();
		}
	}
	
	public boolean isGetByKey() {
		return getByKey;
	}

	public void setGetByKey(boolean getByKey) {
		this.getByKey = getByKey;
	}

	public boolean isFirstTurn() {
		return firstTurn;
	}
	
	public State getcurrentState(){
		return State.getCurrentState();
	}

	public void setFirstTurn(boolean firstTurn) {
		this.firstTurn = firstTurn;
	}
}
