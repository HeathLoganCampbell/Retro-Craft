package com.craftclassic.play.utils;

import com.craftclassic.play.world.World;

public class Location 
{
	private double x, y, z;
	private float yaw, pitch;
	private World world;
	
	public Location(World world, double x, double y, double z, float yaw, float pitch) 
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Location(World world, double x, double y, double z) 
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = 0;
		this.pitch = 0;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
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
}
