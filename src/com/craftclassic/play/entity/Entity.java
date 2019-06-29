package com.craftclassic.play.entity;

import com.craftclassic.play.utils.Location;
import com.craftclassic.play.utils.Vector;

public class Entity {
	private int id;
	private String nameId;
	private Location location;
	private boolean destroy;
	private Vector velocity;
	
	public Entity(int id, String nameId, boolean destroy) {
		this.id = id;
		this.nameId = nameId;
		this.destroy = destroy;
		this.setVelocity(new Vector(0, 0, 0));
	}
	
	public Entity(int id, String nameId) {
		this(id, nameId, false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Override pls
	 * @param location
	 */
	public void spawn(Location location) 
	{
		this.setLocation(location);
	
	}
	
	/**
	 * Override pls
	 */
	public void tick() 
	{
		
	}
	
	/**
	 * Override pls
	 */
	public void render() 
	{
		
	}
}
