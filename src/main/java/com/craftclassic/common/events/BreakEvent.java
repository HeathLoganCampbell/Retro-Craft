package com.craftclassic.common.events;

import com.craftclassic.common.utils.Location;

public class BreakEvent 
{
	private Location location;
	
	public BreakEvent(Location location) {
		super();
		this.setLocation(location);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
