package com.craftclassic.common.blocks;

import com.craftclassic.common.events.BreakEvent;
import com.craftclassic.common.utils.Location;

public class TNTBlock extends Block {
	public static final int EXPLODE_SIZE = 4;
	
	
	public TNTBlock(int id) 
	{
		super(id, "TNT", 10);
	}

	@Override
	public boolean onBreak(BreakEvent e)
	{
		Location loc = e.getLocation();
		double baseX = loc.getX();
		double baseY = loc.getY();
		double baseZ = loc.getZ();
		loc.getWorld().setBlock(loc, Block.AIR);
		for(int y = (EXPLODE_SIZE/-2); y < (EXPLODE_SIZE/2); y++)
			for(int x = (EXPLODE_SIZE/-2); x < (EXPLODE_SIZE/2); x++)
				for(int z = (EXPLODE_SIZE/-2); z < (EXPLODE_SIZE/2); z++)
				{
					Location newLocation = new Location(loc.getWorld(), (int) baseX + x, (int) baseY + y, (int) baseZ + z);
					if(newLocation.getBlock() == Block.TNT)
						newLocation.getBlock().onBreak(new BreakEvent(newLocation));
					loc.getWorld().setBlock(newLocation, Block.AIR);
				}
		return false;
	}
}
