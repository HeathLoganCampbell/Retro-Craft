package com.craftclassic.common.world.types;

import com.craftclassic.common.blocks.Block;
import com.craftclassic.common.world.IWorld;
import com.craftclassic.common.world.noise.CombinedNoise;
import com.craftclassic.common.world.noise.OctaveNoise;

public class NaturalWorldType extends WorldType
{
    private int waterLevel = 32;

    public NaturalWorldType(IWorld world) {
        super(world);
    }

    @Override
    public void generateTerrian(int height, int width)
    {
        CombinedNoise var6 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
        CombinedNoise var7 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
        OctaveNoise var8 = new OctaveNoise(this.random, 6);
        float var10 = 1.3F;

        int[] var9 = new int[width * height];

        for (int z = 0; z < width; ++z)
        {
            for (int x = 0; x < height; ++x)
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

                var9[z + x * width] = (int) var19;
            }
        }

        int[] var42 = var9;
        var7 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
        CombinedNoise var49 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));

        for (int z = 0; z < width; ++z) {
            for (int x = 0; x < height; ++x) {
                double var21 = var7.compute((double) (z << 1), (double) (x << 1)) / 8.0D;
                int var12 = var49.compute((double) (z << 1), (double) (x << 1)) > 0.0D ? 1 : 0;
                if (var21 > 2.0D) {
                    int var23 = ((var42[z + x * width] - var12) / 2 << 1) + var12;
                    var42[z + x * width] = var23;
                }
            }
        }

        OctaveNoise var53 = new OctaveNoise(this.random, 8);
        Block blockType = Block.AIR;

        for(int z = 0; z < width; ++z) {
            for(int x = 0; x < height; ++x) {
                int var12 = (int)(var53.compute((double)z, (double)x) / 24.0D) - 4;
                int baseHeight = var42[z + x * width] + waterLevel;
                int var25 = baseHeight + var12;
                var42[z + x * width] = Math.max(baseHeight, var25);
                if(var42[z + x * width] > 64 - 2) {
                    var42[z + x * width] = 64 - 2;
                }

                if(var42[z + x * width] < 1) {
                    var42[z + x * width] = 1;
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
                    {
                        world.setBlock(x, 63-y, z, blockType);
                    }
                }
            }
        }

        for(int z = 0; z < width; ++z)
        {
            for(int x = 0; x < height; ++x)
            {
                for(int y = 0; y < waterLevel; ++y)
                {
                    if (y < 64 && y > 0 && world.getBlock(x, y, z) == Block.AIR)
                    {
                        world.setBlock(x, 63-y, z, Block.WATER);
                    }
                }
            }
        }
    }
}
