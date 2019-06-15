package com.minecraft4k.base;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Input implements KeyListener, FocusListener,
		MouseListener, MouseMotionListener {

	private boolean[] keys = new boolean[65536];
	private boolean[] mouseButtons = new boolean[4];
	private int mouseX = 0;
	private int mouseY = 0;

	public void mouseDragged(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) 
	{}

	public void mouseEntered(MouseEvent e) 
	{}

	public void mouseExited(MouseEvent e) 
	{}

	public void mousePressed(MouseEvent e) 
	{
		int code = e.getButton();
		if (code > 0 && code < mouseButtons.length)
			mouseButtons[code] = true;
	}

	public void mouseReleased(MouseEvent e) 
	{
		int code = e.getButton();
		if (code > 0 && code < mouseButtons.length)
			mouseButtons[code] = false;
	}

	public void focusGained(FocusEvent e) 
	{}

	public void focusLost(FocusEvent e) 
	{
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
		for (int i = 0; i < mouseButtons.length; i++)
			mouseButtons[i] = false;
	}


	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		if(code > 0 && code < keys.length)
			keys[code] = true;
	}

	public void keyReleased(KeyEvent e) 
	{
		int code = e.getKeyCode();
		if(code > 0 && code < keys.length)
			keys[code] = false;
	}

	public void keyTyped(KeyEvent e) 
	{}

	public boolean getKey(int key) {return keys[key];}

	public boolean getMouse(int button) {return mouseButtons[button];}

	public int getMouseX() {return mouseX;}

	public int getMouseY() {return mouseY;}
}