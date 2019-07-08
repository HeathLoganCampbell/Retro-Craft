package com.craftclassic.play;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import com.craftclassic.play.utils.AppletAdapter;

import javax.swing.JFrame;


public class Main {
	public static final int WIDTH = (int) (856);
	public static final int HEIGHT = (int) (480);
	public static final String TITLE = "Retro Craft";

	public static void main(String[] av) {
		new Main();
	}

	public Main() 
	{
		JFrame frame = new JFrame(TITLE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});
		Container contentPanel = frame.getContentPane();
		AppletAdapter appletAdapter = new AppletAdapter();

 		Minecraft minecraft = new Minecraft(WIDTH, HEIGHT);
		minecraft.setSize(WIDTH, HEIGHT);

		
		minecraft.setStub(appletAdapter);

		contentPanel.add("Center", minecraft);

		frame.setSize(minecraft.getSize());
		frame.setVisible(true);

		contentPanel.remove(appletAdapter);
		
		frame.setCursor(frame.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));

		minecraft.init();
		minecraft.start();
		frame.requestFocus();
	}

	
}
