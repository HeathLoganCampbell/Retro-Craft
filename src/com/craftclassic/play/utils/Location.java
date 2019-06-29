package com.craftclassic.play.utils;

public class Location 
{
	private double x, y, z;
	private float yaw, pitch;
	
	public Location(double x, double y, double z, float yaw, float pitch) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Location(double x, double y, double z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = 0;
		this.pitch = 0;
	}
	
	
}
