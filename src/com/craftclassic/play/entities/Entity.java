package com.craftclassic.play.entities;

import com.craftclassic.play.utils.Location;

public class Entity
{
	private String name;
	private Location location;
	
	public Entity(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
}
