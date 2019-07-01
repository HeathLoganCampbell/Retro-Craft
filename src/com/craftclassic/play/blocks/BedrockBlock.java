package com.craftclassic.play.blocks;

import com.craftclassic.play.events.BreakEvent;

public class BedrockBlock extends Block {

	public BedrockBlock(int id) 
	{
		super(id, "Bedrock", 9);
	}

	@Override
	public boolean onBreak(BreakEvent e)
	{
		return false;
	}
}
