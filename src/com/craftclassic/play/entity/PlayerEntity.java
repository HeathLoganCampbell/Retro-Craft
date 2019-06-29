package com.craftclassic.play.entity;

public class PlayerEntity extends LivingEntity
{
	public static final String ENTITY_NAME_ID = "living_entity_player";

	
	public PlayerEntity(int id, boolean destroy, int health, int maxHealth) 
	{
		super(id, ENTITY_NAME_ID, destroy, health, maxHealth);
	}
	
	public PlayerEntity(int id, int health, int maxHealth) 
	{
		this(id, false, health, maxHealth);
	}
}
