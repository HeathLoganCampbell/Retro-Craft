package com.craftclassic.common.world.types;

import com.craftclassic.common.world.IWorld;

import java.util.Random;

public abstract class WorldType
{
    public Random random;

    protected IWorld world;

    public WorldType(IWorld world) {
        this.random = new Random(world.getSeed());
        this.world = world;
    }

    public abstract void generateTerrian(int width, int height);
}
