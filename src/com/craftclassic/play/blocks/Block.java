package com.craftclassic.play.blocks;

import com.craftclassic.play.utils.Location;

public class Block 
{
	private String name;
	private int textureId;
	
	public Block(String name, int textureId) {
		super();
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
}
