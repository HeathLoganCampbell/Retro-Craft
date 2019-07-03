package com.craftclassic.play;

import java.applet.Applet;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import com.craftclassic.play.assets.FontRender;
import com.craftclassic.play.assets.Textures;
import com.craftclassic.play.blocks.Block;
import com.craftclassic.play.entities.Player;
import com.craftclassic.play.events.BreakEvent;
import com.craftclassic.play.events.PlaceEvent;
import com.craftclassic.play.input.Input;
import com.craftclassic.play.utils.Location;
import com.craftclassic.play.utils.Vector;
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
    
    private int fps = 0;
    private long fpsTimestamp = 0;
    
    public Player player;
    
    private Robot robot;
    private FontRender font;
    
    private List<Runnable> nextTickRunnables;
    private List<Runnable> nextTickRunnablesWater;
    
    private int placeBlockTypeId = 1;

    public static final int PLAYER_HEIGHT = 12;
    
    
    BufferedImage frameBuffer = new BufferedImage(214, 120, BufferedImage.TYPE_INT_RGB);
    int[] imageData = ((DataBufferInt)frameBuffer.getRaster().getDataBuffer()).getData();

    public Minecraft(int width, int height)
    {
    	this.width = width;
    	this.height = height;
    	
    	this.halfHeight = height / 2;
    	this.halfWidth = width / 2;

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
        	this.nextTickRunnables = new ArrayList<>();
        	this.nextTickRunnablesWater = new ArrayList<>();
        	this.world = new World(this, 64, 64, 1);
        	this.font = new FontRender(this);
        	
        	this.player = new Player("Player1");
        	this.player.setLocation(new Location(this.world, 64 + 32.5f, 64 + 1.0f, 64 + 32.5f));
        	this.player.setVelocity(new Vector(0f, 0f, 0f));
            
            long now = System.currentTimeMillis();
            int selectedBlock = -1;
            int i5 = 0;
            int targetBlockX = 0;
            int targetBlockY = 0;
            int targetBlockZ = 0;
            int ticks = 0;
            
            while(true)
            {
            	if(ticks % 100 == 0)
            	{
	            	this.nextTickRunnables.forEach(task -> {
	            		task.run();
	            	});
	            	this.nextTickRunnables.clear();
            	}
            	
            	if(ticks % 10 == 0)
            	{
	            	this.nextTickRunnablesWater.forEach(task -> {
	            		task.run();
	            	});
	            	this.nextTickRunnablesWater.clear();
            	}
            	
            	
            	
            	Location playerLoc = this.player.getLocation();
                float sinYaw = (float)Math.sin(playerLoc.getYaw() + (this.player.preyaw - playerLoc.getYaw()));
                float cosYaw = (float)Math.cos(playerLoc.getYaw() + (this.player.preyaw - playerLoc.getYaw()));
                float sinPitch = (float)Math.sin(playerLoc.getPitch() + (this.player.prepitch - playerLoc.getPitch()));
                float cosPitch = (float)Math.cos(playerLoc.getPitch() + (this.player.prepitch - playerLoc.getPitch()));
                
                block7 : while (System.currentTimeMillis() - now > 10) {
                   
                	
//                	if (this.input.getMouseX() > 0) 
//                	{
//                    	//rotate head
////                    	float inMouseX = (float)(this.input.getMouseX() - lastMouseX);
////                        float inMouseY = (float)(this.input.getMouseY() - lastMouseY);
////                        
////                        inMouseX *= 0.005f; //yaw senativity
////                        inMouseY *= 0.005f; //pitch senativity
////                        
////                        
////                        lastMouseX = this.input.getMouseX();
////                        lastMouseY = this.input.getMouseY();
//                        
//                        
//                        
////                        pitch += inMouseY;
////                        yaw += inMouseX;
//                        
//
//                        if(pitch > 7.8f)
//                        {
//                        	pitch = 7.8f;
//                        }
//                        
//                        if(pitch < 4.8f)
//                        {
//                        	pitch = 4.8f;
//                        }
//                    }
//                	
                	
                    now += 10;
                    float accX = (float)((this.input.getKey(KeyEvent.VK_D) ? 1 : 0) - (this.input.getKey(KeyEvent.VK_A) ? 1 : 0)) * 0.02f;
                    float accZ = (float)((this.input.getKey(KeyEvent.VK_W) ? 1 : 0) - (this.input.getKey(KeyEvent.VK_S) ? 1 : 0)) * 0.02f;
                    
                    this.player.getVelocity().scalar(0.5f, 0.99f, 0.5f);
                    
                    
                    float xVel = sinYaw * accZ + cosYaw * accX;
                    float zVel = cosYaw * accZ - sinYaw * accX;
                    float yVel = 0.003f;
                    
                    this.player.getVelocity().add(new Vector(xVel, yVel, zVel));
                    
                    
                    //x = 0, y = 1, z = 2
                    for (int rawCollAxis = 0; rawCollAxis < 3; rawCollAxis++)
                    {
                    	int collAxis = 0;
                    	if(rawCollAxis == 1) collAxis = 2;
                    	if(rawCollAxis == 2) collAxis = 1;
                    	
                        float newPlayerX = playerLoc.getX();
                        float newPlayerY = playerLoc.getY();
                        float newPlayerZ = playerLoc.getZ();
                        
                        if(collAxis == 0)
                        {
                        	
                        	newPlayerX += this.player.getVelocity().getX();
                        }
                        
                        if(collAxis == 1)
                        {
                        	newPlayerY += this.player.getVelocity().getY();
                        }
                        
                        
                        if(collAxis == 2)
                        {
                        	newPlayerZ += this.player.getVelocity().getZ();
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
	                                if (this.input.getKey(KeyEvent.VK_SPACE))
	                                {
	                                	this.input.setKey(KeyEvent.VK_SPACE, false);
	                                	this.player.getVelocity().setY(-0.1f);
	                                    this.player.setOnJump(true);
	                                    this.player.setJumpingTicks(0);
	                                    this.player.setOnGround(false);
	                                    
	                                } 
	                                collison = true;
	                                collisonY = true;
                             	}
                            }
                        }
                        
                        if(!collisonX) playerLoc.setX(newPlayerX);
                        if(!collisonY) 
                    	{
                        	playerLoc.setY(newPlayerY);
                        	this.player.setOnGround(false);
                    	}
                        else
                        {
                        	if(this.player.getJumpingTicks() > 0)
                        	{
                        		this.player.setJumpingTicks(-1);
                        		this.player.setOnJump(false);
                        	}
                        	this.player.setOnGround(true);
                    		if(this.player.isOnJump()) this.player.incrementJumpingTicks();
                    		if(!this.player.isOnJump() && this.player.isOnGround()) this.player.getVelocity().setY(0.0f);
                        }
                        if(!collisonZ) playerLoc.setZ(newPlayerZ);
                     
                        if(collison) continue block7;
                    }
                }
                
                int textureX = 0;
                int textureY = 0;
                if (this.input.getMouse(MouseEvent.BUTTON1) && selectedBlock > 0)
                {
                	if(world.blockData[selectedBlock].onBreak(new BreakEvent(new Location(this.world, targetBlockX, targetBlockY, targetBlockZ))))
                		this.world.setBlock(selectedBlock, Block.AIR);
                    this.input.setMouse(MouseEvent.BUTTON1, false);
                }
                
                if (this.input.getMouse(MouseEvent.BUTTON3) && selectedBlock > 0)
                {
                	if(world.blockData[selectedBlock].onPlace(new PlaceEvent(new Location(this.world, targetBlockX, targetBlockY, targetBlockZ))))
                		this.world.setBlock(selectedBlock + i5, Block.getBlockById(this.getPlaceBlockTypeId()));
                    this.input.setMouse(MouseEvent.BUTTON3, false);
                }
               
                
                //delete collision, if a player tries to place a block on themselfs
                for ( int i8 = 0; i8 < PLAYER_HEIGHT; i8++) 
                {
                	int i9 = (int)(playerLoc.getX() + (float)(i8 >> 0 & 1) * 0.6f - 0.3f) - 64;
                    int i10 = (int)(playerLoc.getY() + (float)((i8 >> 2) - 1) * 0.8f + 0.65f) - 64;
                    int i11A = (int)(playerLoc.getZ() + (float)(i8 >> 1 & 1) * 0.6f - 0.3f) - 64;
                    
                    if (i9 >= 0 && i10 >= 0 && i11A >= 0 && i9 < 64 && i10 < 64 && i11A < 64) 
                    {
                    	this.world.setBlock(i9, i10, i11A , Block.AIR);
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
                        float f22 = fov * cosPitch + f20 * sinPitch;
                        float rotatedY = f20 * cosPitch - fov * sinPitch;
                        
                        float rotatedX = f18 * cosYaw + f22 * sinYaw;
                        float rotatedZ = f22 * cosYaw - f18 * sinYaw;
                        
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
                            float decAxis = playerLoc.getX() - (float)((int)playerLoc.getX());
                            
                            if (axis == 1) 
                            {
                                decAxis = playerLoc.getY() - (float)((int)playerLoc.getY());
                            }
                            
                            if (axis == 2) 
                            {
                                decAxis = playerLoc.getZ() - (float)((int)playerLoc.getZ());
                            }
                            
                            if (f27 > 0.0f) 
                            {
                                decAxis = 1.0f - decAxis;
                            }
                            float f33 = invrtRotatedAxis * decAxis;
                            
                            //block faces placement
                            float faceX = playerLoc.getX() + rotXInvrt * decAxis;
                            float faceY = playerLoc.getY() + rotYInvrt * decAxis;
                            float faceZ = playerLoc.getZ() + rotZInvert * decAxis;

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
                                    if ((blockInde != selectedBlock || !block.isSelectable()) || (textureX > 0 && textureY % 16 > 0 && textureX < 15 && textureY % 16 < 15))
                                        colorOfOutline = Textures.blockTextures.getTextures()[textureX + textureY * Textures.blockTextures.width + block.getTextureId() * Textures.blockTextures.height];
                                    
                                    //target block in middle of screen
                                    if (f33 < readDistance && vertIndex == this.eigthWidth && hortIndex ==  this.eigthHeight) 
                                    {
                                    	tempSelectingBlock = blockInde;
                                    	targetBlockX = blockX;
                                        targetBlockY = blockY;
                                        targetBlockZ = blockZ;
                                    	if(this.input.getKey(KeyEvent.VK_M))
                                    	{
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
                        imageData[vertIndex + hortIndex * (214)] = r << 16 | g << 8 | b;
                    }
                }
                selectedBlock = (int) tempSelectingBlock;
                
                //cross hair 
                invertPixel(this.eigthWidth, (this.eigthHeight));
                invertPixel(this.eigthWidth, (this.eigthHeight - 1));
                invertPixel(this.eigthWidth, (this.eigthHeight + 1));
                invertPixel(this.eigthWidth, (this.eigthHeight - 2));
                invertPixel(this.eigthWidth, (this.eigthHeight - 3));
                invertPixel(this.eigthWidth + 1, (this.eigthHeight - 1));
                invertPixel(this.eigthWidth + 2, (this.eigthHeight - 1));
                invertPixel(this.eigthWidth - 1, (this.eigthHeight - 1));
                invertPixel(this.eigthWidth - 2, (this.eigthHeight - 1));
                
                this.font.renderString("V0.4.5", 0, 0);
                this.font.renderString(Block.getBlockById(this.getPlaceBlockTypeId()).getName(), 0, 7);
                
                
                if(!this.input.isFocused())
                {
                	String message = "CLICK TO FOCUS";
                	this.font.renderString(message, message.length() * this.font.letterWidth, this.eigthHeight - (this.font.letterHeight / 2) - this.font.letterHeight);
                }
                
                for (int y = 0; y < this.world.getHeight(); ++y) 
    			{
	                for (int z = 0; z < this.world.getWidth(); ++z) 
	        		{
	        			for (int x = 0; x < this.world.getHeight(); ++x) 
	        			{
	        				Location location = new Location(this.world, x, y, z);
	        				location.getBlock().onTick(new Location(this.world, x, y, z));
	        			}
	        		}
    			}
                ticks++;
                Thread.sleep(1);
                if (!this.isActive()) 
                {
                    return;
                }
                this.getGraphics().drawImage(this.frameBuffer, 0, 0, this.width, this.height, null);
                fps++;
                if( System.currentTimeMillis() > fpsTimestamp + 1000)
                {
                	fpsTimestamp = System.currentTimeMillis();
//                	System.out.println("fps = " + fps);
                	fps = 0;
                }
            } 
        }
        catch (Exception localException) {
        	localException.printStackTrace();
            return;
        }
    }
    
    public void doNextTick(Runnable runnable)
    {
    	this.nextTickRunnables.add(runnable);
    }
    
    public void doNextTickWater(Runnable runnable)
    {
    	this.nextTickRunnablesWater.add(runnable);
    }
    
    
    public void setPixel(int x, int y, int pixel)
    {
    	imageData[x + y * this.quartWidth] = pixel;
    }
    
    public void invertPixel(int xWidth, int yHeight)
    {
    	int currentPixel = 0xFFFFFF - imageData[xWidth + yHeight * this.quartWidth];
    	
    	imageData[xWidth + yHeight * this.quartWidth] = currentPixel;
    }

	public int getPlaceBlockTypeId() {
		return placeBlockTypeId;
	}

	public void setPlaceBlockTypeId(int placeBlockTypeId) {
		this.placeBlockTypeId = placeBlockTypeId;
	}
}
