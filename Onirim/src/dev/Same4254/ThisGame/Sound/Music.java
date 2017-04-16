package dev.Same4254.ThisGame.Sound;

import java.util.Random;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class Music {
	private MediaPlayer player;
	private JFXPanel panel;
	private Duration startTime;
	
	private Runnable onEnd;
	private Random randy;
	
	private String[] paths;
	private int index;
	
	public Music(String[] paths){
		this.paths = paths;
		randy = new Random();
		
		panel = new JFXPanel();
		Platform.runLater(() -> initFX(panel, paths[0]));
		setOnEnd(this::nextSong);
	}
	
	public void changeSong(String path){
		Platform.runLater(()->{
			startTime = Duration.ZERO;
			Media media = new Media(path);
			player.stop();
			player = new MediaPlayer(media);
			player.setAutoPlay(true);
			player.setOnEndOfMedia(onEnd);
		});
	}

	public void pauseSong(){
		startTime = player.getCurrentTime();
		Platform.runLater(player::pause);
	}
	
	public void resume(){
		player.setStartTime(startTime);
		Platform.runLater(player::play);
	}
	
	public void play(){
		Platform.runLater(player::play);
	}
	
	public void initFX(JFXPanel panel, String path){
		Media media = new Media(path);
		player = new MediaPlayer(media);
		MediaView view = new MediaView(player);
		StackPane pane = new StackPane(view);
		panel.setScene(new Scene(pane));
		
		player.play();
	}

	public void nextSong(){
//		System.out.println("Next Song");
		
		int lastIndex = index;
		index = randy.nextInt(paths.length);
		
		if(index == lastIndex)
			nextSong();
		
		changeSong(paths[index]);
	}
	
	public void setOnEnd(Runnable onEnd) {
		this.onEnd = onEnd;
		Platform.runLater(()->player.setOnEndOfMedia(onEnd));
	}

	public MediaPlayer getPlayer() {
		return player;
	}
}
