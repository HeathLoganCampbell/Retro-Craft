package com.minecraft4k.base.frame;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.minecraft4k.base.Minecraft;

public class Viewer {
	private JFrame f;

	public static final int WIDTH = 856;
	public static final int HEIGHT = 480;

	public static void main(String[] av) {
		new Viewer();
	}

	Viewer() 
	{
		f = new JFrame("Minecraft Classic - Daniel is gay");
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
