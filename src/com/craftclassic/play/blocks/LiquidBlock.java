package com.craftclassic.play.blocks;

public class LiquidBlock extends Block 
{
	private int movingId;

	public LiquidBlock(int id, String name, int textureId) 
	{
		super(id, name, textureId, false);
		this.movingId = id + 1;
		
	}
//updateLater(Ticks, () -> {Tasks} })
}
