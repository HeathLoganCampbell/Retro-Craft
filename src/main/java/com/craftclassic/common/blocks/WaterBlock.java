package com.craftclassic.common.blocks;

import com.craftclassic.common.utils.Location;

public class WaterBlock extends Block {

	public WaterBlock(int id) 
	{
		super(id, "Water", 13);
		this.setBreakable(false);
		this.setSelectable(false);
		this.setSolid(false);
	}
	
	private void flow(Location location, int x, int y, int z)
	{
		location.add(x, y, z);
		if(location.getBlock() == AIR)
		{
			Location newLoc = location.clone();
			location.getWorld().getMinecraft().doNextTickWater(() -> 
			{
				if(newLoc.getBlock() == AIR)
					newLoc.setBlock(WATER);
			});
		}
		location.subtract(x, y, z);
	}

	public void onTick(Location location) 
	{
		flow(location, 1, 0, 0);
		flow(location, 0, 0, 1);
		flow(location, 0, 0, -1);
		flow(location, -1, 0, 0);
		flow(location, 0, 1, 0);
	}
}
