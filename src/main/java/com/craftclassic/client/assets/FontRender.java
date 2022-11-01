package com.craftclassic.client.assets;

import com.craftclassic.client.graphics.Screen;

public class FontRender
{
	private String fontChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + //
						  	   "0123456789!?()*.^$#=+-/\\\";" + 
						  	   ":[]{}|&@";
	private int lettersOnRow = 26;

	private Screen screen;
	public int letterWidth = 5;
	public int letterHeight = 6;
	
	public FontRender(Screen screen)
	{
		this.screen = screen;
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
					this.screen.setPixel(screenX + ix, screenY + iy, pixel);
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
