package dev.Same4254.ThisGame.gfx.ButtonUIs;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

import dev.Same4254.ThisGame.gfx.Assets;

public class ReturnToMenuUI extends BasicButtonUI{
	public void paint(Graphics g, JComponent c){
		JButton b = (JButton)c;
		
		if(b.getModel().isArmed()){
			g.drawImage(Assets.returnButtonArmed, 0, 0, c.getWidth(), c.getHeight(), null);
		}
		else if(c.isEnabled()){
			g.drawImage(Assets.returnButton, 0, 0, c.getWidth(), c.getHeight(), null);
		}
	}
}
