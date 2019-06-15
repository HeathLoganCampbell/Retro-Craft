package com.minecraft4k.base;

import java.util.Random;

public class Textures 
{
	static Random random = new Random();
	public static int[] textureData = new int[12288];
	static
	{
		initTextures();
	}
	
	public static void initTextures()
    {
        for(int j = 0; j < 16; j++) {
            int k = 255 - random.nextInt(96);
            for(int m = 0; m < 48; m++) {
               for(int n = 0; n < 16; n++) {
                    int i3;
                    int i2;
                    int i1 = 9858122;
                    if (j == 4) {
                        i1 = 8355711;
                    }
                    if (j != 4 || random.nextInt(3) == 0) {
                        k = 255 - random.nextInt(96);
                    }
                    if (j == 1 && m < (n * n * 3 + n * 81 >> 2 & 3) + 18) {
                        i1 = 6990400;
                    } else if (j == 1 && m < (n * n * 3 + n * 81 >> 2 & 3) + 19) {
                        k = k * 2 / 3;
                    }
                    if (j == 7) {
                        i1 = 6771249;
                        if (n > 0 && n < 15 && (m > 0 && m < 15 || m > 32 && m < 47)) {
                            i1 = 12359778;
                            i2 = n - 7;
                            i3 = (m & 15) - 7;
                            if (i2 < 0) {
                                i2 = 1 - i2;
                            }
                            if (i3 < 0) {
                                i3 = 1 - i3;
                            }
                            if (i3 > i2) {
                                i2 = i3;
                            }
                            k = 196 - random.nextInt(32) + i2 % 3 * 32;
                        } else if (random.nextInt(2) == 0) {
                            k = k * (150 - (n & 1) * 100) / 100;
                        }
                    }
                    if (j == 5) {
                        i1 = 11876885;
                        if ((n + m / 4 * 4) % 8 == 0 || m % 4 == 0) {
                            i1 = 12365733;
                        }
                    }
                    i2 = k;
                    if (m >= 32) {
                        i2 /= 2;
                    }
                    if (j == 8) {
                        i1 = 5298487;
                        if (random.nextInt(2) == 0) {
                            i1 = 0;
                            i2 = 255;
                        }
                    }
                    textureData[n + m * 16 + j * 256 * 3] = i3 = (i1 >> 16 & 255) * i2 / 255 << 16 | (i1 >> 8 & 255) * i2 / 255 << 8 | (i1 & 255) * i2 / 255;
                }
            }
        }
    }
}
