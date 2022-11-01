package com.craftclassic.common.world;

import com.craftclassic.common.blocks.Block;
import com.craftclassic.common.entities.Entity;
import com.craftclassic.common.utils.Location;

import java.util.List;

public interface IWorld
{
    List<Entity> getEntities();

    void setBlock(int x, int y, int z, Block block);

    void setBlock(Location location, Block block);

    Block getBlock(int x, int y, int z);

    Block getBlock(Location location);

    long getSeed();
}
