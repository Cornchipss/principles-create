package com.superempires.game.map.buildings;

import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.render.IDrawable;

public abstract class Building extends PhysicalObject implements IDrawable
{
	public Building(float x, float y)
	{
		super(x, y);
	}
}
