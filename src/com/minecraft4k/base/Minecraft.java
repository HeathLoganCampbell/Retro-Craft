package com.minecraft4k.base;

import java.applet.Applet;
import java.awt.Event;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Minecraft
extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;
	private int[] inputData = new int[32767];
    int[] textureData = Textures.textureData;
    World world = new World();
    Input input = new Input();
    
    
    BufferedImage frameBuffer = new BufferedImage(214, 120, 1);
    int[] imageData = ((DataBufferInt)frameBuffer.getRaster().getDataBuffer()).getData();

    public Minecraft()
    {
//    	addKeyListener(input);
//		addFocusListener(input);
//		addMouseListener(input);
//		addMouseMotionListener(input);
    }
    
    @Override
    public void start() 
    {
        new Thread(this).start();
    }

    @Override
    public void run() 
    {
        try 
        {                             
            float f1 = 64 + 32.5f;
            float f2 = 65.0f;
            float f3 = 96.5f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            long now = System.currentTimeMillis();
            int i4 = -1;
            int i5 = 0;
            float f7 = 0.0f;
            float f8 = 0.0f;
            while(true)
            {
                int i11;
                float sinf7 = (float)Math.sin(f7);
                float cosf7 = (float)Math.cos(f7);
                float f11 = (float)Math.sin(f8);
                float f12 = (float)Math.cos(f8);
                block7 : while (System.currentTimeMillis() - now > 10) {
                    float f13;
                    float f14;
                    if (this.inputData[2] > 0) {
                        f13 = (float)(this.inputData[2] - 428) / 214.0f * 2.0f;
                        f14 = (float)(this.inputData[3] - 240) / 120.0f * 2.0f;
                        float f15 = (float)Math.sqrt(f13 * f13 + f14 * f14) - 1.2f;
                        if (f15 < 0.0f) {
                            f15 = 0.0f;
                        }
                        if (f15 > 0.0f) {
                            f7 += f13 * f15 / 400.0f;
                            if ((f8 -= f14 * f15 / 400.0f) < -1.57f) {
                                f8 = -1.57f;
                            }
                            if (f8 > 1.57f) {
                                f8 = 1.57f;
                            }
                        }
                    }
                    now += 10;
                    f13 = 0.0f;
                    f14 = 0.0f;
                    f4 *= 0.5f;
                    f5 *= 0.99f;
                    f6 *= 0.5f;
                    f4 += sinf7 * (f14 += (float)(this.inputData[119] - this.inputData[115]) * 0.02f) + cosf7 * (f13 += (float)(this.inputData[100] - this.inputData[97]) * 0.02f);
                    f6 += cosf7 * f14 - sinf7 * f13;
                    f5 += 0.003f;
                    int i8 = 0;
                    while (i8 < 3) {
                        float f16 = f1 + f4 * (float)((i8 + 2) % 3 / 2);
                        float f17 = f2 + f5 * (float)((i8 + 1) % 3 / 2);
                        float f19 = f3 + f6 * (float)((i8 + 2) % 3 / 2);
                        int i12 = 0;
                        while (i12 < 12) {
                            int i13 = (int)(f16 + (float)(i12 >> 0 & 1) * 0.6f - 0.3f) - 64;
                            int i14 = (int)(f17 + (float)((i12 >> 2) - 1) * 0.8f + 0.65f) - 64;
                            int i15 = (int)(f19 + (float)(i12 >> 1 & 1) * 0.6f - 0.3f) - 64;
                            if (i13 < 0 || i14 < 0 || i15 < 0 || i13 >= 64 || i14 >= 64 || i15 >= 64 || world.blockData[i13 + i14 * 64 + i15 * 4096] > 0) {
                                if (i8 != 1) continue block7;
                                if (this.inputData[32] > 0 && f5 > 0.0f) {
                                    this.inputData[32] = 0;
                                    f5 = -0.1f;
                                    continue block7;
                                }
                                f5 = 0.0f;
                                continue block7;
                            }
                            ++i12;
                        }
                        f1 = f16;
                        f2 = f17;
                        f3 = f19;
                        ++i8;
                    }
                }
                int i6 = 0;
                int i7 = 0;
                if (this.inputData[1] > 0 && i4 > 0) {
                    world.blockData[i4] = 0;
                    this.inputData[1] = 0;
                }
                if (this.inputData[0] > 0 && i4 > 0) {
                    world.blockData[i4 + i5] = 1;
                    this.inputData[0] = 0;
                }
                int i8 = 0;
                while (i8 < 12) {
                    int i9 = (int)(f1 + (float)(i8 >> 0 & 1) * 0.6f - 0.3f) - 64;
                    int i10 = (int)(f2 + (float)((i8 >> 2) - 1) * 0.8f + 0.65f) - 64;
                    i11 = (int)(f3 + (float)(i8 >> 1 & 1) * 0.6f - 0.3f) - 64;
                    if (i9 >= 0 && i10 >= 0 && i11 >= 0 && i9 < 64 && i10 < 64 && i11 < 64) {
                        world.blockData[i9 + i10 * 64 + i11 * 4096] = 0;
                    }
                    ++i8;
                }
                float i82 = -1.0f;
                int i9 = 0;
                while (i9 < 214) {
                    float f18 = (float)(i9 - 107) / 90.0f;
                    i11 = 0;
                    while (i11 < 120) {
                        float f20 = (float)(i11 - 60) / 90.0f;
                        float f21 = 1.0f;
                        float f22 = f21 * f12 + f20 * f11;//dot product
                        float f23 = f20 * f12 - f21 * f11;
                        float f24 = f18 * cosf7 + f22 * sinf7;
                        float f25 = f22 * cosf7 - f18 * sinf7;
                        int i16 = 0;
                        int i17 = 255;
                        double d = 20.0;
                        float f26 = 5.0f;
                        int i18 = 0;
                        while (i18 < 3) {
                            float f27 = f24;
                            if (i18 == 1) {
                                f27 = f23;
                            }
                            if (i18 == 2) {
                                f27 = f25;
                            }
                            float f28 = 1.0f / (f27 < 0.0f ? - f27 : f27);
                            float f29 = f24 * f28;
                            float f30 = f23 * f28;
                            float f31 = f25 * f28;
                            float f32 = f1 - (float)((int)f1);
                            if (i18 == 1) {
                                f32 = f2 - (float)((int)f2);
                            }
                            if (i18 == 2) {
                                f32 = f3 - (float)((int)f3);
                            }
                            if (f27 > 0.0f) {
                                f32 = 1.0f - f32;
                            }
                            float f33 = f28 * f32;
                            float f34 = f1 + f29 * f32;
                            float f35 = f2 + f30 * f32;
                            float f36 = f3 + f31 * f32;
                            if (f27 < 0.0f) 
                            {
                                if (i18 == 0) {
                                    f34 -= 1.0f;
                                }
                                if (i18 == 1) {
                                    f35 -= 1.0f;
                                }
                                if (i18 == 2) {
                                    f36 -= 1.0f;
                                }
                            }
                            while ((double)f33 < d) {
                                int i21 = (int)f34 - 64;
                                int i22 = (int)f35 - 64;
                                int i23 = (int)f36 - 64;
                                if (i21 < 0 || i22 < 0 || i23 < 0 || i21 >= 64 || i22 >= 64 || i23 >= 64) break;
                                int i24 = i21 + i22 * 64 + i23 * 4096;
                                int i25 = world.blockData[i24];
                                if (i25 > 0) {
                                    i6 = (int)((f34 + f36) * 16.0f) & 15;
                                    i7 = ((int)(f35 * 16.0f) & 15) + 16;
                                    if (i18 == 1) {
                                        i6 = (int)(f34 * 16.0f) & 15;
                                        i7 = (int)(f36 * 16.0f) & 15;
                                        if (f30 < 0.0f) {
                                            i7 += 32;
                                        }
                                    }
                                    int i26 = 16777215;
                                    if (i24 != i4 || i6 > 0 && i7 % 16 > 0 && i6 < 15 && i7 % 16 < 15) {
                                        i26 = textureData[i6 + i7 * 16 + i25 * 256 * 3];
                                    }
                                    if (f33 < f26 && i9 == this.inputData[2] / 4 && i11 == this.inputData[3] / 4) {
                                        i82 = i24;
                                        i5 = 1;
                                        if (f27 > 0.0f) {
                                            i5 = -1;
                                        }
                                        i5 <<= 6 * i18;
                                        f26 = f33;
                                    }
                                    if (i26 > 0) {
                                        i16 = i26;
                                        i17 = 255 - (int)(f33 / 20.0f * 255.0f);
                                        i17 = i17 * (255 - (i18 + 2) % 3 * 50) / 255;
                                        d = f33;
                                    }
                                }
                                f34 += f29;
                                f35 += f30;
                                f36 += f31;
                                f33 += f28;
                            }
                            ++i18;
                        }
                        int r = (i16 >> 16 & 255) * i17 / 255;
                        int g = (i16 >> 8 & 255) * i17 / 255;
                        int b = (i16 & 255) * i17 / 255;
                        imageData[i9 + i11 * 214] = r << 16 | g << 8 | b;
                        i11++;
                    }
                    i9++;
                }
                i4 = (int)i82;
                Thread.sleep(2);
                if (!this.isActive()) {
                    return;
                }
                this.getGraphics().drawImage(frameBuffer, 0, 0, 856, 480, null);
            } 
        }
        catch (Exception localException) {
            return;
        }
    }

    @Override
    public boolean handleEvent(Event paramEvent) {
        int i = 0;
        switch (paramEvent.id) {
            case 401: {
                i = 1;
            }
            case 402: {
            	//movement
                this.inputData[paramEvent.key] = i;
                break;
            }
            case 501: {
                i = 1;
                //click location
                this.inputData[2] = paramEvent.x;
                this.inputData[3] = paramEvent.y;
            }
            case 502: {
            
                if ((paramEvent.modifiers & 4) > 0) {
                	//place
                    this.inputData[0] = i;
                    break;
                }
            	//break
                this.inputData[1] = i;
                break;
            }
            case 503: 
            case 506: {
            	//mouse
                this.inputData[2] = paramEvent.x;
                this.inputData[3] = paramEvent.y;
                break;
            }
            case 505: {
                this.inputData[2] = 0;
            }
        }
        return true;
    }
}
