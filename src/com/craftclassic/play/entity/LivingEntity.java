package com.craftclassic.play.entity;

public class LivingEntity extends Entity 
{
	private long aliveSince;
	private int health;
	private int maxHealth;
	private String displayName = null;

	public LivingEntity(int id, String nameId, boolean destroy, int health, int maxHealth) 
	{
		super(id, nameId, destroy);
		this.health = health;
		this.maxHealth = maxHealth;
	}
	
	public LivingEntity(int id, String nameId, int health, int maxHealth) 
	{
		this(id, nameId, false, health, maxHealth);
	}

	public long getAliveSince() {
		return aliveSince;
	}

	public void setAliveSince(long aliveSince) {
		this.aliveSince = aliveSince;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	
}
