package dev.Same4254.ThisGame.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardColors;
import dev.Same4254.ThisGame.Entities.Card.CardSymbols;
import dev.Same4254.ThisGame.Entities.Card.CardTypes;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.States.GameState;
import dev.Same4254.ThisGame.gfx.Assets;

public class Deck extends Entity{
	private ArrayList<Card> cards; //68 cards
	
	private MouseManager mouse;
	private Rectangle hitBox;
	
	private ArrayList<Card> outOfDeck;
	
	private Random randy = new Random();
	private Slot[] slots;
	private Limbo limbo;
	private Game game;
	
	public Deck(Game game, GameState gameState, int x, int y, int width, int height) {
		super(x, y, width, height);
		cards = new ArrayList<Card>();
		this.game = game;
		mouse = game.getMouseManager();
		
		this.slots = gameState.getHand().getSlots();
		this.limbo = gameState.getLimbo();
		
		
		outOfDeck = gameState.getCardsOutOfDeck();
		
		hitBox = new Rectangle((int)x, (int)y, width, height);
		
		for(int i = 0; i < 58; i++){
			if(i < 6)
				cards.add(new Card(game, gameState, Assets.tanCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.TAN));
			else if(i < 10)                
				cards.add(new Card(game, gameState, Assets.tanCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.TAN));
			else if(i < 13)                
				cards.add(new Card(game, gameState, Assets.tanCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.TAN));
			                               
			else if(i < 22)                
				cards.add(new Card(game, gameState, Assets.redCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.RED));
			else if(i < 26)                
				cards.add(new Card(game, gameState, Assets.redCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.RED));
			else if(i < 29)                
				cards.add(new Card(game, gameState, Assets.redCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.RED));
			                               
			else if(i < 37)                
				cards.add(new Card(game, gameState, Assets.blueCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.BLUE));
			else if(i < 41)                
				cards.add(new Card(game, gameState, Assets.blueCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.BLUE));
			else if(i < 44)                
				cards.add(new Card(game, gameState, Assets.blueCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.BLUE));
			                               
			else if(i < 51)                
				cards.add(new Card(game, gameState, Assets.greenCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.GREEN));
			else if(i < 55)                
				cards.add(new Card(game, gameState, Assets.greenCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.GREEN));
			else if(i < 58)                
				cards.add(new Card(game, gameState, Assets.greenCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.GREEN));
		}
		for(int i = 0; i < 10; i++){
			cards.add(new Card(game, gameState, Assets.nightmare, x, y, CardTypes.DREAM, CardSymbols.NIGHTMARE, CardColors.BLACK));
		}
		
		for(int i = 0; i < 2; i++){
			cards.add(new Card(game, gameState, Assets.greenDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.GREEN));
			cards.add(new Card(game, gameState, Assets.redDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.RED));
			cards.add(new Card(game, gameState, Assets.blueDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.BLUE));
			cards.add(new Card(game, gameState, Assets.tanDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.TAN));
		}
		
		shuffle();
	}

	public void shuffle(){
		for(int i = cards.size() - 1; i > 0; i--){
	      int index = randy.nextInt(i + 1);
	      Card a = cards.get(index);
	      cards.set(index, cards.get(i));
	      cards.set(i, a);
	    }
	}
	
	public void update() {
		if(!Prophecy.prophosizing && hitBox.contains(mouse.getMouseX(), mouse.getMouseY()) && mouse.justReleased){
//			System.out.println("MREG");
//			outOfDeck.add(cards.get(cards.size() - 1));
//			cards.get(cards.size() - 1).makeVisible();
//			cards.remove(cards.size() - 1);
			for(int i = 0; i < slots.length; i++){
				if(slots[i].storedCard == null){
//					notifyProphecy();
//					
					Card temp = null;
					boolean cardFound = false;
					for(int k = 0; k < game.getGameState().getProphecy().getSlots().length; k++){
						if(game.getGameState().getProphecy().getSlots()[k].storedCard != null){
							System.out.println("Bye");
							temp = game.getGameState().getProphecy().getSlots()[k].storedCard;
							cardFound = true;
							break;
						}
					}
					
					if(!cardFound){
						System.out.println("Hi");
						temp = cards.remove(cards.size()-1);
					}
					
					cardFound = false;
					temp.setInProphecy(false);
					temp.makeVisible();
					outOfDeck.add(temp);
					
					
					if(temp.getType() != CardTypes.LOCATION){
						limbo.addCard(temp);
						break;
					}
					else{
						game.getGameState().getHand().addCard(temp);
//						game.update();
						break;
					}
				}
			}
			mouse.justReleased = false;
		}
	}
	
	public void notifyProphecy(){
		Slot[] proSlots = game.getGameState().getProphecy().getSlots();
		for(int i = 0; i < proSlots.length; i++){
			if(proSlots[i].storedCard != null){
				proSlots[i].storedCard = null;
				return;
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(Assets.cardBack, x, y, null);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
}
