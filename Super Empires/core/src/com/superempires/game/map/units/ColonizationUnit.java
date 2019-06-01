package com.superempires.game.map.units;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.actions.Action;
import com.superempires.game.map.buildings.town.TownHall;
import com.superempires.game.map.units.troops.BasicTroop;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.util.Callback;

public class ColonizationUnit extends Unit
{
	private Texture texture = GameRegistry.getTexture("unit-colonization");
	
	public ColonizationUnit(Transform transform, GameMap map)
	{
		super(transform, map);
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

	@Override
	public List<Action> getActions()
	{
		final ColonizationUnit unit = this;
		
		return Arrays.asList(new Action("Settle", GameRegistry.getTexture("tile-grass"), new Callback()
		{
			@Override
			public void run(Object... args)
			{
				unit.getTile().setBuilding(new TownHall(unit.getTile().getTransform(), getMap()));
				unit.getTile().setUnit(new BasicTroop(unit.getTile().getTransform(), getMap()), true);
			}
		}));
	}
}
