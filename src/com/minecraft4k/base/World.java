package com.minecraft4k.base;

import java.util.Random;

public class World {
	Random random = new Random();
	public int[] blockData = new int[262144];
	
	private int width, height;
	private long seed;
	
	public World(int width, int height, long seed)
	{
		this.width = width;
		this.height = height;
		this.seed = seed;
		init();
	}
	

	public void init() 
	{
		random.setSeed(seed);
		
		generateTerrian();
	}
	
	public void generateTerrian()
	{
		for(int z = 0; z < 64; z++ )
			for(int x = 0; x < 64; x++ )
			{
				int y = (int) (Math.sin(x / 300.0) * 20 + Math.cos(z / 252.0) * 31) + 10;
				// x = side
				// y = trapped
				// z = side
				int i = x + y * 64 + z * 64 * 64;
				if(y < 64 && y >= 0)
					blockData[i] = 1;
			}
	}
	
	public void generateQbert()
	{
		for(int z = 0; z < 64; z++ )
			for(int x = 0; x < 64; x++ )
			{
				int y = x / 2 + z / 2;
				// x = side
				// y = trapped
				// z = side
				int i = x + y * 64 + z * 64 * 64;
				if(y < 64)
					blockData[i] = 1;
			}
	}
	
	public void generateFlat()
	{
		for(int z = 0; z < 64; z++ )
			for(int y = 0; y < 64; y++ )
				for(int x = 0; x < 64; x++ )
				{
					// x = side
					// y = trapped
					// z = side
					int i = x + y * 64 + z * 64 * 64;
					if(64 - y < 45)
						blockData[i] = 1;
				}
	}
}
