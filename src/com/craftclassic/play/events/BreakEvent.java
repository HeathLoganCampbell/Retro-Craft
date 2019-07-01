package com.craftclassic.play.events;

import com.craftclassic.play.utils.Location;

public class BreakEvent 
{
	public BreakEvent(Location location) {
		super();
		this.location = location;
	}

	private Location location;
}
