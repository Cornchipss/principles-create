package com.superempires.game.map.buildings;

import com.superempires.game.map.tiling.Tile;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.IDrawable;

public abstract class Building extends PhysicalObject implements IDrawable
{
	private Tile tile;
	private static int currentingBuildingID = 0;
	
	private int id;
	
	public Building(Transform t)
	{
		super(t);
		
		this.id = currentingBuildingID++;
	}

	public void setTile(Tile tile)
	{
		this.tile = tile;
	}
	
	public Tile getTile() { return tile; }
	
	@Override
	public int hashCode()
	{
		return getId();
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Building)
		{
			Building building = (Building)obj;
			
			return building.getId() != getId();
		}
		return false;
	}
	
	public int getId() { return id; }
}
