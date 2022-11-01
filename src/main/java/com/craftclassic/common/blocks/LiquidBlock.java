package com.craftclassic.common.blocks;

import com.craftclassic.common.utils.Location;

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
