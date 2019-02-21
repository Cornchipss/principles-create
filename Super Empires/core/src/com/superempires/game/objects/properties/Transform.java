package com.superempires.game.objects.properties;

import com.badlogic.gdx.math.Vector2;

public class Transform
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
	
	public float getX() { return x; }
	public void setX(float x) { this.x = x; }

	public float getY() { return y; }
	public void setY(float y) { this.y = y; }
	
	public float getWidth() { return width; }
	public float getHeight() { return height; }
}
