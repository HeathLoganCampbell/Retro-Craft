package com.craftclassic.client.input;

import com.craftclassic.client.Minecraft;

import java.awt.*;
import java.awt.event.*;

public class Input implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	private static final int TAU = (int) (Math.PI * 4 * 100);
	
	private static boolean[] keys = new boolean[65536];
	private static boolean[] mouseButtons = new boolean[4];
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
		
		halfWidth = this.width / 4;
		halfHeight = this.height / 4;
	}

	public void mouseDragged(MouseEvent e) 
	{
		if(!this.isFocused()) return;
		
		int centerX = this.halfWidth;
		int centerY = this.halfWidth;
		
		this.rawX = e.getX() - centerX;
		this.rawY = (e.getY() - centerY + 45);
		
//		this.robot.mouseMove(centerX, centerY);
		
//		this.minecraft.player.turn(this.rawX, this.rawY);
	}

	private void mouseMoveFixed(final int x,
								final int y) {

		final double modFactor = 1 - (1 / 200);

		final Point origin = MouseInfo.getPointerInfo().getLocation();

		final int deltaX = x - origin.x;
		final int deltaY = y - origin.y;

		final int finalX = (int) (x - deltaX * modFactor); // I don't know if this needs to be rounded.
		final int finalY = (int) (y - deltaY * modFactor); // I couldn't spot a difference if Math.round() was used.

//		robot.mouseMove(finalX, finalY);
	}

	private boolean processing = false;
	public void mouseMoved(MouseEvent e)
	{
		if(!this.isFocused()) return;
		if(processing) return;
		processing = true;

		int centerX = this.halfWidth;
		int centerY = this.halfWidth;
		
		this.rawX = e.getX() - centerX;
		this.rawY = (e.getY() - centerY + 45);

//		this.robot.mouseMove(centerX, centerY);


		processing = false;
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
//		this.robot.mouseMove(this.halfWidth, this.halfHeight);
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

		float rotateSpeed = (float) Math.PI;

		if(KeyEvent.VK_RIGHT == code)
		{
			this.minecraft.setPlaceBlockTypeId(this.minecraft.getPlaceBlockTypeId() + 1);
			this.minecraft.player.turn(rotateSpeed,0);
		}
		
		if(KeyEvent.VK_LEFT == code)
		{
			this.minecraft.setPlaceBlockTypeId(this.minecraft.getPlaceBlockTypeId() - 1);
			this.minecraft.player.turn(-rotateSpeed,0);
		}

		if(KeyEvent.VK_DOWN == code)
		{
			this.minecraft.player.turn(0, -rotateSpeed);
		}

		if(KeyEvent.VK_UP == code)
		{
			this.minecraft.player.turn(0, rotateSpeed);
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

	public static boolean getKey(int key) {return keys[key];}
	
	public static void setKey(int key, boolean keyValue)
	{ 
		keys[key] = keyValue;
	}

	public static boolean getMouse(int button) {return mouseButtons[button];}
	public static void setMouse(int button, boolean value) { mouseButtons[button] = value; }

	public int getMouseX() {return mouseX;}

	public int getMouseY() {return mouseY;}
}