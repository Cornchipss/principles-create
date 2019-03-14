package com.superempires.game.map.buildings.natural;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.map.buildings.Building;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;

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
}
