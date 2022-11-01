package com.craftclassic.common.entities;

import com.craftclassic.common.utils.Location;
import com.craftclassic.common.utils.Vector;
import com.craftclassic.common.world.World;

public class Entity
{

	private int entityId;
	private String name;
	private Location location;
	private Vector velocity;
	public float prepitch = 0;
	public float preyaw = (float) 0f;
	private boolean onGround = false;
	private boolean onJump = false;
    private int jumpingTicks = -1;
	
	public Entity(String name, int entityId) {
		super();
		this.entityId = entityId;
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
	
	public void turn(float diffX, float diffY)
	{
		double sensitivity = 0.00015D;

		 this.getLocation().setYaw((float) (this.getLocation().getYaw() + diffX * sensitivity));
		 this.getLocation().setPitch((float) (this.getLocation().getPitch() + diffY * sensitivity));
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

	public World getWorld()
	{
		return this.getLocation().getWorld();
	}

	public void tick()
	{

	}

	public int getEntityId() {
		return entityId;
	}

	@Override
	public String toString() {
		return "Entity{" +
				"entityId=" + entityId +
				", name='" + name + '\'' +
				", location=" + location +
				'}';
	}
}
