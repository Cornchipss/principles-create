package com.superempires.game.map.troops;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.render.IDrawable;

public class Unit extends PhysicalObject implements IDrawable
{
	private Texture texture = GameRegistry.getTexture("unit");
	private Tile tile;
	
	public Unit(Transform transform)
	{
		super(transform);
	}

	public void setTile(Tile tile) { this.tile = tile; }
	public Tile getTile() { return tile; }
	
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
