package com.superempires.game.map.units.troops;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.actions.Action;
import com.superempires.game.map.units.Unit;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;

public class BasicTroop extends Unit
{
	private Texture texture = GameRegistry.getTexture("unit-troop-basic");
	
	public BasicTroop(Transform transform, GameMap map)
	{
		super(transform, map);
	}

	@Override
	public void drawShapes(ShapeRenderer batch)
	{
		
	}

	@Override
	public void drawLines(ShapeRenderer batch)
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
	public List<Action> getActions()
	{
		return new ArrayList<>();
	}

	@Override
	public double getTravelRadius()
	{
		return 3.5;
	}
}
