package com.craftclassic.play.blocks;

import com.craftclassic.play.utils.Location;

public class LiquidBlock extends Block 
{
	public LiquidBlock(int id, String name, int textureId) 
	{
		super(id, name, textureId, false);
	}
	
	public void onTick(Location loc) 
	{
		
	}
	
	//scheduleLater(() -> {Tasks}, ticks)
	//scheduleRepeat(() -> {task}, ticks)
}
