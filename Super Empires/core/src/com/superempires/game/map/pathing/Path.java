package com.superempires.game.map.pathing;

import com.superempires.game.map.tiling.Tile;

public class Path
{
	private double travelTime;
	private Tile tile;
	
	private Path parent;
	
	public Path(Tile tile, double travelTime)
	{
		this.tile = tile;
		this.travelTime = travelTime;
	}
	
	public Path giveBirth(Tile t, double travelTime)
	{
		Path p = new Path(t, travelTime);
		p.parent = this;
		
		return p;
	}
	
	public double getTravelTime() { return travelTime; }
	
	public Tile getTile() { return tile; }
	
	public Path getParent() { return parent; }
}
