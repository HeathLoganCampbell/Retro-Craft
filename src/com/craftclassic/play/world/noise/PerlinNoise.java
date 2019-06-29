package com.craftclassic.play.world.noise;

import java.util.Random;

public class PerlinNoise extends Noise 
{
	private int[] noise;

	public PerlinNoise(Random random) 
	{
		noise = new int[512];

		for (int count = 0; count < 256;  noise[count] = count++) 
		{

		}

		for (int count = 0; count < 256; count++) 
		{
			int unknown0 = random.nextInt(256 - count) + count;
			int unknown1 = noise[count];

			noise[count] = noise[unknown0];
			noise[unknown0] = unknown1;
			noise[count + 256] = noise[count];
		}

	}
	
	public PerlinNoise() 
	{
		this(new Random());
	}

	@Override
	public double compute(double x, double z) 
	{
		double newY = 0.0D;
		double newZ = z;
		double newX = x;

		int bitmaskedX = (int) Math.floor(x) & 255;
		int bitmaskedY = (int) Math.floor(z) & 255;
		int bitmaskedZ = (int) Math.floor(0.0D) & 255;

		newX -= Math.floor(newX);
		newZ -= Math.floor(newZ);
		newY = 0.0D - Math.floor(0.0D);

		double unknown6 = a(newX);
		double unknown7 = a(newZ);
		double unknown8 = a(newY);

		int unknown9 = noise[bitmaskedX] + bitmaskedY;
		int unknown10 = noise[unknown9] + bitmaskedZ;

		unknown9 = noise[unknown9 + 1] + bitmaskedZ;
		bitmaskedX = noise[bitmaskedX + 1] + bitmaskedY;
		bitmaskedY = noise[bitmaskedX] + bitmaskedZ;
		bitmaskedX = noise[bitmaskedX + 1] + bitmaskedZ;

		return lerp(unknown8,
				lerp(unknown7,
						lerp(unknown6, grad(noise[unknown10], newX, newZ, newY),
								grad(noise[bitmaskedY], newX - 1.0D, newZ, newY)),
						lerp(unknown6, grad(noise[unknown9], newX, newZ - 1.0D, newY),
								grad(noise[bitmaskedX], newX - 1.0D, newZ - 1.0D, newY))),
				lerp(unknown7,
						lerp(unknown6, grad(noise[unknown10 + 1], newX, newZ, newY - 1.0D),
								grad(noise[bitmaskedY + 1], newX - 1.0D, newZ, newY - 1.0D)),
						lerp(unknown6, grad(noise[unknown9 + 1], newX, newZ - 1.0D, newY - 1.0D),
								grad(noise[bitmaskedX + 1], newX - 1.0D, newZ - 1.0D, newY - 1.0D))));
	}


	private static double a(double unknown0) {
		return unknown0 * unknown0 * unknown0 * (unknown0 * (unknown0 * 6.0D - 15.0D) + 10.0D);
	}

	private static double lerp(double unknown0, double unknown1, double unknown2) {
		return unknown1 + unknown0 * (unknown2 - unknown1);
	}

	private static double grad(int unknown0, double unknown1, double unknown2, double unknown3) {
		double unknown4 = (unknown0 &= 15) < 8 ? unknown1 : unknown2;
		double unknown5 = unknown0 < 4 ? unknown2 : (unknown0 != 12 && unknown0 != 14 ? unknown3 : unknown1);

		return ((unknown0 & 1) == 0 ? unknown4 : -unknown4) + ((unknown0 & 2) == 0 ? unknown5 : -unknown5);
	}
}