package com.craftclassic.play.utils;

import com.craftclassic.play.blocks.Block;
import com.craftclassic.play.world.World;

public class Location implements Cloneable
{
	private float x, y, z;
	private float yaw, pitch;
	private World world;
	
	public Location(World world, float x, float y, float z, float yaw, float pitch) 
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Location(World world, float x, float y, float z) 
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = 0;
		this.pitch = 0;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public void add(int x, int y, int z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void subtract(int x, int y, int z)
	{
		this.add(-x, -y, -z);
	}
	
	public Block getBlock()
	{
		return this.getWorld().getBlock(this);
	}
	
	public void setBlock(Block block)
	{
		this.getWorld().setBlock(this, block);
	}
	
	@Override
    public Location clone()
	{
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

}
