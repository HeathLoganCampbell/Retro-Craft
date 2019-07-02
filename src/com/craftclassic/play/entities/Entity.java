package com.craftclassic.play.entities;

import com.craftclassic.play.utils.Location;

public class Entity
{
	private String name;
	private Location location;
	public float prepitch = 0;
	public float preyaw = 5.0f;
	
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
	
	public void turn(int diffX, int diffY) 
	{
		float beforeYaw = this.preyaw;
		float beforePitch = this.prepitch;
		
		this.prepitch = (float)((double)this.prepitch - (double)diffY * 0.015D);
		this.preyaw = (float)((double)this.preyaw + (double)diffX * 0.015D);
		
		 if(this.preyaw < -90.0F)
			 this.preyaw = -90.0F;

		 if(this.preyaw > 90.0F)
			 this.preyaw = 90.0F;
		 
		 if(this.prepitch < 10.9f)
			 this.prepitch = 10.9f;
		 
		 if(this.prepitch > 14.15f)
			 this.prepitch = 14.15f;
		 
		 this.getLocation().setYaw(this.getLocation().getYaw() + (this.preyaw - beforeYaw));
		 this.getLocation().setPitch(this.getLocation().getPitch() + (this.prepitch - beforePitch));
	}
}
