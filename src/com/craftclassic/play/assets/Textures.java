package com.craftclassic.play.assets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Textures {
	static Random random = new Random();
	public static int[] textureData = new int[12288];
	public static int height = 0;
	public static int width = 0;
	static {
		initTextures();
	}
	
	public static BufferedImage convert(BufferedImage src, int bufImgType) 
	{
	    BufferedImage img= new BufferedImage(src.getWidth(), src.getHeight(), bufImgType);
	    Graphics2D g2d= img.createGraphics();
	    g2d.drawImage(src, 0, 0, null);
	    g2d.dispose();
	    return img;
	}

	public static void initTextures() {
		try {
			BufferedImage image = ImageIO.read(Textures.class.getClassLoader().getResource("saved-texturess.png"));
			image = convert(image, BufferedImage.TYPE_INT_RGB);
			
			height = image.getHeight();
			width = image.getWidth();
			
			int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
			for(int y = 0; y < image.getHeight(); y++)
			{
				for(int x = 0; x < image.getWidth(); x++)
				{
					int i = x + y * image.getWidth();
					
					textureData[i] = pixels[i];
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}