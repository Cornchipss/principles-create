package com.superempires.game.map.units;

import com.superempires.game.map.GameMap;
import com.superempires.game.map.actions.IActionable;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.IDrawable;

public abstract class Unit extends PhysicalObject implements IDrawable, IActionable
{
	private static int currentUnitId = 0;
	private Tile tile;
	private int id;
	
	public Unit(Transform transform, GameMap map)
	{
		super(transform, map);
		
		this.id = currentUnitId++;
	}

	public void setTile(Tile tile) { this.tile = tile; }
	public Tile getTile() { return tile; }

	public abstract double getTravelRadius();

	@Override
	public int hashCode()
	{
		return getId();
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Unit)
		{
			Unit unit = (Unit)obj;
			
			return unit.getId() == getId();
		}
		
		return false;
	}
	
	public int getId() { return id; }
}