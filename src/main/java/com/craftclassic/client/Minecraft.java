package com.craftclassic.client;

import com.craftclassic.client.graphics.Screen;
import com.craftclassic.client.input.Input;
import com.craftclassic.client.net.NettyClient;
import com.craftclassic.client.overlay.CrossHair;
import com.craftclassic.client.overlay.InfoOverlay;
import com.craftclassic.common.entities.Player;
import com.craftclassic.common.network.packet.Packet;
import com.craftclassic.common.network.packet.packets.EntityPositionPacket;
import com.craftclassic.common.utils.Location;
import com.craftclassic.common.utils.Vector;
import com.craftclassic.common.world.World;

import java.applet.Applet;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Minecraft extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;
//    int[] textureData = Textures.textureData;
    public World world;

    public Input input;
    
    private int width = 0;
    private int height = 0;
    
    private int fps = 0;
    private long fpsTimestamp = 0;
    
    public Player player;
    
    private Robot robot;
    
    private List<Runnable> nextTickRunnables;
    private List<Runnable> nextTickRunnablesWater;
    
    private static int placeBlockTypeId = 1;

    public static int FRAMES_PER_SECOND = 0;

    public Screen screen;

    private CrossHair crossHair;

    private InfoOverlay infoOverlay;

    public static Minecraft INSTANCE;

    private NettyClient nettyClient;

    public Minecraft(int width, int height)
    {
    	this.width = width;
    	this.height = height;

    	try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
    	
//    	this.particleManager = new ParticleManager();
////    	for(int y = 0; y < 150; y++)
//    	{
//    		this.particleManager.spawn(new Particle("Draw", 100.5, 83.5, 100.5));
//
//    	}
    	
    	this.input = new Input(width, height, this.robot, this);
    	addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

        this.screen = new Screen(width, height);
        this.crossHair = new CrossHair(this.screen);
        this.infoOverlay = new InfoOverlay(this, this.screen);

        INSTANCE = this;
    }
    
    @Override
    public void start() 
    {
        new Thread(this).start();
    }

    public void tick(int tickCount)
    {
        if(tickCount % 100 == 0)
        {
            this.nextTickRunnables.forEach(Runnable::run);
            this.nextTickRunnables.clear();
        }

        if(tickCount % 10 == 0)
        {
            this.nextTickRunnablesWater.forEach(Runnable::run);
            this.nextTickRunnablesWater.clear();
        }

        this.infoOverlay.tick();
        this.world.tick();
    }

    public void render()
    {
        this.world.render();

        this.crossHair.render();
        this.infoOverlay.render();
    }

    @Override
    public void run() 
    {
        try 
        {          
        	this.nextTickRunnables = new ArrayList<>();
        	this.nextTickRunnablesWater = new ArrayList<>();

            nettyClient = new NettyClient();

        	this.world = new World(this, 64, 64, 64, 1);

        	this.player = new Player(null,"Player1", 0);
        	this.player.setLocation(new Location(this.world, 64 + 32.5f, 64 + 1.0f, 64 + 32.5f));
        	this.player.setVelocity(new Vector(0f, 60f, 0f));

            this.world.addEntity(this.player);

            int ticks = 0;

            nettyClient.run();

            while(true)
            {
            	this.tick(ticks);
                this.render();

                Location location = this.player.getLocation();
                nettyClient.getServerChannel().writeAndFlush(new EntityPositionPacket(0, location.getX(), location.getY(), location.getZ()));

                ticks++;
                Thread.sleep(5);
                if (!this.isActive()) 
                {
                    return;
                }

                this.getGraphics().drawImage(this.screen.frameBuffer, 0, 0, Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE, null);
                fps++;
                if( System.currentTimeMillis() > fpsTimestamp + 1000)
                {
                    FRAMES_PER_SECOND = fps;
                	fpsTimestamp = System.currentTimeMillis();
                	fps = 0;
                }
            } 
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

	public static int getPlaceBlockTypeId() {
		return placeBlockTypeId;
	}

	public static void setPlaceBlockTypeId(int localPlaceBlockTypeId) {
		placeBlockTypeId = localPlaceBlockTypeId;
	}

    public void sendPacket(Packet packet)
    {
        this.nettyClient.sendPacket(packet);
    }
}
