package com.craftclassic.play;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Input implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	private static final int TAU_100 = (int) (Math.PI * 200);
	
	private boolean[] keys = new boolean[65536];
	private boolean[] mouseButtons = new boolean[4];
	private int mouseX = TAU_100;
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
	
	
	public Input(int width, int height, Robot robot) 
	{
		this.width = width;
		this.height = height;
		this.robot = robot;
		
		halfWidth = this.width >> 1;
		halfHeight = this.height >> 1;
	}

	public void mouseDragged(MouseEvent e) 
	{
		if(this.rawX == e.getX() &&
				this.rawY == (this.height - e.getY())) return;
		
		this.pushMousePositionUpdate(e.getX(), e.getY());
		
		this.mouseX -= (lastX - rawX);
		this.inverseMouseY -= (lastY - rawY - 45);
		this.mouseY = this.height - this.inverseMouseY;
		
		
		this.pushMousePositionUpdate(this.halfWidth, this.halfHeight);
		this.pushMousePositionUpdate(this.halfWidth, this.halfHeight);
		this.robot.mouseMove(this.halfWidth, this.halfHeight);
	}
	
	public void pushMousePositionUpdate(int x, int y)
	{
		lastX = rawX;
		lastY = rawY;
		
		rawX = x;
		rawY = y;
	}

	public void mouseMoved(MouseEvent e) 
	{
		if(this.rawX == e.getX() &&
				this.rawY == (this.height - e.getY())) return;
		
		this.pushMousePositionUpdate(e.getX(), e.getY());
		
		this.mouseX -= (lastX - rawX);
		this.mouseX = (int) ((this.mouseX % TAU_100) + TAU_100);
		
		this.inverseMouseY -= (lastY - rawY - 45);
		this.mouseY = this.height - this.inverseMouseY;
		
		this.rawX = this.lastX = this.width / 2;
		this.rawY = this.lastY = this.height / 2;
		
		this.robot.mouseMove(this.width / 2, this.height / 2);
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
}