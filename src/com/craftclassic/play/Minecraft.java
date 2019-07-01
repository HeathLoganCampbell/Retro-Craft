package com.craftclassic.play;

import java.applet.Applet;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.craftclassic.play.assets.Textures;
import com.craftclassic.play.blocks.Block;
import com.craftclassic.play.input.Input;
import com.craftclassic.play.world.World;

public class Minecraft
extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;
//    int[] textureData = Textures.textureData;
    
    World world;
    Input input;
    
    public static float fogDistance = 50f;
    
    private int width = 0;
    private int height = 0;
    
    private int halfWidth = 0;
    private int halfHeight = 0;
    
    private int quartWidth = 0;
    private int quartHeight = 0;
    
    private int eigthWidth = 0;
    private int eigthHeight = 0;
    
    private Robot robot;
    
    private int placeBlockTypeId = 1;

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
    	
    	this.input = new Input(width, height, this.robot, this);
    	addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
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
            
            boolean onGround = false;
            boolean onJump = false;
            int jumpingTicks = -1;
            
            long now = System.currentTimeMillis();
            int selectedBlock = -1;
            int i5 = 0;
            
            float yaw = 0.0f;
            float pitch = 5.0f;
            
            int lastMouseX = 0;
            int lastMouseY = 0;
            
            int targetBlockX = 0;
            int targetBlockY = 0;
            int targetBlockZ = 0;
            
            while(true)
            {
                float sinf7 = (float)Math.sin(yaw);
                float cosf7 = (float)Math.cos(yaw);
                float sinf8 = (float)Math.sin(pitch);
                float cosf8 = (float)Math.cos(pitch);
                
                block7 : while (System.currentTimeMillis() - now > 10) {
                   
                	
                	if (this.input.getMouseX() > 0) 
                	{
                    	//rotate head
                    	float inMouseX = (float)(this.input.getMouseX() - lastMouseX);
                        float inMouseY = (float)(this.input.getMouseY() - lastMouseY);
                        
                        inMouseX *= 0.01f; //yaw senativity
                        inMouseY *= 0.01f; //pitch senativity
                        
                        
                        lastMouseX = this.input.getMouseX();
                        lastMouseY = this.input.getMouseY();
                        
                        
                        
                        pitch += inMouseY;
                        yaw += inMouseX;

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
                    float accX = (float)((this.input.getKey(KeyEvent.VK_D) ? 1 : 0) - (this.input.getKey(KeyEvent.VK_A) ? 1 : 0)) * 0.02f;
                    float accZ = (float)((this.input.getKey(KeyEvent.VK_W) ? 1 : 0) - (this.input.getKey(KeyEvent.VK_S) ? 1 : 0)) * 0.02f;
                    
                    xVelocity *= 0.5f;
                    yVelocity *= 0.99f;
                    zVelocity *= 0.5f;
                    
                    xVelocity += sinf7 * accZ + cosf7 * accX;
                    zVelocity += cosf7 * accZ - sinf7 * accX;
                    yVelocity += 0.003f;
                    
                    
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
                           
                            if (i13 < 0 || i14 < 0 || i15 < 0 || i13 >= 64 || i14 >= 64 || i15 >= 64 || world.blockData[i13 + i14 * 64 + i15 * 4096] != Block.AIR)
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
//                            		yVelocity = 0.00f;
                            		
	                                if (this.input.getKey(KeyEvent.VK_SPACE))
	                                {
	                                	this.input.setKey(KeyEvent.VK_SPACE, false);
	                                    yVelocity = -0.1f;
	                                    onJump = true;
	                                    jumpingTicks = 0;
	                                    onGround = false;
	                                    
	                                } 
	                                collison = true;
	                                collisonY = true;
                             	}
                            }
                        }
                        
                        if(!collisonX) playerX = newPlayerX;
                        if(!collisonY) 
                    	{
                    		playerY = newPlayerY;
                    		onGround = false;
                    	}
                        else
                        {
                        	if(jumpingTicks > 0)
                        	{
                        		jumpingTicks = -1;
                        		onJump = false;
                        	}
                    		onGround = true;
                    		if(onJump) jumpingTicks++;
                    		if(!onJump && onGround) yVelocity = 0;
                        }
                        if(!collisonZ) playerZ = newPlayerZ;
                     
                        if(collison) continue block7;
                    }
                }
                
                int textureX = 0;
                int textureY = 0;
                if (this.input.getMouse(MouseEvent.BUTTON1) && selectedBlock > 0)
                {
                    world.blockData[selectedBlock] = Block.AIR;
                    this.input.setMouse(MouseEvent.BUTTON1, false);
                }
                if (this.input.getMouse(MouseEvent.BUTTON3) && selectedBlock > 0)
                {
                    world.blockData[selectedBlock + i5] = Block.getBlockById(this.getPlaceBlockTypeId());
                    this.input.setMouse(MouseEvent.BUTTON3, false);
                }
               
                
                //delete collision, if a player tries to place a block on themselfs
                for ( int i8 = 0; i8 < PLAYER_HEIGHT; i8++) 
                {
                	int i9 = (int)(playerX + (float)(i8 >> 0 & 1) * 0.6f - 0.3f) - 64;
                    int i10 = (int)(playerY + (float)((i8 >> 2) - 1) * 0.8f + 0.65f) - 64;
                    int i11A = (int)(playerZ + (float)(i8 >> 1 & 1) * 0.6f - 0.3f) - 64;
                    
                    if (i9 >= 0 && i10 >= 0 && i11A >= 0 && i9 < 64 && i10 < 64 && i11A < 64) 
                    {
                        world.blockData[i9 + i10 * 64 + i11A * 4096] = Block.AIR;
                    }
                }
                
                float tempSelectingBlock = -1.0f;
                //render
                //vertizle lines
                for (int vertIndex = 0; vertIndex < 214; vertIndex++) {
                    float f18 = (float)(vertIndex - 107) / 90.0f;
                    
                    //number of lines to render, horzontial
                    for(int hortIndex = 0; hortIndex < 120; hortIndex++) {
                        float f20 = (float)(hortIndex - 60) / 90.0f;
                        float fov = 1f; //fov, less than 1 makes it look faster
                        
                        //rotation matrix
                        float f22 = fov * cosf8 + f20 * sinf8;
                        float rotatedY = f20 * cosf8 - fov * sinf8;
                        
                        float rotatedX = f18 * cosf7 + f22 * sinf7;
                        float rotatedZ = f22 * cosf7 - f18 * sinf7;
                        
                        int skyboxColour = 0xA7C9EB;
                        int maxSkyboxColour = 255;
                        
                        double fogOfWar = 50.0;//render distance
                        float readDistance = 5.0f;//read distance
                        int axis = 0;//
                        while (axis < 3) 
                        {
                            float f27 = rotatedX;
                            if (axis == 1) //y
                                f27 = rotatedY;
                            
                            if (axis == 2) //z
                                f27 = rotatedZ;
                            
                            float invrtRotatedAxis = 1.0f / Math.abs(f27);
                            float rotXInvrt = rotatedX * invrtRotatedAxis;
                            float rotYInvrt = rotatedY * invrtRotatedAxis;
                            float rotZInvert = rotatedZ * invrtRotatedAxis;
                            float decAxis = playerX - (float)((int)playerX);
                            
                            if (axis == 1) 
                            {
                                decAxis = playerY - (float)((int)playerY);
                            }
                            
                            if (axis == 2) 
                            {
                                decAxis = playerZ - (float)((int)playerZ);
                            }
                            
                            if (f27 > 0.0f) 
                            {
                                decAxis = 1.0f - decAxis;
                            }
                            float f33 = invrtRotatedAxis * decAxis;
                            
                            //block faces placement
                            float faceX = playerX + rotXInvrt * decAxis;
                            float faceY = playerY + rotYInvrt * decAxis;
                            float faceZ = playerZ + rotZInvert * decAxis;

                            if (f27 < 0.0f) 
                            {
                            	//block faces
                                if (axis == 0)
                                {
                                	//back face
                                    faceX -= 1.0f;
                                }
                                
                                if (axis == 1)
                                {
                                	//side face
                                    faceY -= 1.0f;
                                }
                                
                                if (axis == 2) 
                                {
                                	//bottom face
                                    faceZ -= 1.0f;
                                }
                            }
                            
                            while ((double)f33 < fogOfWar) 
                            {
                            	//render all blocks
                            	//off set so we don't have divide zero errors
                                int blockX = (int)faceX - 64;
                                int blockY = (int)faceY - 64;
                                int blockZ = (int)faceZ - 64;
                                
                                //block out of mapsize
                                if (blockX < 0 || blockY < 0 || blockZ < 0 || blockX >= 64 || blockY >= 64 || blockZ >= 64) 
                                	break;
                                
                                //get block, then get pixel in block
                                int blockInde = blockX + blockY * 64 + blockZ * 4096;
                                Block block = world.blockData[blockInde];//selected block
                                
                                if (block != Block.AIR) 
                                {//not air
                                	//render horz of block
                                    textureX = (int)((faceX + faceZ) * 16.0f) & 15;
                                    //render vert of block
                                    textureY = ((int)(faceY * 16.0f) & 15) + 16;
                                    
                                    //if y, render bottom face of block differently
                                    if (axis == 1) 
                                    {
                                    	//map texture onto block
                                        textureX = (int)(faceX * 16.0f) & 15;
                                        textureY = (int)(faceZ * 16.0f) & 15;
                                        //map bottom of block render as bottom,
                                        if (rotYInvrt < 0.0f) 
                                        {
                                            textureY += 32;
                                        }
                                    }
                                    
                                    int colorOfOutline = 0xffff00;//default colour of block
                                  
                                   
                                    //render select or just render whole block
                                    if (blockInde != selectedBlock || textureX > 0 && textureY % 16 > 0 && textureX < 15 && textureY % 16 < 15)
                                        colorOfOutline = Textures.blockTextures.getTextures()[textureX + textureY * Textures.blockTextures.width + block.getTextureId() * Textures.blockTextures.height];
                                    
                                    //target block in middle of screen
                                    if (f33 < readDistance && vertIndex == this.eigthWidth && hortIndex ==  this.eigthHeight) 
                                    {
                                    	tempSelectingBlock = blockInde;
                                    	if(this.input.getKey(KeyEvent.VK_M))
                                    	{
                                    		targetBlockX = blockX;
                                            targetBlockY = blockY;
                                            targetBlockZ = blockZ;
                                    		System.out.println("Looking at: (" + targetBlockX + ", " + targetBlockY + ", " + targetBlockZ + ")" );
                                    		
                                    	}
                                        i5 = 1;
                                        if (f27 > 0.0f)
                                        {
                                            i5 = -1;
                                        }
                                        i5 <<= 6 * axis;
                                        readDistance = f33;
                                    }
                                    
                                    //Add fog to each pixel
                                    if (colorOfOutline > 0x000000)//Don't render black textures 
                                    {
                                        skyboxColour = colorOfOutline;
                                        //50 = FOGGINESS
                                        maxSkyboxColour = 255 - (int)(f33 / fogDistance * 255.0f);
                                        maxSkyboxColour = maxSkyboxColour * (255 - (axis + 2) % 3 * 50) / 255;
                                        fogOfWar = f33;
                                    }
                                }
                                
                                
//                                int playerInde = blockX + blockY * 64 + blockZ * 4096;
//                                int playerId = world.blockData[blockInde];//selected block
//                                
//                                if (blockId > 0) 
//                                {//not air
//                                	//render horz of block
//                                    textureX = (int)((f34 + f36) * 16.0f) & 15;
//                                    //render vert of block
//                                    textureY = ((int)(f35 * 16.0f) & 15) + 16;
//                                    
//                                    //if y, render bottom face of block differently
//                                    if (axis == 1) 
//                                    {
//                                    	//map texture onto block
//                                        textureX = (int)(f34 * 16.0f) & 15;
//                                        textureY = (int)(f36 * 16.0f) & 15;
//                                        //map bottom of block render as bottom,
//                                        if (rotYInvrt < 0.0f) 
//                                        {
//                                            textureY += 32;
//                                        }
//                                    }
//                                    
//                                    int colorOfOutline = 0xffff00;//default colour of block
//                                   
//                                    //render select or just render whole block
//                                    if (blockInde != selectedBlock || textureX > 0 && textureY % 16 > 0 && textureX < 15 && textureY % 16 < 15)
//                                        colorOfOutline = Textures.blockTextures.getTextures()[textureX + textureY * Textures.blockTextures.width + blockId * Textures.blockTextures.height];
//                                    
//                                    //target block in middle of screen
//                                    if (f33 < readDistance && vertIndex == this.eigthWidth && hortIndex ==  this.eigthHeight) 
//                                    {
//                                    	tempSelectingBlock = blockInde;
//                                    	if(this.input.getKey(KeyEvent.VK_M))
//                                    	{
//                                    		targetBlockX = blockX;
//                                            targetBlockY = blockY;
//                                            targetBlockZ = blockZ;
//                                    		System.out.println("Looking at: (" + targetBlockX + ", " + targetBlockY + ", " + targetBlockZ + ")" );
//                                    		
//                                    	}
//                                        i5 = 1;
//                                        if (f27 > 0.0f)
//                                        {
//                                            i5 = -1;
//                                        }
//                                        i5 <<= 6 * axis;
//                                        readDistance = f33;
//                                    }
//                                    
//                                    if (colorOfOutline > 0) 
//                                    {
//                                        skyboxColour = colorOfOutline;
//                                        //50 = FOGGINESS
//                                        maxSkyboxColour = 255 - (int)(f33 / fogDistance * 255.0f);
//                                        maxSkyboxColour = maxSkyboxColour * (255 - (axis + 2) % 3 * 50) / 255;
//                                        fogOfWar = f33;
//                                    }
//                                }
                                faceX += rotXInvrt;
                                faceY += rotYInvrt;
                                faceZ += rotZInvert;
                                f33 += invrtRotatedAxis;
                            }
                            ++axis;
                        }
                        
                        int r = (skyboxColour >> 16 & 255) * maxSkyboxColour / 255;
                        int g = (skyboxColour >> 8 & 255) * maxSkyboxColour / 255;
                        int b = (skyboxColour & 255) * maxSkyboxColour / 255;
                        imageData[vertIndex + hortIndex * 214] = r << 16 | g << 8 | b;
                    }
                }
                selectedBlock = (int) tempSelectingBlock;
                
                Thread.sleep(2);
                if (!this.isActive()) 
                {
                    return;
                }
                this.getGraphics().drawImage(this.frameBuffer, 0, 0, this.width, this.height, null);
            } 
        }
        catch (Exception localException) {
            return;
        }
    }

	public int getPlaceBlockTypeId() {
		return placeBlockTypeId;
	}

	public void setPlaceBlockTypeId(int placeBlockTypeId) {
		this.placeBlockTypeId = placeBlockTypeId;
	}
}
