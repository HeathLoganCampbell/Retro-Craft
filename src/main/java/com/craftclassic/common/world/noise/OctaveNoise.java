package com.craftclassic.common.world.noise;

import java.util.Random;

public class OctaveNoise extends Noise 
{
	private PerlinNoise[] perlin;
	private int octaves;
	
	public OctaveNoise(Random random, int octaves)
	{
		this.octaves = octaves;
		perlin = new PerlinNoise[octaves];

		for(int count = 0; count < octaves; count++)
		{
			perlin[count] = new PerlinNoise(random);
		}

	}

	@Override
	public double compute(double x, double z)
	{
		double result = 0.0D;
		double feature = 1.0D;

		for(int count = 0; count < octaves; count++)
		{
			result += perlin[count].compute(x / feature, z / feature) * feature;

			feature *= 2.0D;
		}

		return result;
	}
}
