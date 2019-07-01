package com.craftclassic.play.blocks;

import com.craftclassic.play.utils.Location;

public class Block 
{
	private int id;
	private String name;
	private int textureId;
	private boolean breakable = true;
	
	public Block(int id, String name, int textureId) {
		super();
		this.id = id;
		this.name = name;
		this.textureId = textureId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTextureId() {
		return textureId;
	}
	
	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}
	
	public void onPlace(Location location) {}
	public void onBreak(Location location) {}
	public void onTick(Location location) {}

	public boolean isBreakable()
	{
		return breakable;
	}

	public void setBreakable(boolean breakable) 
	{
		this.breakable = breakable;
	}
	
	public static final Block DIRT 	= new DirtBlock(1)
							, GRASS = new GrassBlock(2)
							, STONE = new StoneBlock(3)
							, LAVA = new LavaBlock(4)
							, BEDROCK = new BedrockBlock(5)
							, LOG = new LogBlock(6)
							, LEAVES = new LeavesBlock(7)
							, BRICK = new BrickBlock(8)
							, COBBLESTONE = new CobbleStoneBlock(9)
							, GLASS = new GlassBlock(10)
							, TNT = new TNTBlock(11)
							;
}
