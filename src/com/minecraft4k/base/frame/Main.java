package com.minecraft4k.base.frame;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.minecraft4k.base.Minecraft;

public class Main {
	/*
	 * Heath is the future,
	 * 
	 * This is what's wrong
	 * - The mouse doesn't get trapped on the screen and the 
	 *   Robot class doesn't really work because the robot class
	 *   Calls a mouse move event
	 * - add multi-player
	 * - show more entities? Other players?
	 * - Import textures from a file, so they can be easily added
	 *   
	 * Fixes from that I did
	 * - I split it up into a few files
	 * - Fixed z collision problem (made collision loop check z before y 
	 *   because you're always touching the ground so it never made it to z)
	 * - Move the mouse controls to a motion system
	 * - Change the map to be a flat world instead of random
	 * 
	 * You stopped working on this because you had to study for your
	 * 2019 semester 1 exams, which one is on monday... a day away...
	 * Well anyway, good luck
	 * 
	 */
	private JFrame f;

	public static final int WIDTH = (int) (856);
	public static final int HEIGHT = (int) (480);

	public static void main(String[] av) {
		new Main();
	}

	Main() 
	{
		f = new JFrame("Minecraft Classic");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.setVisible(false);
				f.dispose();
				System.exit(0);
			}
		});
		Container contentPanel = f.getContentPane();
		AppletAdapter aa = new AppletAdapter();

 		Minecraft minecraft = new Minecraft(WIDTH, HEIGHT);
		minecraft.setSize(WIDTH, HEIGHT);

		
		minecraft.setStub(aa);

		contentPanel.add("Center", minecraft);

		f.setSize(minecraft.getSize());
		f.setVisible(true);

		contentPanel.remove(aa);
		
		f.setCursor(f.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));

		minecraft.init();
		minecraft.start();
	}

	
}
