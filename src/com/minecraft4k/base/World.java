package com.minecraft4k.base;

import java.util.Random;

public class World {
	Random random = new Random();
	public int[] blockData = new int[262144];
	
	public World()
	{
		init();
	}
	
	public void init() 
	{
		random.setSeed(18295169);
		
		for(int z = 0; z < 64; z++ )
			for(int y = 0; y < 64; y++ )
				for(int x = 0; x < 64; x++ )
				{
					 // x = side
					// y = trapped
					// z = side
					int i = x + y * 64 + z * 64 * 64;
					if(64 - y < 4)
						blockData[i] = 1;
				}
	}	
}
