package com.craftclassic.play.assets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bitmap 
{
	public int[] textureData;
	public int height;
	public int width;
	
	public Bitmap(BufferedImage image)
	{
		image = convert(image, BufferedImage.TYPE_INT_RGB);
		
		this.height = image.getHeight();
		this.width = image.getWidth();
		
		this.textureData = new int[this.height * this.width];
		
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for(int y = 0; y < this.height; y++)
		{
			for(int x = 0; x < this.width; x++)
			{
				int i = x + y * this.width;
				textureData[i] = pixels[i];
			}
		}
	}
	
	public Bitmap(int width, int height, int[] textures)
	{
		this.width = width;
		this.height = height;
		this.textureData = textures;
	}
	
	public BufferedImage convert(BufferedImage src, int bufImgType) 
	{
	    BufferedImage img= new BufferedImage(src.getWidth(), src.getHeight(), bufImgType);
	    Graphics2D g2d = img.createGraphics();
	    g2d.drawImage(src, 0, 0, null);
	    g2d.dispose();
	    return img;
	}
	
	public int[] getTextures()
	{
		return this.textureData;
	}
}
