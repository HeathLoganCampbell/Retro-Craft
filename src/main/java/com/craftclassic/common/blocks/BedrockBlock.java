package com.craftclassic.common.blocks;

import com.craftclassic.common.events.BreakEvent;

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
