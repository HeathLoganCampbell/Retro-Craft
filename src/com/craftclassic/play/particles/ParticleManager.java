package com.craftclassic.play.particles;

import java.util.ArrayList;

public class ParticleManager
{
	private ArrayList<Particle> particles = new ArrayList<>();
	
	public ParticleManager()
	{
		
	}
	
	public void draw()
	{
		this.particles.forEach(Particle::draw);
	}
	
	public void tick()
	{
		
	}
	
	public void spawn(Particle particle)
	{
		particles.add(particle);
	}
}
