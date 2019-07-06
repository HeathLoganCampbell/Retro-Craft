package com.craftclassic.play.particles;

public class Particle
{
	private String name;
	private double x, y, z;
	
	public Particle(String name, double x, double y, double z) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void draw()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
}
