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

	public Vector2i sub(int i, int j)
	{
		x -= i;
		y -= j;
		return this;
	}
	
	public Vector2i add(int i, int j)
	{
		x += i;
		y += j;
		return this;
	}
	
	public Vector2i cpy()
	{
		return new Vector2i(x, y);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Vector2i)
		{
			Vector2i v = (Vector2i)other;
			
			return x == v.x && y == v.y;
		}
		return false;
	}
}
