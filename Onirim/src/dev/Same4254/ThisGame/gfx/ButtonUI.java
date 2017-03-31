package dev.Same4254.ThisGame.gfx;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class ButtonUI extends BasicButtonUI{
	public void paint(Graphics g, JComponent c){
//		System.out.println(c.getWidth() + " " + c.getHeight());
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
