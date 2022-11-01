package com.craftclassic.play.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.craftclassic.play.Minecraft;
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
	private Minecraft minecraft;
	
	private List<Entity> entities;
	
	public World(Minecraft minecraft, int width, int height, long seed) {
		this.width = width;
		this.height = height;
		this.seed = seed;
		this.waterLevel = 32;
		this.setMinecraft(minecraft);
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
		
		for(int z = 0; z < this.width; ++z) 
		{
	         for(int x = 0; x < this.height; ++x) 
	         {
	        	 for(int y = 0; y < this.waterLevel; ++y) 
		         {
		        	 int i = x + (63-y) * 64 + z * 64 * 64;
						if (y < 64 && y > 0 && blockData[i] == Block.AIR)
							blockData[i] = Block.WATER;
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
		int index = this.locationToWorldIndex(location);
		if(location.getX() >= 0 && location.getX() <= this.width
				&& location.getY() >= 0 && location.getY() <= this.height
				&& index > 0 && index < this.blockData.length)
			this.blockData[index] = block;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setBlock(int indexed, Block block)
	{
		if(indexed > 0 && indexed < this.blockData.length)
			this.blockData[indexed] = block;
	}
	
	public Block getBlock(int x, int y, int z)
	{
		return this.blockData[x + y * 64 + z * 4096];
	}
	
	public Block getBlock(Location location)
	{
		int index = this.locationToWorldIndex(location);
		if(location.getX() >= 0 && location.getX() <= this.width
				&& location.getY() >= 0 && location.getY() <= this.height
				&& index > 0 && index < this.blockData.length)
			return this.blockData[index];
		return Block.BEDROCK;
	}
	
	private int locationToWorldIndex(Location loc)
	{
		return ((int) loc.getX()) + ((int) loc.getY()) * 64 + ((int) loc.getZ()) * 4096;
	}
	
	/**
	 * Might grow a tree in a location if there is enough space
	 * @param location
	 * @return successfully grown tree
	 */
	public boolean maybeGrowTree(Location location)
	{
		int trunk = this.random.nextInt(3) + 5;
		boolean isValid = true;
		
		//check if above is clear
		for(int y = 0; y < trunk; y++)
		{
			int size = 1;
			if(y >= 3)
			{
				size = 2;
			}
			
			for(int z = -size; z < size; z++)
			{
				for(int x = -size; x < size; x++)
				{
					if(this.getBlock((int) location.getX() + x,(int) location.getY() + y,(int) location.getZ() + z) != Block.AIR)
						isValid = false;
				}
			}
		}
		
		if(isValid) return false;
		
		for(int y = 0; y < trunk; y++)
		{
			int size = 1;
			if(y >= 3)
			{
				size = 2;
			}
			//first 2 blocks are just logs
			//next 2 are leaves
			//next 2 are leaves
			for(int z = -size; z < size; z++)
			{
				for(int x = -size; x < size; x++)
				{
					
				}
			}
		}
		
		
		return true;
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

	public Minecraft getMinecraft() {
		return minecraft;
	}

	public void setMinecraft(Minecraft minecraft) {
		this.minecraft = minecraft;
	}
}
