package com.minecraft4k.base;

import java.applet.Applet;
import java.awt.AWTException;
import java.awt.Event;
import java.awt.Robot;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Minecraft
extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;
	private int[] inputData = new int[32767];
    int[] textureData = Textures.textureData;
    World world;
    Input input = new Input();
    
    
    private int width = 0;
    private int height = 0;
    
    private int halfWidth = 0;
    private int halfHeight = 0;
    
    private int quartWidth = 0;
    private int quartHeight = 0;
    
    private int eigthWidth = 0;
    private int eigthHeight = 0;

    private Robot robot;
    
    public static final int PLAYER_HEIGHT = 12;
    
    
    BufferedImage frameBuffer = new BufferedImage(214, 120, 1);
    int[] imageData = ((DataBufferInt)frameBuffer.getRaster().getDataBuffer()).getData();

    public Minecraft(int width, int height)
    {
    	this.width = width;
    	this.height = height;
    	
    	this.halfHeight = height / 2;
    	this.halfWidth = width / 2;

    	this.quartHeight = halfHeight / 2;
    	this.quartWidth = halfWidth / 2;
    	
    	this.quartHeight = halfHeight / 2;
    	this.quartWidth = halfWidth / 2;
    	
    	this.eigthHeight = quartHeight / 2;
    	this.eigthWidth = quartWidth / 2;
    	
    	try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
    	
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
        	this.world = new World(64, 64, 1);
        	
            float playerX = 64 + 32.5f;
            float playerY = 64 + 1.0f;
            float playerZ = 64 + 32.5f;
            
            float xVelocity = 0.0f;
            float yVelocity = 0.0f;
            float zVelocity = 0.0f;
            
            long now = System.currentTimeMillis();
            int selectedBlock = -1;
            int i5 = 0;
            
            float yaw = 0.0f;
            float pitch = 5.0f;
            
            int lastMouseX = 0;
            int lastMouseY = 0;
            
            while(true)
            {
                int i11;
                float sinf7 = (float)Math.sin(yaw);
                float cosf7 = (float)Math.cos(yaw);
                float sinf8 = (float)Math.sin(pitch);
                float cosf8 = (float)Math.cos(pitch);
                
                block7 : while (System.currentTimeMillis() - now > 10) {
                   
                	
                	if (this.inputData[2] > 0) 
                	{
                    	//rotate head
                		
                		
                    	float f13 = (float)(this.inputData[2] - lastMouseX);
                        float f14 = (float)(this.inputData[3]- lastMouseY);
                        f13 *= 0.01f; //yaw senativity
                        f14 *= 0.01f; //pitch senativity
                        
                        lastMouseX = this.inputData[2];
                        lastMouseY = this.inputData[3];
                        
                        pitch += f14;
                        yaw += f13;
//                        
//                        System.out.println("Pitch: " + pitch);
//                        System.out.println("Yaw:   " + yaw);
//                        
                        if(pitch > 7.8f)
                        {
                        	pitch = 7.8f;
                        }
                        
                        if(pitch < 4.8f)
                        {
                        	pitch = 4.8f;
                        }
                    }
                	
                	
                    now += 10;
                    float f13 = (float)(this.inputData[100] - this.inputData[97]) * 0.02f;
                    float f14 = (float)(this.inputData[119] - this.inputData[115]) * 0.02f;
                    
                    xVelocity *= 0.5f;
                    yVelocity *= 0.99f;
                    zVelocity *= 0.5f;
                    
                    xVelocity += sinf7 * f14 + cosf7 * f13;
                    zVelocity += cosf7 * f14 - sinf7 * f13;
                    yVelocity += 0.003f;
                    
                    //collision code
                    
                    //x = 0, y = 1, z = 2
                    for (int rawCollAxis = 0; rawCollAxis < 3; rawCollAxis++)
                    {
                    	int collAxis = 0;
                    	if(rawCollAxis == 1) collAxis = 2;
                    	if(rawCollAxis == 2) collAxis = 1;
                    	
                        float newPlayerX = playerX;
                        float newPlayerY = playerY;
                        float newPlayerZ = playerZ;
                        
                        if(collAxis == 0)
                        {
                        	
                        	newPlayerX += xVelocity;
                        }
                        
                        if(collAxis == 1)
                        {
                        	newPlayerY += yVelocity;
                        }
                        
                        
                        if(collAxis == 2)
                        {
                        	newPlayerZ += zVelocity;
                        }
                        
                        
                        //height
                        boolean collison = false;
                        boolean collisonX = false;
                        boolean collisonY = false;
                        boolean collisonZ = false;
                        
                        for(int i12 = 0; i12 < PLAYER_HEIGHT; i12++)
                        {
                        	//x        
                            int i13 = (int)(newPlayerX + (float)(i12 & 1) * 0.6f - 0.3f) - 64;
                            //y
                            int i14 = (int)(newPlayerY + (float)((i12 >> 2) - 1) * 0.8f + 0.65f) - 64;
                            //z
                            int i15 = (int)(newPlayerZ + (float)((i12 >> 1) & 1) * 0.6f - 0.3f) - 64;
                           
                            if (i13 < 0 || i14 < 0 || i15 < 0 || i13 >= 64 || i14 >= 64 || i15 >= 64 || world.blockData[i13 + i14 * 64 + i15 * 4096] > 0)
                            {
                            	if (collAxis != 1) 
                             	{
                            		if(collAxis == 0) {
                            			collisonX = true;
                            		}
                            		
									if(collAxis == 2) {
										collisonZ = true;
                            		}
                             	} 
                            	else 
                             	{
	                               //Jumping code, if space
	                                if (this.inputData[32] > 0 && yVelocity > 0.0f)
	                                {
	                                    this.inputData[32] = 0;
	                                    yVelocity = -0.1f;
	                                } 
	                                collison = true;
	                                collisonY = true;
                             	}
                            }
                        }
                        
                        if(!collisonX) playerX = newPlayerX;
                        if(!collisonY) playerY = newPlayerY;
                        if(!collisonZ) playerZ = newPlayerZ;
                     
                        if(collison) continue block7;
                    }
                }
                int i6 = 0;
                int i7 = 0;
                if (this.inputData[1] > 0 && selectedBlock > 0)
                {
                    world.blockData[selectedBlock] = 0;
                    this.inputData[1] = 0;
                }
                if (this.inputData[0] > 0 && selectedBlock > 0)
                {
                    world.blockData[selectedBlock + i5] = 1;
                    this.inputData[0] = 0;
                }
                //delete collision
                for ( int i8 = 0; i8 < PLAYER_HEIGHT; i8++) {
                    int i9 = (int)(playerX + (float)(i8 >> 0 & 1) * 0.6f - 0.3f) - 64;
                    int i10 = (int)(playerY + (float)((i8 >> 2) - 1) * 0.8f + 0.65f) - 64;
                    i11 = (int)(playerZ + (float)(i8 >> 1 & 1) * 0.6f - 0.3f) - 64;
                    if (i9 >= 0 && i10 >= 0 && i11 >= 0 && i9 < 64 && i10 < 64 && i11 < 64) {
                        world.blockData[i9 + i10 * 64 + i11 * 4096] = 0;
                    }
                }
                float tempSelectingBlock = -1.0f;
                //render
                for (int i9 = 0; i9 < 214; i9++) {
                    float f18 = (float)(i9 - 107) / 90.0f;
                    i11 = 0;
                    while (i11 < 120) {
                        float f20 = (float)(i11 - 60) / 90.0f;
                        float f21 = 1.0f;
                        
                        //rotation matrix
                        float f22 = f21 * cosf8 + f20 * sinf8;
                        float f23 = f20 * cosf8 - f21 * sinf8;
                        float f24 = f18 * cosf7 + f22 * sinf7;
                        float f25 = f22 * cosf7 - f18 * sinf7;
                        
                        int i16 = 0;
                        int i17 = 255;
                        
                        double fogOfWar = 20.0;//render distance
                        float readDistance = 5.0f;//read distance
                        int axis = 0;//
                        while (axis < 3) {
                            float f27 = f24;
                            if (axis == 1) 
                            {
                                f27 = f23;
                            }
                            
                            if (axis == 2)
                            {
                                f27 = f25;
                            }
                            float f28 = 1.0f / Math.abs(f27);
                            float f29 = f24 * f28;
                            float f30 = f23 * f28;
                            float f31 = f25 * f28;
                            float f32 = playerX - (float)((int)playerX);
                            
                            if (axis == 1) 
                            {
                                f32 = playerY - (float)((int)playerY);
                            }
                            
                            if (axis == 2) 
                            {
                                f32 = playerZ - (float)((int)playerZ);
                            }
                            
                            if (f27 > 0.0f) 
                            {
                                f32 = 1.0f - f32;
                            }
                            float f33 = f28 * f32;
                            float f34 = playerX + f29 * f32;
                            float f35 = playerY + f30 * f32;
                            float f36 = playerZ + f31 * f32;
                            if (f27 < 0.0f) 
                            {
                                if (axis == 0) {
                                    f34 -= 1.0f;
                                }
                                if (axis == 1) {
                                    f35 -= 1.0f;
                                }
                                if (axis == 2) {
                                    f36 -= 1.0f;
                                }
                            }
                            while ((double)f33 < fogOfWar) {
                            	//render all blocks
                            	//off set
                                int i21 = (int)f34 - 64;
                                int i22 = (int)f35 - 64;
                                int i23 = (int)f36 - 64;
                                
                                //block out of mapsize
                                if (i21 < 0 || i22 < 0 || i23 < 0 || i21 >= 64 || i22 >= 64 || i23 >= 64) break;
                                
                                int i24 = i21 + i22 * 64 + i23 * 4096;
                                int i25 = world.blockData[i24];//selected block
                                if (i25 > 0) {
                                	//render horz of block
                                    i6 = (int)((f34 + f36) * 16.0f) & 15;
                                    //render vert of block
                                    i7 = ((int)(f35 * 16.0f) & 15) + 16;
                                    
                                    if (axis == 1) {
                                    	//map texture onto block
                                        i6 = (int)(f34 * 16.0f) & 15;
                                        i7 = (int)(f36 * 16.0f) & 15;
                                        if (f30 < 0.0f) {
                                            i7 += 32;
                                        }
                                    }
                                    int i26 = 16777215;
                                    //render select or just render whole block
                                    if (i24 != selectedBlock || i6 > 0 && i7 % 16 > 0 && i6 < 15 && i7 % 16 < 15) {
                                        i26 = textureData[i6 + i7 * 16 + i25 * 256 * 3];
                                    }
                                    
                                    if (f33 < readDistance && i9 == this.eigthWidth && i11 ==  this.eigthHeight) {
                                    	tempSelectingBlock = i24;
                                        i5 = 1;
                                        if (f27 > 0.0f) {
                                            i5 = -1;
                                        }
                                        i5 <<= 6 * axis;
                                        readDistance = f33;
                                    }
                                    if (i26 > 0) {
                                        i16 = i26;
                                        i17 = 255 - (int)(f33 / 20.0f * 255.0f);
                                        i17 = i17 * (255 - (axis + 2) % 3 * 50) / 255;
                                        fogOfWar = f33;
                                    }
                                }
                                f34 += f29;
                                f35 += f30;
                                f36 += f31;
                                f33 += f28;
                            }
                            ++axis;
                        }
                        int r = (i16 >> 16 & 255) * i17 / 255;
                        int g = (i16 >> 8 & 255) * i17 / 255;
                        int b = (i16 & 255) * i17 / 255;
                        imageData[i9 + i11 * 214] = r << 16 | g << 8 | b;
                        i11++;
                    }
                }
                selectedBlock = (int) tempSelectingBlock;
                
                Thread.sleep(2);
                if (!this.isActive()) 
                {
                    return;
                }
                this.getGraphics().drawImage(frameBuffer, 0, 0, 856, 480, null);
            } 
        }
        catch (Exception localException) {
            return;
        }
    }
    
    boolean isRecentering = false;
    double robotDiffX = 0;
    double robotDiffY = 0;
    
    private synchronized void recenterMouse() {
          isRecentering = true;
          robot.mouseMove(this.halfWidth, this.halfHeight);
      }
    
    @Override
    public synchronized boolean handleEvent(Event paramEvent) {
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
//                this.inputData[2] = paramEvent.x;
//                this.inputData[3] = this.height - paramEvent.y;
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
                  this.inputData[2] = (int) (paramEvent.x);
                  this.inputData[3] = (int) (this.height - paramEvent.y );
                 
                break;
            }
            case 505: {
                this.inputData[2] = 0;
            }
        }
        return true;
    }
}
