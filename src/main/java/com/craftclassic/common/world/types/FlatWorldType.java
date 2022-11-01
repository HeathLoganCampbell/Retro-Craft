package com.craftclassic.common.world.types;

import com.craftclassic.common.blocks.Block;
import com.craftclassic.common.world.IWorld;

public class FlatWorldType extends WorldType
{
    public FlatWorldType(IWorld world) {
        super(world);
    }

    @Override
    public void generateTerrian(int width, int height)
    {
        for (int x = 0; x < width; x++)
        {
            for (int z = 0; z < height; z++)
            {
//                if(this.random.nextBoolean())
//                {
//                    this.world.setBlock(x, 61, z, Block.STONE);
//                    this.world.setBlock(x, 62, z, Block.STONE);
//                }

                this.world.setBlock(x, 63, z, Block.STONE);
            }
        }
    }
}
