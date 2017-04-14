package dev.Same4254.ThisGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.sun.glass.events.KeyEvent;

import dev.Same4254.ThisGame.Entities.Card;
import dev.Same4254.ThisGame.Entities.Card.CardColors;
import dev.Same4254.ThisGame.Entities.Card.CardSymbols;
import dev.Same4254.ThisGame.Entities.Card.CardTypes;
import dev.Same4254.ThisGame.Entities.Discard;
import dev.Same4254.ThisGame.Entities.Limbo;
import dev.Same4254.ThisGame.Entities.Slot;
import dev.Same4254.ThisGame.Input.KeyManager;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.Sound.Music;
import dev.Same4254.ThisGame.States.GameState;
import dev.Same4254.ThisGame.States.LoseState;
import dev.Same4254.ThisGame.States.MenuState;
import dev.Same4254.ThisGame.States.State;
import dev.Same4254.ThisGame.States.WinState;
import dev.Same4254.ThisGame.dis.DeckMenu;
import dev.Same4254.ThisGame.dis.Display;
import dev.Same4254.ThisGame.gfx.Assets;
import dev.Same4254.ThisGame.gfx.ButtonUIs.CompleteDoorUI;
import dev.Same4254.ThisGame.gfx.ButtonUIs.ReturnToMenuUI;
import dev.Same4254.ThisGame.gfx.ButtonUIs.StartGameUI;

public class Game extends JPanel implements ActionListener, ComponentListener{
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
	
	private DeckMenu deckMenu;
	
	private JButton completeDoor;
	private JButton returnToMenu;
	private JButton startGame;
	
	private boolean firstTurn = true;
	
	/*
	 * This is the image that the paint component will draw to then will get scaled
	 */
	private BufferedImage field; 
	
	private float heightOffSet, widthOffSet;
	
	private boolean getByKey;
	
	private boolean lostFoundHard = false;
	private boolean lostFound = false;
	
	/**
	 * TODO LIST
	 * 
	 * Menus
	 * 
	 * X-Packs
	 * 	Other Prophecy
	 * 	Deselect switch door
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
		returnToMenu = new JButton();
		startGame = new JButton();
		
		field = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		display = new Display(this, title, width, height);
		
		addMouseListener(mouseManager);
		addMouseMotionListener(mouseManager);
		addKeyListener(keyManager);
		addComponentListener(this);
//		display.getFrame().addKeyListener(keyManager);
		
		startGame.setText("Start Game");
		startGame.setVisible(true);
		startGame.setEnabled(true);
		startGame.addActionListener(this);
		startGame.setSize(400,80);
		startGame.setLocation((int)((display.getWidth()/2) - (startGame.getWidth()/2)), (int)((display.getHeight()/1.5) - (startGame.getHeight()/2))); 
//		startGame.setUI(new StartGameUI());
		startGame.setOpaque(true);
		startGame.setBorderPainted(false);
		
		gameState = new GameState(this);
		menuState = new MenuState(this);
		winState = new WinState(this);
		loseState = new LoseState(this);
		State.setCurrentState(menuState);
		
//		gameState.getDeck().shuffle();
		
		completeDoor.setVisible(false);
		completeDoor.setEnabled(false);
		completeDoor.addActionListener(this);
		completeDoor.setSize(200,40);
		completeDoor.setLocation((int)gameState.getLimbo().getX(), (int)gameState.getLimbo().getY()+ gameState.getLimbo().getHeight());
		completeDoor.setUI(new CompleteDoorUI());
		completeDoor.setOpaque(false);
		completeDoor.setBorderPainted(false);
		
		returnToMenu.setVisible(false);
		returnToMenu.setEnabled(true);
		returnToMenu.addActionListener(this);
		returnToMenu.setSize(400,80);
		returnToMenu.setLocation((int)((display.getWidth()/2) - (returnToMenu.getWidth()/2)), (int)((display.getHeight()/1.5) - (returnToMenu.getHeight()/2))); 
		returnToMenu.setUI(new ReturnToMenuUI());
		returnToMenu.setOpaque(false);
		returnToMenu.setBorderPainted(false);
		
		setLayout(null);
		add(completeDoor);
		add(returnToMenu);
		add(startGame);
		
		firstTurn = true;
		
		deckMenu = new DeckMenu(this);
		
		float percentX = (float)gameState.getLimbo().getX() / field.getWidth();
		float percentY = ((float)gameState.getLimbo().getY() + gameState.getLimbo().getHeight()) / field.getHeight();
		completeDoor.setLocation((int)(percentX * (getWidth() - widthOffSet*2) + widthOffSet), (int)(percentY * (getHeight() - heightOffSet*2) + heightOffSet) + 16);
		
		float percentWidth = (float)gameState.getLimbo().getWidth() / field.getWidth();
		float percentHeight = ((float)40 / field.getHeight());
		completeDoor.setSize((int)(percentWidth * (getWidth() - widthOffSet*2)), (int)(percentHeight * (getHeight() - heightOffSet*2)));
		
		update();
	}
	
	public DeckMenu getDeckMenu() {
		return deckMenu;
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
		if(keyManager.backspace){
			lose();
		}
		
		if(State.getCurrentState() != null)
			State.getCurrentState().update();
		repaint();
		
//		System.out.println(gameState.getDoorsCompleted().getOrder());
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
		
//		System.out.println("Width: " + getWidth() + " Height: " + getHeight());
	}
	
	public void lose(){
		completeDoor.setVisible(false);
		returnToMenu.setVisible(true);
		State.setCurrentState(loseState);
	}
	
	public void win(){
		completeDoor.setVisible(false);
		returnToMenu.setVisible(true);
		State.setCurrentState(winState);
	}

    public void endMenuState(){
//    	gameState = new GameState(this);
    	completeDoor.setEnabled(false);
    	Discard.size = 0;
    	State.setCurrentState(gameState);
    	deckMenu.update();
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
			
				boolean added = false;
				
				ArrayList<Card> deck = gameState.getDeck().getCards();
				for(Card c : deck){
					if(c.getType() == Card.CardTypes.DOOR && c.getColor() == color){
//						deck.remove(c);
						added = gameState.getDoorsCompleted().addDoor(c);
						break;
					}
				}
				
				if(!added){
					Slot[] proSlots = gameState.getProphecy().getSlots();
					for(Slot s : proSlots){
						if(s.storedCard != null && s.storedCard.getType() == CardTypes.DOOR && s.storedCard.getColor() == color){
							added = gameState.getDoorsCompleted().addDoor(s.storedCard);
							break;
						}
					}
				}
				
				if(added){
					for(int i = slots.length - 1; i >= 0; i--){
						if(slots[i].storedCard != null){
							slots[i].storedCard.setUsed(true);
							slots[i].storedCard.setMoveable(false);
						}
					}
					gameState.getLimbo().shuffleToDeck();
				}
			}
			else{
				Slot[] handSlots = gameState.getHand().getSlots();
				for(int i = 0; i < handSlots.length; i++){
					if(handSlots[i].storedCard != null){
						if(lostFound){
							CardColors tempColor = null;
							
							ArrayList<CardColors> tempOrder = gameState.getDoorsCompleted().getOrder();
							
							for(CardColors c : tempOrder){
								if(c != null){
									tempColor = c;
									break;
								}
							}
							
							if(handSlots[i].storedCard.getSymbol() == CardSymbols.KEY && handSlots[i].storedCard.getColor() == Limbo.currentDrawnCard.getColor() && tempColor == Limbo.currentDrawnCard.getColor()){
								gameState.getDiscard().addCard(handSlots[i].storedCard);
								break;
							}
						}
						else{
							if(handSlots[i].storedCard.getSymbol() == CardSymbols.KEY && handSlots[i].storedCard.getColor() == Limbo.currentDrawnCard.getColor()){
								gameState.getDiscard().addCard(handSlots[i].storedCard);
								break;
							}
						}
					}
				}
			}
			
			getByKey = false;
			completeDoor.setEnabled(false);
			update();
		}
		else if(e.getSource() == returnToMenu){
			gameState = new GameState(this);
			menuState = new MenuState(this);
			State.setCurrentState(menuState);
			returnToMenu.setVisible(false);
			startGame.setVisible(true);
			update();
		}
		else if(e.getSource() == startGame){
			completeDoor.setVisible(true);
			deckMenu.getDis().getFrame().setVisible(true);
			startGame.setVisible(false);
			
			KeyManager.enter = false;
			keyManager.keys[KeyEvent.VK_ENTER] = false;
			
			endMenuState();
			update();
		}
	}
	
	public JButton getStartGame() {
		return startGame;
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
	
	public boolean isLostFoundHard() {
		return lostFoundHard;
	}

	public boolean isLostFound() {
		return lostFound;
	}
	
	public State getcurrentState(){
		return State.getCurrentState();
	}

	public void setFirstTurn(boolean firstTurn) {
		this.firstTurn = firstTurn;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void componentResized(ComponentEvent e) {
		if(gameState != null){
			float percentX = (float)gameState.getLimbo().getX() / field.getWidth();
			float percentY = (float)(644) / field.getHeight();
			completeDoor.setLocation((int)(percentX * (getWidth() - widthOffSet*2) + widthOffSet), (int)(percentY * (getHeight() - heightOffSet*2) + heightOffSet));
			
			float percentWidth = (float)gameState.getLimbo().getWidth() / field.getWidth();
			float percentHeight = ((float)40 / field.getHeight());
			completeDoor.setSize((int)(percentWidth * (getWidth() - widthOffSet*2)), (int)(percentHeight * (getHeight() - heightOffSet*2)));
			
//			startGame.setLocation((int)((display.getWidth()/2) - (startGame.getWidth()/2)), (int)((display.getHeight()/1.5) - (startGame.getHeight()/2)));
			
			percentX = (float)((display.getWidth()/2) - (startGame.getWidth()/2)) / field.getWidth();
			percentY = (float)(644) / field.getHeight();
			startGame.setLocation((int)(percentX * (getWidth() - widthOffSet*2) + widthOffSet), (int)(percentY * (getHeight() - heightOffSet*2) + heightOffSet));
			
			percentWidth = (float)400 / field.getWidth();
			percentHeight = ((float)80 / field.getHeight());
			startGame.setSize((int)(percentWidth * (getWidth() - widthOffSet*2)), (int)(percentHeight * (getHeight() - heightOffSet*2)));
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
