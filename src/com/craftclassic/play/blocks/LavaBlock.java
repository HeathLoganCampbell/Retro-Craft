package com.craftclassic.play.blocks;

import com.craftclassic.play.utils.Location;

public class LavaBlock extends Block {

	public LavaBlock(int id) 
	{
		super(id, "Lava", 12);
	}
	
	private void flow(Location location, int x, int y, int z)
	{
		location.add(x, y, z);
		if(location.getBlock() == Block.AIR)
		{
			Location newLoc = location.clone();
			location.getWorld().getMinecraft().doNextTick(() -> 
			{
				if(newLoc.getBlock() == Block.AIR)
					newLoc.setBlock(Block.LAVA);
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
