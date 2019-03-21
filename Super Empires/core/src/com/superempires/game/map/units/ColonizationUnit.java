package com.superempires.game.map.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;

public class ColonizationUnit extends Unit
{
	private Texture texture = GameRegistry.getTexture("unit-colonization");
	
	public ColonizationUnit(Transform transform)
	{
		super(transform);
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
	
	@Override
	public double getTravelRadius()
	{
		return 3.5;
	}
}
