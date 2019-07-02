package com.craftclassic.play.blocks;

import com.craftclassic.play.events.BreakEvent;
import com.craftclassic.play.utils.Location;

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
		for(int y = (EXPLODE_SIZE/-2); y < (EXPLODE_SIZE/2); y++)
			for(int x = (EXPLODE_SIZE/-2); x < (EXPLODE_SIZE/2); x++)
				for(int z = (EXPLODE_SIZE/-2); z < (EXPLODE_SIZE/2); z++)
					e.getLocation().getWorld().setBlock((int) baseX + x, (int) baseY + y, (int) baseZ + z, Block.AIR);
		return false;
	}
}
