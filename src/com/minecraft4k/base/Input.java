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
	
	private int width;
	private int height;
	
	public Input(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public void mouseDragged(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = this.height - e.getY();
	}

	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = this.height - e.getY();
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
	
	public void setKey(int key, boolean keyValue)
	{ 
		keys[key] = keyValue;
	}

	public boolean getMouse(int button) {return mouseButtons[button];}
	public void setMouse(int button, boolean value) { this.mouseButtons[button] = value; }

	public int getMouseX() {return mouseX;}

	public int getMouseY() {return mouseY;}
	
	
//  @Override
//  public boolean handleEvent(Event paramEvent) {
//      int i = 0;
//      switch (paramEvent.id) {
//          case 401: {
//              i = 1;
//          }
//          case 402: {
//              this.inputData[paramEvent.key] = i;
//              break;
//          }
//          case 501: {
//              i = 1;
//              this.inputData[2] = paramEvent.x;
//              this.inputData[3] = paramEvent.y;
//          }
//          case 502: {
//              if ((paramEvent.modifiers & 4) > 0) {
//                  this.inputData[1] = i;
//                  break;
//              }
//              this.inputData[0] = i;
//              break;
//          }
//          case 503: 
//          case 506: {
//              this.inputData[2] = paramEvent.x;
//              this.inputData[3] = paramEvent.y;
//              break;
//          }
//          case 505: {
//              this.inputData[2] = 0;
//          }
//      }
//      return true;
//  }
}