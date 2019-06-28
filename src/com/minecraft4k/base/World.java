package com.minecraft4k.base;

import java.util.Random;

import com.minecraft4k.base.noise.CombinedNoise;
import com.minecraft4k.base.noise.OctaveNoise;

public class World {
	Random random = new Random();
	public int[] blockData = new int[262144];
	
	private int width, height;
	private int waterLevel;
	private long seed;
	
	public World(int width, int height, long seed)
	{
		this.width = width;
		this.height = height;
		this.seed = seed;
		this.waterLevel = 32;
		
		
		init();
	}
	

	public void init() 
	{
		random.setSeed(seed);
		
		generateTerrian();
	}
	
	public void generateTerrian()
	{
		CombinedNoise var6 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
	    CombinedNoise var7 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
	    OctaveNoise var8 = new OctaveNoise(this.random, 6);
	    float var10 = 1.3F;
	    
	    for(int z = 0; z < this.width; ++z) 
	    {
	         for(int x = 0; x < this.height; ++x) {
	            double var13 = var6.compute((double)((float)z * var10), (double)((float)x * var10)) / 6.0D + (double)-4;
	            double var15 = var7.compute((double)((float)z * var10), (double)((float)x * var10)) / 5.0D + 10.0D + (double)-4;
	            if(var8.compute((double)z, (double)x) / 8.0D > 0.0D) {
	               var15 = var13;
	            }

	            double var19;
	            if((var19 = Math.max(var13, var15) / 2.0D) < 0.0D) {
	               var19 *= 0.8D;
	            }

	            int y = (int) (var19);
	            int i = x + y * 64 + z * 64 * 64;
	            if(y < 64 && y >= 0)
					blockData[i] = 1;
	         }
	      }
		
//		for(int z = 0; z < 64; z++ )
//			for(int x = 0; x < 64; x++ )
//			{
//				int y = (int) (Math.sin(x / 300.0) * 20 + Math.cos(z / 252.0) * 31) + 10;
//				// x = side
//				// y = trapped
//				// z = side
//				int i = x + y * 64 + z * 64 * 64;
//				if(y < 64 && y >= 0)
//					blockData[i] = 1;
//			}
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
