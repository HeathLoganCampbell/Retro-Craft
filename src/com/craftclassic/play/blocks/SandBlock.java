package com.craftclassic.play.blocks;

import com.craftclassic.play.utils.Location;

public class SandBlock extends Block {

	public SandBlock(int id) 
	{
		super(id, "Sand", 14);
		this.setPhysics(true);
	}
}
