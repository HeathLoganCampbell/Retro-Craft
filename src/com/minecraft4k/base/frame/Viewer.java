package com.minecraft4k.base.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.minecraft4k.base.Minecraft;

public class Viewer {
	private JFrame f;

	public static final int WIDTH = 888;
	public static final int HEIGHT = 568;

	public static void main(String[] av) {
		new Viewer();
	}

	Viewer() 
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
		contentPanel.setLayout(new BorderLayout());
		AppletAdapter aa = new AppletAdapter();

		Minecraft minecraft = new Minecraft();
		minecraft.setSize(WIDTH, HEIGHT);

		
		minecraft.setStub(aa);

		contentPanel.add("Center", minecraft);

		f.setSize(minecraft.getSize());
		f.setVisible(true);

		contentPanel.remove(aa);

		minecraft.init();
		minecraft.start();
	}

	
}
