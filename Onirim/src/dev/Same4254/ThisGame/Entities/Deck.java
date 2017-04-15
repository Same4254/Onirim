package dev.Same4254.ThisGame.Entities;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

import dev.Same4254.ThisGame.Game;
import dev.Same4254.ThisGame.Entities.Card.CardColors;
import dev.Same4254.ThisGame.Entities.Card.CardSymbols;
import dev.Same4254.ThisGame.Entities.Card.CardTypes;
import dev.Same4254.ThisGame.Input.MouseManager;
import dev.Same4254.ThisGame.States.GameState;
import dev.Same4254.ThisGame.gfx.Assets;

public class Deck extends Entity{
	private ArrayList<Card> cards; 
	
//	private MouseManager mouse;
	private Rectangle hitBox;
	
//	private ArrayList<Card> outOfDeck;
	
//	private Random randy = new Random();
	private Slot[] slots;
	private Limbo limbo;
	private Game game;
	public static boolean enabled;
	
	public Deck(Game game, GameState gameState, int x, int y, int width, int height) {
		super(x, y, width, height);
		cards = new ArrayList<Card>();
		this.game = game;
//		mouse = game.getMouseManager();
		
		this.slots = gameState.getHand().getSlots();
		this.limbo = gameState.getLimbo();
		
		enabled = true;
		
//		outOfDeck = gameState.getCardsOutOfDeck();
		
		hitBox = new Rectangle((int)x, (int)y, width, height);
		
		for(int i = 0; i < 6; i++){
			cards.add(new Card(game, gameState, Assets.tanCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.TAN, 0));
		}
		
		for(int i = 0; i < 9; i++){
			cards.add(new Card(game, gameState, Assets.redCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.RED, 3));
		}
		
		for(int i = 0; i < 8; i++){
			cards.add(new Card(game, gameState, Assets.blueCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.BLUE, 6));
		}
		
		for(int i = 0; i < 7; i++){
			cards.add(new Card(game, gameState, Assets.greenCardSun, x, y, CardTypes.LOCATION, CardSymbols.SUN, CardColors.GREEN, 9));
		}
		
		for(int i = 0; i < 4; i++){
			cards.add(new Card(game, gameState, Assets.tanCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.TAN, 1));
			cards.add(new Card(game, gameState, Assets.redCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.RED, 4));
			cards.add(new Card(game, gameState, Assets.blueCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.BLUE, 7));
			cards.add(new Card(game, gameState, Assets.greenCardMoon, x, y, CardTypes.LOCATION, CardSymbols.MOON, CardColors.GREEN, 10));
		}
		
		for(int i = 0; i < 3; i++){
			cards.add(new Card(game, gameState, Assets.tanCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.TAN, 2));
			cards.add(new Card(game, gameState, Assets.redCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.RED, 5));
			cards.add(new Card(game, gameState, Assets.blueCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.BLUE, 8));
			cards.add(new Card(game, gameState, Assets.greenCardKey, x, y, CardTypes.LOCATION, CardSymbols.KEY, CardColors.GREEN, 11));
		}
		
		for(int i = 0; i < 10; i++){
			cards.add(new Card(game, gameState, Assets.nightmare, x, y, CardTypes.DREAM, CardSymbols.NIGHTMARE, CardColors.BLACK, 12));
		}
		
		for(int i = 0; i < 2; i++){
			cards.add(new Card(game, gameState, Assets.greenDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.GREEN, 13));
			cards.add(new Card(game, gameState, Assets.redDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.RED, 14));
			cards.add(new Card(game, gameState, Assets.blueDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.BLUE, 15));
			cards.add(new Card(game, gameState, Assets.tanDoor, x, y, CardTypes.DOOR, CardSymbols.DOOR, CardColors.TAN, 16));
		}
		
//		shuffle();
	}

	public void dump5Cards(){
		Slot[] propSlots = game.getGameState().getProphecy().getSlots();
		for(int i = 0; i < 5; i++){
			if(cards.size() < 5){
				game.lose();
				return;
			}
			
			Card temp = null;
			for(Slot s : propSlots){
				if(s.storedCard != null){
					temp = s.storedCard;
				}
			}
			
			if(temp == null){
				temp = cards.remove(cards.size()-1);
			}
			
			if(temp.getType() == CardTypes.LOCATION){
				game.getGameState().getDiscard().addCardWithoutCheck(temp);
			}
			else{ 
				game.getGameState().getLimbo().addCard(temp);
			}
		}
		game.getGameState().getLimbo().shuffleToDeck();
	}
	
	public void shuffle(){
		System.out.println("SHUFFLE");
		game.getGameState().getProphecy().clearAllProphecy();
		Collections.shuffle(cards);
	}
	
	public void shuffleWithoutClear(){
		System.out.println("SHUFFLE");
		Collections.shuffle(cards);
	}
	
	public void update() {
		if(enabled){
			if(game.getGameState().getDoorsCompleted().getSlots()[game.getGameState().getDoorsCompleted().getSlots().length - 1].storedCard != null){
				game.win();
				return;
			}
			
			else if(cards.size() == 0 && Hand.handSize < 5 && !game.getCompleteDoor().isEnabled()){
				game.lose();
				return;
			}
			
			if(!Prophecy.prophosizing && hitBox.contains(MouseManager.mouseX, MouseManager.mouseY) && MouseManager.justReleased){
				deckPressed();
				
				MouseManager.justReleased = false;
			}
		}
	}
	
	public void deckPressed(){
		if(game.getCompleteDoor().isEnabled() && Limbo.currentDrawnCard != null && Limbo.currentDrawnCard.getType() != CardTypes.DOOR){
			game.getGameState().getPlayArea().overRide();
		}
		else if(game.getCompleteDoor().isEnabled() && Limbo.currentDrawnCard == null){
			game.getGameState().getPlayArea().overRide();
		}
		else if(game.getCompleteDoor().isEnabled()){
			game.getCompleteDoor().setEnabled(false);
		}
		
		if(Limbo.currentDrawnCard != null && !game.isFirstTurn() && Limbo.currentDrawnCard.getSymbol() == CardSymbols.NIGHTMARE){
			game.getGameState().getDiscard().addCard(Limbo.currentDrawnCard);
//			game.getGameState().getCardsOutOfDeck().remove(Limbo.currentDrawnCard);
			Limbo.currentDrawnCard = null;
			dump5Cards();
			
			MouseManager.justReleased = false;
			return;
		}
		
		draw();
	}
	
	public void draw(){
		for(int i = 0; i < slots.length; i++){
			if(slots[i].storedCard == null){
				Card temp = null;
				boolean cardFound = false;
				
				//Checks to see if there is a card in prophecy to draw
				for(int k = 0; k < game.getGameState().getProphecy().getSlots().length; k++){
					if(game.getGameState().getProphecy().getSlots()[k].storedCard != null){
						temp = game.getGameState().getProphecy().getSlots()[k].storedCard;
						cardFound = true;
						temp.setInProphecy(false);
						break;
					}
				}
				
				//No card in prohecy was found, so get card from deck
				if(!cardFound){
					temp = cards.remove(cards.size()-1);
					temp.setInProphecy(false);
				}
				
				cardFound = false;
				temp.makeVisible();
//				outOfDeck.add(temp);
				
				
				//make cards in the play area not moveable
				Slot[] playAreaSlots = game.getGameState().getPlayArea().getSlots();
				for(int k = playAreaSlots.length-1; k >= 0; k--){
					if(playAreaSlots[k].storedCard != null){
						playAreaSlots[k].storedCard.setMoveable(false);
					}
				}
				
				
				//hand out the card depending on what it is
				if(temp.getType() != CardTypes.LOCATION){
					limbo.addCard(temp);
					break;
				}
				else{
					game.getGameState().getHand().addCard(temp);
					Limbo.currentDrawnCard = null;
					Hand.handSize++;
					if(Hand.handSize == 5 && game.getGameState().getLimbo().getSlots()[0].storedCard != null){
						game.getGameState().getLimbo().shuffleToDeck();
					}
					break;
				}
			}
		}
		
		//checks to see if there is a match of a key and a door in limbo
		game.getGameState().getHand().checkForDoorToKeyMatch();
	}
	
	public void render(Graphics g) {
//		g.setColor(Color.RED);
//		g.fillRect(x, y, width, height);
		g.drawImage(Assets.cardBack, x, y,100,158, null);
		g.setFont(new Font("MyFont", Font.PLAIN, 18));
		
		
		//counts up the cards in prophecy
		int temp = 0;
		for(Slot s : game.getGameState().getProphecy().getSlots()){
			if(s.storedCard != null){
				temp++;
			}
		}
		
		g.drawString("Deck Size: " + (cards.size()+temp), x, y + height + 17);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
}
