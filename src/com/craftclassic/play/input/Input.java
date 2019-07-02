package com.craftclassic.play.input;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.craftclassic.play.Minecraft;

public class Input implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	private static final int TAU = (int) (Math.PI * 4 * 100);
	
	private boolean[] keys = new boolean[65536];
	private boolean[] mouseButtons = new boolean[4];
	private int mouseX = TAU;
	private int mouseY = 10;
	private int inverseMouseY = 0;
	
	private int width;
	private int height;
	private Robot robot;
	
	private int lastX = 0;
	private int lastY = 0;
	
	private int rawX = 0;
	private int rawY = 0;
	
	private int halfWidth;
	private int halfHeight;
	private Minecraft minecraft;
	private boolean isFocused = false;

	
	
	public Input(int width, int height, Robot robot, Minecraft minecraft) 
	{
		this.width = width;
		this.height = height;
		this.robot = robot;
		this.minecraft = minecraft;
		
		halfWidth = this.width >> 1;
		halfHeight = this.height >> 1;
	}

	public void mouseDragged(MouseEvent e) 
	{
		if(!this.isFocused()) return;
		
		int centerX = this.halfWidth;
		int centerY = this.halfWidth;
		
		this.rawX = e.getX() - centerX;
		this.rawY = (e.getY() - centerY + 45);
		
		this.robot.mouseMove(centerX, centerY);
		
		this.minecraft.turn(this.rawX, this.rawY);
	}
	
	

	public void mouseMoved(MouseEvent e) 
	{
		if(!this.isFocused()) return;
		
		int centerX = this.halfWidth;
		int centerY = this.halfWidth;
		
		this.rawX = e.getX() - centerX;
		this.rawY = (e.getY() - centerY + 45);
		
		this.robot.mouseMove(centerX, centerY);
		
		this.minecraft.turn(this.rawX, this.rawY);
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
	
	public boolean isFocused()
    {
    	return this.isFocused;
    }

	public void focusGained(FocusEvent e) 
	{
		this.robot.mouseMove(this.halfWidth, this.halfHeight);
		this.isFocused = true;
	}

	public void focusLost(FocusEvent e) 
	{
		this.isFocused = false;
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
		
		if(KeyEvent.VK_RIGHT == code)
		{
			this.minecraft.setPlaceBlockTypeId(this.minecraft.getPlaceBlockTypeId() + 1);
		}
		
		if(KeyEvent.VK_LEFT == code)
		{
			this.minecraft.setPlaceBlockTypeId(this.minecraft.getPlaceBlockTypeId() - 1);
		}
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
}