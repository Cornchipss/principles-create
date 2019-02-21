package com.superempires.game.map.troops;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.render.IDrawable;

public class Unit extends PhysicalObject implements IDrawable
{
	static
	{
		GameRegistry.registerTexture("unit", "units/unit.png");
	}
	
	private Texture texture = GameRegistry.getTexture("unit");
	
	public Unit(float x, float y)
	{
		super(x, y);
	}
	
	@Override
	public void drawShapes(ShapeRenderer batch)
	{
		
	}

	@Override
	public void drawPolygons(PolygonSpriteBatch batch)
	{
		
	}

	@Override
	public void drawSprites(SpriteBatch batch)
	{
		batch.draw(texture, getTransform().getX(), getTransform().getY(), getTransform().getWidth(), getTransform().getHeight());
	}

	@Override
	public void drawLines(ShapeRenderer batch)
	{
		
	}
}
