package com.superempires.game.objects.properties;

import com.badlogic.gdx.math.Vector2;

public class Transform implements Cloneable
{
	private float x, y;
	private float width, height;
	private Vector2 center;

	public Transform()
	{
		this(0, 0, 0, 0);
	}
	
	public Transform(float x, float y, float w, float h)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		
		center = new Vector2(x + width / 2, y + height / 2);
	}
	
	public Vector2 getCenter()
	{
		return center;
	}
	
	@Override
	public Transform clone() { return new Transform(x, y, width, height); }
	
	@Override
	public String toString()
	{
		return "Transform: [(" + x + ", " + y + ") " + width + " x " + height + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + Float.floatToIntBits(width);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Transform)
		{
			Transform other = (Transform) obj;
			if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
				return false;
			if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
				return false;
			if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
				return false;
			if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
				return false;
			
			return true;
		}
		
		return false;
	}

	public float getX() { return x; }
	public void setX(float x) { this.x = x; }

	public float getY() { return y; }
	public void setY(float y) { this.y = y; }
	
	public float getWidth() { return width; }
	public float getHeight() { return height; }

	public void setWidth(float w) { this.width = w; }
	public void setHeight(float h) { this.height = h; }
}
