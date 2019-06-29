package com.craftclassic.play.utils;

public class Vector 
{
	private double x, y, z;

	public Vector(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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

	public double dot(Vector vector)
	{
		return this.x * vector.x + this.y * vector.y + this.z * vector.z;
	}
	
	public void add(Vector vector)
	{
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}

	public void subtract(Vector vector)
	{
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
	}
	
	public void scalar(double scale)
	{
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}
	
}
