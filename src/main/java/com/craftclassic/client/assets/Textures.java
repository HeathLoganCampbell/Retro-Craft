package com.craftclassic.client.assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Textures {
	public static Bitmap playerTextures;
	public static Bitmap blockTextures;
	public static Bitmap fontTilesheet;
	public static Bitmap particleTilesheet;
	
	
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
		
		BufferedImage fontImg = ImageIO.read(Textures.class.getClassLoader().getResource("font.png"));
		fontTilesheet = new Bitmap(fontImg);
		
		BufferedImage particleImg = ImageIO.read(Textures.class.getClassLoader().getResource("particle.png"));
		particleTilesheet = new Bitmap(particleImg);
	}	
}