package com.craftclassic.common.entities;

import com.craftclassic.client.Minecraft;
import com.craftclassic.client.input.Input;
import com.craftclassic.common.blocks.Block;
import com.craftclassic.common.events.BreakEvent;
import com.craftclassic.common.events.PlaceEvent;
import com.craftclassic.common.network.packet.packets.BlockPlacePacket;
import com.craftclassic.common.network.player.PlayerConnection;
import com.craftclassic.common.utils.Location;
import com.craftclassic.common.utils.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends LivingEntity
{
	public static final int PLAYER_HEIGHT = 12;

	private Location targetBlock;

	private Location targetBlockPlace;

	private PlayerConnection playerConnection;

	private boolean sprinting = false;

	public Player(PlayerConnection playerConnection, String name, int id)
	{
		super(name, id);
		this.playerConnection = playerConnection;
	}

	public boolean isSprinting() {
		return sprinting;
	}

	public Location getTargetBlock() {
		return targetBlock;
	}

	public void setTargetBlock(Location targetBlock) {
		this.targetBlock = targetBlock;
	}

	@Override
	public void tick()
	{
		if (Input.getMouse(MouseEvent.BUTTON1) && this.getTargetBlock() != null)
		{
			if(getWorld().getBlock(this.getTargetBlock()).onBreak(new BreakEvent(this.getTargetBlock())))
			{
				BlockPlacePacket blockPlacePacket = new BlockPlacePacket((int) this.getTargetBlock().getX(),
						(int) this.getTargetBlock().getY(),
						(int) this.getTargetBlock().getZ(),
						Block.AIR.getId());
				getWorld().setBlock(this.getTargetBlock(), Block.AIR);
				Minecraft.INSTANCE.sendPacket(blockPlacePacket);
			}
			Input.setMouse(MouseEvent.BUTTON1, false);
		}

		this.sprinting = Input.getKey(KeyEvent.VK_CONTROL);

		if (Input.getMouse(MouseEvent.BUTTON3) && this.getTargetBlock() != null)
		{
			Block block = getWorld().getBlock(this.getTargetBlockPlace());

			if(block.onPlace(new PlaceEvent(this.getTargetBlockPlace()))) {
				BlockPlacePacket blockPlacePacket = new BlockPlacePacket((int) this.getTargetBlockPlace().getX(),
																		 (int) this.getTargetBlockPlace().getY(),
																		 (int) this.getTargetBlockPlace().getZ(),
						Minecraft.getPlaceBlockTypeId());
				getWorld().setBlock(this.getTargetBlockPlace(), Block.getBlockById(Minecraft.getPlaceBlockTypeId()));
				Minecraft.INSTANCE.sendPacket(blockPlacePacket);
			}
			Input.setMouse(MouseEvent.BUTTON3, false);
		}

		Location playerLoc = this.getLocation();
		float sinYaw = (float)Math.sin(playerLoc.getYaw() * 100f);
		float cosYaw = (float)Math.cos(playerLoc.getYaw() * 100f);

		float speed = 0.02f;

		if(sprinting)
		{
			speed = 0.03f;
		}

		float accX = (float)((Input.getKey(KeyEvent.VK_D) ? 1 : 0) - (Input.getKey(KeyEvent.VK_A) ? 1 : 0)) * speed;
		float accZ = (float)((Input.getKey(KeyEvent.VK_W) ? 1 : 0) - (Input.getKey(KeyEvent.VK_S) ? 1 : 0)) * speed;

		this.getVelocity().scalar(0.5f, 0.99f, 0.5f);

		float xVel = sinYaw * accZ + cosYaw * accX;
		float zVel = cosYaw * accZ - sinYaw * accX;
		float yVel = 0.004f;

		this.getVelocity().add(new Vector(xVel, yVel, zVel));

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

				newPlayerX += this.getVelocity().getX();
			}

			if(collAxis == 1)
			{
				newPlayerY += this.getVelocity().getY();
			}


			if(collAxis == 2)
			{
				newPlayerZ += this.getVelocity().getZ();
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

				if (i13 < 0 || i14 < 0 || i15 < 0 || i13 >= 64 || i14 >= 64 || i15 >= 64 || playerLoc.getWorld().getBlock(i13, i14, i15) != Block.AIR)
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
						if (Input.getKey(KeyEvent.VK_SPACE))
						{
							Input.setKey(KeyEvent.VK_SPACE, false);
							this.getVelocity().setY(-0.13f);
							this.setOnJump(true);
							this.setJumpingTicks(0);
							this.setOnGround(false);

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
				this.setOnGround(false);
			}
			else
			{
				if(this.getJumpingTicks() > 0)
				{
					this.setJumpingTicks(-1);
					this.setOnJump(false);
				}

				this.setOnGround(true);
				if(this.isOnJump()) this.incrementJumpingTicks();
				if(!this.isOnJump() && this.isOnGround()) this.getVelocity().setY(0.0f);
			}

			if(!collisonZ) playerLoc.setZ(newPlayerZ);

			if(collison) return;
		}

	}

	public Location getTargetBlockPlace() {
		return targetBlockPlace;
	}

	public void setTargetBlockPlace(Location targetBlockPlace) {
		this.targetBlockPlace = targetBlockPlace;
	}

	public PlayerConnection getPlayerConnection() {
		return playerConnection;
	}
}
