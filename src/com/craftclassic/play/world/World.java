package com.craftclassic.play.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.craftclassic.play.blocks.Block;
import com.craftclassic.play.entities.Entity;
import com.craftclassic.play.utils.Location;
import com.craftclassic.play.world.noise.CombinedNoise;
import com.craftclassic.play.world.noise.OctaveNoise;

public class World {
	Random random = new Random();
	public Block[] blockData = new Block[262144];

	private int width, height;
	private int waterLevel;
	private long seed;
	
	private List<Entity> entities;
	
	public World(int width, int height, long seed) {
		this.width = width;
		this.height = height;
		this.seed = seed;
		this.waterLevel = 32;
		this.entities = new ArrayList<>();

		init();
	}

	public void init() {
		random.setSeed(seed);
		
		Entity entity = new Entity("Demo");
		entity.setLocation(new Location(this, 42, 20, 31));
		this.entities.add(entity);

		generateTerrian();
	}

	public List<Entity> getEntities()
	{
		return this.entities;
	}
	
	public void generateTerrian() {
		CombinedNoise var6 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
		CombinedNoise var7 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
		OctaveNoise var8 = new OctaveNoise(this.random, 6);
		float var10 = 1.3F;

		int[] var9 = new int[this.width * this.height];

		for (int z = 0; z < this.width; ++z) 
		{
			for (int x = 0; x < this.height; ++x) 
			{
				double var13 = var6.compute((double) ((float) z * var10), (double) ((float) x * var10)) / 6.0D
						+ (double) -4;
				double var15 = var7.compute((double) ((float) z * var10), (double) ((float) x * var10)) / 5.0D + 10.0D
						+ (double) -4;
				if (var8.compute((double) z, (double) x) / 8.0D > 0.0D) 
				{
					var15 = var13;
				}

				double var19;
				if ((var19 = Math.max(var13, var15) / 2.0D) < 0.0D)
				{
					var19 *= 0.8D;
				}

//				int y = (int) (var19);
//				int i = x + y * 64 + z * 64 * 64;
//				if (y < 64 && y >= 0)
//					blockData[i] = 1;
				var9[z + x * this.width] = (int) var19;
			}
		}

		int[] var42 = var9;
		var7 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
		CombinedNoise var49 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));

		for (int z = 0; z < this.width; ++z) {
			for (int x = 0; x < this.height; ++x) {
				double var21 = var7.compute((double) (z << 1), (double) (x << 1)) / 8.0D;
				int var12 = var49.compute((double) (z << 1), (double) (x << 1)) > 0.0D ? 1 : 0;
				if (var21 > 2.0D) {
					int var23 = ((var42[z + x * this.width] - var12) / 2 << 1) + var12;
					var42[z + x * this.width] = var23;
				}
			}
		}

		OctaveNoise var53 = new OctaveNoise(this.random, 8);
		Block blockType = Block.AIR;
		
		for(int z = 0; z < this.width; ++z) {
	         for(int x = 0; x < this.height; ++x) {
	            int var12 = (int)(var53.compute((double)z, (double)x) / 24.0D) - 4;
	            int baseHeight = var42[z + x * this.width] + this.waterLevel;
	            int var25 = baseHeight + var12;
	            var42[z + x * this.width] = Math.max(baseHeight, var25);
	            if(var42[z + x * this.width] > 64 - 2) {
	               var42[z + x * this.width] = 64 - 2;
	            }

	            if(var42[z + x * this.width] < 1) {
	               var42[z + x * this.width] = 1;
	            }
	            
	            for(int y = 0; y < 64; ++y) {
	               blockType = Block.AIR;
	               //grass layer
	               if(y <= baseHeight) {
	            	   blockType = Block.GRASS;
	            	   if(y == baseHeight) {
		            	   blockType = Block.DIRT;
		               }
	               }
	               
	               
	               
	               //stone layer
	               if(y <= var25) {
	            	   blockType = Block.STONE;
	               }

	               if(y == 0) {
	            	   blockType = Block.BEDROCK;
	               }

					int i = x + (63-y) * 64 + z * 64 * 64;
					if (y < 64 && y >= 0)
						blockData[i] = blockType;
	            }
	         }
	      }
	}
	
	public void setBlock(int x, int y, int z, Block block)
	{
		this.blockData[x + y * 64 + z * 4096] = block;
	}
	
	public void setBlock(Location location, Block block)
	{
		this.blockData[this.locationToWorldIndex(location)] = block;
	}
	
	public void setBlock(int indexed, Block block)
	{
		this.blockData[indexed] = block;
	}
	
	public Block getBlock(int x, int y, int z)
	{
		return this.blockData[x + y * 64 + z * 4096];
	}
	
	public Block getBlock(Location location)
	{
		return this.blockData[this.locationToWorldIndex(location)];
	}
	
	private int locationToWorldIndex(Location loc)
	{
		return ((int) loc.getX()) + ((int) loc.getY()) * 64 + ((int) loc.getZ()) * 4096;
	}
	
	public void generateQbert() {
		for (int z = 0; z < 64; z++)
			for (int x = 0; x < 64; x++) {
				int y = x / 2 + z / 2;
				// x = side
				// y = trapped
				// z = side
				int i = x + y * 64 + z * 64 * 64;
				if (y < 64)
					blockData[i] = Block.GRASS;
			}
	}

	public void generateFlat() {
		for (int z = 0; z < 64; z++)
			for (int y = 0; y < 64; y++)
				for (int x = 0; x < 64; x++) {
					// x = side
					// y = trapped
					// z = side
					int i = x + y * 64 + z * 64 * 64;
					if (64 - y < 45)
						blockData[i] = Block.GRASS;
				}
	}
}
