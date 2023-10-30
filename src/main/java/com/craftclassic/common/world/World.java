package com.craftclassic.common.world;

import com.craftclassic.client.Main;
import com.craftclassic.client.Minecraft;
import com.craftclassic.client.assets.Textures;
import com.craftclassic.common.blocks.Block;
import com.craftclassic.common.entities.Entity;
import com.craftclassic.common.utils.Location;
import com.craftclassic.common.world.types.FlatWorldType;
import com.craftclassic.common.world.types.WorldType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class World implements IWorld
{
	Random random = new Random();
	public Block[] blockData = new Block[262144];

	private int width, height, depth;
	private long seed;
	private Minecraft minecraft;
	
	private List<Entity> entities;

	public static float fogDistance = 50f;

	private WorldType worldType;
	private double[] zBuffer;


	public World(Minecraft minecraft, int width, int depth, int height, long seed)
	{
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.seed = seed;
		this.setMinecraft(minecraft);
		this.entities = new ArrayList<>();

		this.worldType = new FlatWorldType(this);

		this.zBuffer = new double[Main.WIDTH * Main.HEIGHT];

		init();
	}

	public void init() {
		random.setSeed(seed);

		worldType.generateTerrian(this.width, this.height);
	}

	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}

	public List<Entity> getEntities()
	{
		return this.entities;
	}
	
	public void setBlock(int x, int y, int z, Block block)
	{
		this.blockData[x + y * 64 + z * 4096] = block;
	}
	
	public void setBlock(Location location, Block block)
	{
		int index = this.locationToWorldIndex(location);
		if(location.getX() >= 0 && location.getX() <= this.width
				&& location.getY() >= 0 && location.getY() <= this.height
				&& index > 0 && index < this.blockData.length)
			this.blockData[index] = block;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Block getBlock(int x, int y, int z)
	{
		Block block = this.blockData[x + y * 64 + z * 4096];

		if(block == null)
		{
			return Block.AIR;
		}

		return block;
	}
	
	public Block getBlock(Location location)
	{
		int index = this.locationToWorldIndex(location);
		if(location.getX() >= 0 && location.getX() <= this.width
				&& location.getY() >= 0 && location.getY() <= this.height
				&& index > 0 && index < this.blockData.length)
		{
			Block block = this.blockData[index];

			if (block == null)
			{
				return Block.AIR;
			}

			return block;
		}
		return Block.BEDROCK;
	}

	@Override
	public long getSeed() {
		return this.seed;
	}

	private int locationToWorldIndex(Location loc)
	{
		return ((int) loc.getX()) + ((int) loc.getY()) * 64 + ((int) loc.getZ()) * 4096;
	}

	public void tick()
	{
		for (Entity entity : this.getEntities())
		{
			entity.tick();
		}

		for (int y = 0; y < this.getHeight(); ++y)
		{
			for (int z = 0; z < this.getWidth(); ++z)
			{
				for (int x = 0; x < this.getHeight(); ++x)
				{
					Location location = new Location(this, x, y, z);
					location.getBlock().onTick(new Location(this, x, y, z));
				}
			}
		}
	}

	private boolean isInWorld(int blockX, int blockY, int blockZ)
	{
		return !(blockX < 0 || blockY < 0 || blockZ < 0 || blockX >= width || blockY >= height || blockZ >= depth);
	}


	public void render()
	{
		Arrays.fill(this.zBuffer, Integer.MAX_VALUE);

		int textureX = 0;
		int textureY = 0;

		int skyboxColour = 0xA7C9EB;
		int maxSkyboxColour = 255;

		double fogOfWar = 50.0;//render distance

		Location playerLoc = this.minecraft.player.getLocation();
		float sinYaw = (float)Math.sin(playerLoc.getYaw() * 100f);
		float cosYaw = (float)Math.cos(playerLoc.getYaw() * 100f);
		float sinPitch = (float)Math.sin(playerLoc.getPitch() * 100f);
		float cosPitch = (float)Math.cos(playerLoc.getPitch() * 100f);

		Location tempTargetBlock = null;
		Location tempTargetBlockPlace = null;

		int pixelWidth = Main.WIDTH;
		int pixelHalfWidth = pixelWidth / 2;

		int pixelHeight = Main.HEIGHT;
		int pixelHalfHeight = pixelHeight / 2;

		int targetBlockOffset = 0;

		float fov = this.minecraft.player.isSprinting() && !this.minecraft.player.getVelocity().isZero() ? 0.95f : 1f; //fov, less than 1 makes it look faster

		for (int screenX = 0; screenX < pixelWidth; screenX++)
		{
			float vpixelLoc = (float)(screenX - pixelHalfWidth) / 90.0f;

			//number of lines to render, horzontial
			for(int screenY = 0; screenY < pixelHeight; screenY++)
			{
				float hpixelLoc = (float)(screenY - pixelHalfHeight) / 90.0f;

				//rotation matrix
				float rotateZZ = fov * cosPitch + hpixelLoc * sinPitch;
				float rotatedY = hpixelLoc * cosPitch - fov * sinPitch;

				float rotatedX = vpixelLoc * cosYaw + rotateZZ * sinYaw;
				float rotatedZ = rotateZZ * cosYaw - vpixelLoc * sinYaw;

				float readDistance = 5.0f;//read distance
				double dist = Integer.MAX_VALUE;

				for (int axis = 0; axis < 3; axis++)
				{
					float currentAxisRot = rotatedX;

					if (axis == 1)
					{
						currentAxisRot = rotatedY;
					}

					if (axis == 2)
					{
						currentAxisRot = rotatedZ;
					}

					float invertRotatedAxis = 1.0f / Math.abs(currentAxisRot);
					float rotXInvert = rotatedX * invertRotatedAxis;
					float rotYInvert = rotatedY * invertRotatedAxis;
					float rotZInvert = rotatedZ * invertRotatedAxis;
					float decAxis = 0;

					if (axis == 0)
					{
						decAxis = playerLoc.getX() - (float)((int)playerLoc.getX());
					}

					if (axis == 1)
					{
						decAxis = playerLoc.getY() - (float)((int)playerLoc.getY());
					}

					if (axis == 2)
					{
						decAxis = playerLoc.getZ() - (float)((int)playerLoc.getZ());
					}

					// face closest to the player
					if (currentAxisRot > 0.0f)
					{
						decAxis = 1.0f - decAxis;
					}

					float currentPosition = invertRotatedAxis * decAxis;

					//block faces placement
					float faceX = playerLoc.getX() + rotXInvert * decAxis;
					float faceY = playerLoc.getY() + rotYInvert * decAxis;
					float faceZ = playerLoc.getZ() + rotZInvert * decAxis;

					if (currentAxisRot < 0.0f)
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

					boolean isInLoop = true;
					// loop til we hit a block
					while (isInLoop && currentPosition < fogOfWar)
					{
						//render all blocks
						//off set so we don't have divide zero errors
						int blockX = (int)faceX - 64;
						int blockY = (int)faceY - 64;
						int blockZ = (int)faceZ - 64;

						//block out of mapsize
						if (!isInWorld(blockX, blockY, blockZ))
							break;

						//get block, then get pixel in block
						Block block = getBlock(blockX, blockY, blockZ);//selected block

						if (block != Block.AIR)
						{
							textureX = (int)((faceX + faceZ) * 16.0f) & 15;
							textureY = ((int)(faceY * 16.0f) & 15) + 16;

							//if y, render bottom face of block differently
							if (axis == 1)
							{
								//map texture onto block
								textureX = (int)(faceX * 16.0f) & 15;
								textureY = (int)(faceZ * 16.0f) & 15;
								//map bottom of block render as bottom,
								if (rotYInvert < 0.0f)
								{
									textureY += 32;
								}
							}

							int colorOfOutline = Textures.blockTextures.getTextures()[textureX + textureY * Textures.blockTextures.width + block.getTextureId() * Textures.blockTextures.height];//0xffff00;//default colour of block
							if (this.minecraft.player.getTargetBlock() != null
									&& this.minecraft.player.getTargetBlock().getX() == blockX
									&& this.minecraft.player.getTargetBlock().getY() == blockY
									&& this.minecraft.player.getTargetBlock().getZ() == blockZ
									&& block.isSelectable()
									&& (textureX == 0 || textureY % 16 == 0 || textureX == 15 || textureY % 16 == 15)) {
								colorOfOutline = 0xffff00;
							}

							boolean crosshairOnBlock = screenX == this.minecraft.screen.halfWidth
													&& screenY == this.minecraft.screen.halfHeight;
							if (currentPosition < readDistance  && crosshairOnBlock)
							{
								tempTargetBlock = new Location(this, blockX, blockY, blockZ);

								targetBlockOffset = 1;
								if (currentAxisRot > 0.0f)
								{
									targetBlockOffset = -1;
								}

								targetBlockOffset <<= 6 * axis;

								final int z = targetBlockOffset / (64 * 64);
								targetBlockOffset -= (z * 64 * 64);
								final int y = targetBlockOffset / 64;
								final int x = targetBlockOffset % 64;

								tempTargetBlockPlace = new Location(this, blockX + x, blockY + y, blockZ + z);

								readDistance = currentPosition;
								isInLoop = false;
							}

							//Add fog to each pixel
							if (colorOfOutline > 0x000000)//Don't render black textures
							{
								skyboxColour = colorOfOutline;
								maxSkyboxColour = 255 - (int)(currentPosition / fogDistance * 255.0f);
								// This adds shadows to blocks
								maxSkyboxColour = maxSkyboxColour * (255 - (axis + 2) % 3 * 50) / 255;
								fogOfWar = currentPosition;
								if(currentPosition < dist)
								{
									dist = currentPosition;
								}
							}
						}

						faceX += rotXInvert;
						faceY += rotYInvert;
						faceZ += rotZInvert;
						currentPosition += invertRotatedAxis;
					}
				}

				int r = (skyboxColour >> 16 & 255) * maxSkyboxColour / 255;
				int g = (skyboxColour >> 8 & 255) * maxSkyboxColour / 255;
				int b = (skyboxColour & 255) * maxSkyboxColour / 255;

				this.zBuffer[screenX + screenY * Main.WIDTH] = dist;
				this.minecraft.screen.setPixel(screenX, screenY, r << 16 | g << 8 | b);
			}
		}

		this.minecraft.player.setTargetBlock(tempTargetBlock);
		this.minecraft.player.setTargetBlockPlace(tempTargetBlockPlace);

		for (Entity entity : this.entities)
		{
			if(entity.getEntityId() != 0)
			{
				renderSprite(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
			}
		}
	}

	private void renderSprite(double x, double y, double z)
	{
		int height = 64 * 4;
		int width = 32 * 4;

		int textureHeight = 64;
		int textureWidth = 32;

		int textureOffsetX = 0;
		int textureOffsetY = 0;

		Location playerLoc = this.minecraft.player.getLocation();

		double diffX = x - playerLoc.getX();
		double diffY = (y) - playerLoc.getY();
		double diffZ = z - playerLoc.getZ();

		if(diffY == 0.0) diffY = 0.005;

		float pitch = -playerLoc.getPitch() * 100;
		float yaw = playerLoc.getYaw() * 100;

		// yaw
		double xx = Math.cos(yaw) * diffX - Math.sin(yaw) * diffZ;
		double zz = Math.sin(yaw) * diffX + Math.cos(yaw) * diffZ;

		// pitch
		double yy = Math.cos(pitch) * diffY - Math.sin(pitch) * zz;
		zz = Math.sin(pitch) * diffY + Math.cos(pitch) * zz;

		if(zz < 0) return;

		int screenX = (int) ((xx * (double) 90 / zz) + Main.WIDTH / 2);
		int screenY = (int) ((yy * (double) 90 / zz) + Main.HEIGHT / 2);

		if(screenX < 0 || screenX > Main.WIDTH) return;
		if(screenY < 0 || screenY > Main.HEIGHT) return;

		double xPixelL = screenX - (double) width / 2 / zz;
		double xPixelR = screenX + (double)  width / 2 / zz;

		double yPixelL = screenY - (double)  height / 2 / zz;
		double yPixelR = screenY + (double)  height / 2 / zz;

		int xpl = (int) Math.ceil(xPixelL);
		int xpr = (int) Math.ceil(xPixelR);
		int ypl = (int) Math.ceil(yPixelL);
		int ypr = (int) Math.ceil(yPixelR);

		for (int yp = ypl; yp < ypr; yp++) {

			double pixelRotationY = (yp - yPixelR) / (yPixelL - yPixelR);
			int yTexture = (int) (pixelRotationY * textureHeight);

			for (int xp = xpl; xp < xpr; xp++)
			{
				double pixelRotationX = (xp - xPixelR) / (xPixelL - xPixelR);
				int xTexture = (int) (pixelRotationX * textureWidth);

				int textureX = (textureWidth - xTexture - 1) + textureOffsetX;
				int textureY = (textureHeight - yTexture - 1) + textureOffsetY;

				int textureIndex = textureX + (textureY * textureWidth);
				if(textureIndex < 0 || textureIndex >= Textures.playerTextures.getTextures().length)
					continue;

				int col = Textures.playerTextures.getTextures()[textureIndex];
				if(col != 0xFF000000)
				{
					int i = xp + yp * Main.WIDTH;
					if(i >= 0 && i < this.zBuffer.length)
					{
						if(this.zBuffer[i] < zz)
						{
							continue;
						}

						this.zBuffer[i] = zz;
					}

					this.minecraft.screen.setPixel(xp, yp, col);
				}
			}
		}
	}

	public Minecraft getMinecraft() {
		return minecraft;
	}

	public void setMinecraft(Minecraft minecraft) {
		this.minecraft = minecraft;
	}
}
