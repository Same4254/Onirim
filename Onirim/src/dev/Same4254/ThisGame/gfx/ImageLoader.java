package dev.Same4254.ThisGame.gfx;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLoader {
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ImageIcon loadIcon(String path){
		try {
			 return new ImageIcon(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
