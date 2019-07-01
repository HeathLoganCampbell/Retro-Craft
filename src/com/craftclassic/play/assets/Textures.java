package com.craftclassic.play.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {
	public static Bitmap playerTextures;
	public static Bitmap blockTextures;
	
	
	static 
	{
		try 
		{
			initTextures();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void initTextures() throws IOException 
	{
		BufferedImage playerImage = ImageIO.read(Textures.class.getClassLoader().getResource("player.png"));
		playerTextures = new Bitmap(playerImage);
		
		BufferedImage blocksImage = ImageIO.read(Textures.class.getClassLoader().getResource("saved-texturess.png"));
		blockTextures = new Bitmap(blocksImage);
	}	
}