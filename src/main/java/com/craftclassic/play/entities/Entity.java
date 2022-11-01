package com.craftclassic.play.entities;

import com.craftclassic.play.utils.Location;
import com.craftclassic.play.utils.Vector;

public class Entity
{
	
	private String name;
	private Location location;
	private Vector velocity;
	public float prepitch = 0;
	public float preyaw = 5.0f;
	private boolean onGround = false;
	private boolean onJump = false;
    private int jumpingTicks = -1;
	
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

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isOnJump() {
		return onJump;
	}

	public void setOnJump(boolean onJump) {
		this.onJump = onJump;
	}

	public int getJumpingTicks() {
		return jumpingTicks;
	}
	
	public void incrementJumpingTicks()
	{
		jumpingTicks++;
	}

	public void setJumpingTicks(int jumpingTicks) {
		this.jumpingTicks = jumpingTicks;
	}
}
