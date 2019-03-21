package com.superempires.game.map.units;

import com.superempires.game.map.tiling.Tile;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.IDrawable;

public abstract class Unit extends PhysicalObject implements IDrawable
{
	private Tile tile;
	
	public Unit(Transform transform)
	{
		super(transform);
	}

	public void setTile(Tile tile) { this.tile = tile; }
	public Tile getTile() { return tile; }

	public abstract double getTravelRadius();
}