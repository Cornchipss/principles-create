package com.superempires.game.map.buildings;

import com.superempires.game.map.tiling.Tile;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.IDrawable;

public abstract class Building extends PhysicalObject implements IDrawable
{
	private Tile tile;
	
	public Building(Transform t)
	{
		super(t);
	}

	public void setTile(Tile tile)
	{
		this.tile = tile;
	}
	
	public Tile getTile() { return tile; }
}
