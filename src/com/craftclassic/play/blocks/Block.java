package com.craftclassic.play.blocks;

import java.util.HashMap;

import com.craftclassic.play.events.BreakEvent;
import com.craftclassic.play.events.PlaceEvent;
import com.craftclassic.play.utils.Location;
import com.craftclassic.play.world.World;

public class Block 
{
	private int id;
	private String name;
	private int textureId;
	private boolean breakable = true;
	private boolean physics = false;
	private boolean solid = true;
	private boolean selectable = true;
	
	public Block(int id, String name, int textureId) {
		super();
		this.setId(id);
		this.name = name;
		this.textureId = textureId;
		this.solid = true;
		idToBlocks.put(this.id, this);
	}
	
	public Block(int id, String name, int textureId, boolean solid) {
		super();
		this.setId(id);
		this.name = name;
		this.textureId = textureId;
		this.solid = solid;
		idToBlocks.put(this.id, this);
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
	
	public boolean onPlace(PlaceEvent placeEvent) { return this.solid; }
	public boolean onBreak(BreakEvent breakEvent) { return this.breakable; }
	
	public void onTick(Location loc) 
	{
		if(this.isPhysics())
		{
			World world = loc.getWorld();
			Location underOurBlockLoc = loc.clone();
			underOurBlockLoc.add(0, 1, 0);
			Block underOurBlock = world.getBlock(underOurBlockLoc);
			if(!underOurBlock.isSolid())
			{
				world.setBlock(loc, Block.AIR);
				world.setBlock(underOurBlockLoc, this);
			}
		}
	}
	public void onNeightbourUpdate(Location location) {}

	public boolean isBreakable()
	{
		return breakable;
	}

	public void setBreakable(boolean breakable) 
	{
		this.breakable = breakable;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	protected static HashMap<Integer, Block> idToBlocks = new HashMap<>();
	public static final Block AIR = new Block(0, "Air", -1, false)
							, DIRT 	= new DirtBlock(1)
							, GRASS = new GrassBlock(2)
							, STONE = new StoneBlock(3)
							, LAVA = new LavaBlock(4) //solid lava (4)
							, WATER = new WaterBlock(5)
							, BEDROCK = new BedrockBlock(6)
							, LOG = new LogBlock(7)
							, LEAVES = new LeavesBlock(8)
							, BRICK = new BrickBlock(9)
							, COBBLESTONE = new CobbleStoneBlock(10)
							, GLASS = new GlassBlock(11)
							, TNT = new TNTBlock(12)
							, SAND = new SandBlock(13)
							;
	
	public static Block getBlockById(int id)
	{
		return idToBlocks.get(id);
	}

	public boolean isPhysics() {
		return physics;
	}

	public void setPhysics(boolean physics) {
		this.physics = physics;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
}
