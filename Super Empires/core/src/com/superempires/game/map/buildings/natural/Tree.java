package com.superempires.game.map.buildings.natural;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.map.actions.Action;
import com.superempires.game.map.buildings.Building;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.util.Callback;

public class Tree extends Building
{
	private Texture bottom = GameRegistry.getTexture("building-tree-top");
	private Texture top = GameRegistry.getTexture("building-tree-bottom");
	
	private Color leafColor;
	
	public Tree(Transform t, Color leafColor)
	{
		super(t);
		
		this.leafColor = leafColor;
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
		batch.setColor(leafColor);
		batch.draw(bottom, getTransform().getX(), getTransform().getY(), 
				getTransform().getWidth(), getTransform().getHeight());
		
		batch.setColor(Color.WHITE);
		batch.draw(top, getTransform().getX(), getTransform().getY(), 
				getTransform().getWidth(), getTransform().getHeight());
	}

	@Override
	public List<Action> getActions()
	{
		final Tree instance = this;
		
		return Arrays.asList(new Action("Chop", GameRegistry.getTexture("tile-grass"), new Callback()
		{
			@Override
			public void run(Object... args)
			{
				instance.getTile().setBuilding(null);
			}
		}));
	}
}
