package com.craftclassic.play.assets;

import com.craftclassic.play.Minecraft;

public class FontRender
{
	private String fontChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + //
						  	   "0123456789!?()*.^$#=+-/\\\";" + 
						  	   ":[]{}|&@";
	private int lettersOnRow = 26;
	private Minecraft minecraft;
	private int letterWidth = 5;
	private int letterHeight = 6;
	
	public FontRender(Minecraft minecraft)
	{
		this.minecraft = minecraft;
	}
	
	private void renderLetter(int screenX, int screenY, int x, int y)
	{	
		for(int iy = 0; iy < this.letterHeight; iy++)
		{
			for(int ix = 0; ix < this.letterWidth; ix++)
			{
				int letterBaseX = (x * this.letterWidth);
				int letterBaseY = (y * this.letterHeight);
				int pixel = Textures.fontTilesheet.getTextures()[(letterBaseX + ix) + ((letterBaseY + iy) * Textures.fontTilesheet.width)];
				
				if(pixel > 0)
					this.minecraft.setPixel(screenX + ix, screenY + iy, pixel);
			}
		}
	
	}
	
	public void renderString(String message, int screenX, int screenY)
	{
		message = message.toUpperCase();
		for (int i = 0; i < message.length(); i++) 
		{
			int rawIndex = fontChars.indexOf(message.charAt(i));
			int indexX = rawIndex % this.lettersOnRow;
			int indexY = rawIndex / (this.lettersOnRow);
			
			if (rawIndex >= 0)
				renderLetter(screenX + i * (this.letterWidth + 1), screenY, indexX, indexY);
		}
		
		//loop throuch each character
		//render it
	}
}
