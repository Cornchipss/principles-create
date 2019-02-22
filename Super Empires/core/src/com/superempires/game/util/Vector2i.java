package com.superempires.game.util;

import com.badlogic.gdx.math.Vector2;

public class Vector2i
{
	public int x, y;
	
	public Vector2i()
	{
		this(0, 0);
	}
	
	public Vector2i(int i, int j)
	{
		x = i;
		y = j;
	}
	
	public Vector2 asVector2()
	{
		return new Vector2(x, y);
	}
}
