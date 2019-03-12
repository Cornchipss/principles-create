package com.superempires.game.objects;

import com.superempires.game.map.tiling.Tile;
import com.superempires.game.objects.properties.Transform;

public class PhysicalObject extends GameObject
{
	private Transform transform;
	
	public PhysicalObject()
	{
		transform = new Transform();
	}
	
	public PhysicalObject(float x, float y)
	{
		transform = new Transform(x, y, Tile.DIMENSIONS, Tile.DIMENSIONS);
	}
	
	public PhysicalObject(Transform t)
	{
		transform = t;
	}
	
	public Transform getTransform() { return transform; }
	public void setTransform(Transform transform) { this.transform = transform; }
}
