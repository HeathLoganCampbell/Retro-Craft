package com.craftclassic.client;

import com.craftclassic.client.utils.AppletAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main {
	public static final int WIDTH = 318;
	public static final int HEIGHT = 200;

	public static final int SCALE = 5;
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
		minecraft.setSize(WIDTH * SCALE, HEIGHT * SCALE);

		
		minecraft.setStub(appletAdapter);

		contentPanel.add("Center", minecraft);

		frame.setSize(minecraft.getSize());
		frame.setVisible(true);

		contentPanel.remove(appletAdapter);
		
//		frame.setCursor(frame.getToolkit().createCustomCursor(
//	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
//	            "null"));

		minecraft.init();
		minecraft.start();
		frame.requestFocus();
	}

	
}
