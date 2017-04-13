package dev.Same4254.ThisGame.gfx.ButtonUIs;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

import dev.Same4254.ThisGame.gfx.Assets;

public class CompleteDoorUI extends BasicButtonUI{
	public void paint(Graphics g, JComponent c){
		JButton b = (JButton)c;
		
		if(!c.isEnabled()){
			g.drawImage(Assets.buttonDisabled, 0, 0, c.getWidth(), c.getHeight(), null);
		}
		else if(b.getModel().isArmed()){
			g.drawImage(Assets.buttonClicked, 0, 0, c.getWidth(), c.getHeight(), null);
		}
		else if(c.isEnabled()){
			g.drawImage(Assets.buttonActive, 0, 0, c.getWidth(), c.getHeight(), null);
		}
	}
}
