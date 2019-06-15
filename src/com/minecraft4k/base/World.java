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
		for (int i = 0; i < 262144; i++)
			blockData[i] = i / 64 % 64 > 32 + random.nextInt(8) ? random.nextInt(8) + 1 : 0;
	}	
}
