package com.craftclassic.play.utils;

public class Vector 
{
	private float x, y, z;

	public Vector(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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

	public float dot(Vector vector)
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
	
	public void scalar(float scale)
	{
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}
	
	public void scalar(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
	}
	
}
