package com.superempires.game.map.tiling;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.render.IDrawable;
import com.superempires.game.render.MasterBatch;

public class Tile extends PhysicalObject implements IDrawable
{
	static
	{
		GameRegistry.registerTexture("tile", "tiles/texture.png");
	}
	
	private Texture texture = GameRegistry.getTexture("tile");
	
	public static final float WIDTH = 64 * 4;
	public static final float HEIGHT = 64 * 3;
	
	private static final float[] vertices = new float[]
	{
		// 4/5 and 1/5 are the best ratios for making good hexagons I've found	
		WIDTH * 1 / 4, 0    ,
		WIDTH * 3 / 4, 0    ,
		WIDTH        , HEIGHT / 2,
		WIDTH * 3 / 4, HEIGHT    ,
		WIDTH     / 4, HEIGHT    ,
		0            , HEIGHT / 2,
		WIDTH     / 4, 0      // This last one is for line drawing
	};
	
	private static final short[] indicies = new short[]
	{
		// Make sure they connect
	    0, 5, 4,
	    4, 3, 0,
	    0, 3, 1,
	    1, 2, 3
	    // Makes a decent looking hexagon
	};
	
	private PolygonSprite polygonSprite;
	private boolean highlighted = false;
	
	public Tile(float x, float y)
	{
		super(x, y);
		
		PolygonRegion polyReg = new PolygonRegion(new TextureRegion(texture), vertices, indicies);
		
		polygonSprite = new PolygonSprite(polyReg);
		polygonSprite.setPosition(getTransform().getX(), getTransform().getY());
		polygonSprite.setOrigin(getTransform().getCenter().x, getTransform().getCenter().y);
	}
	
	public void draw(MasterBatch batch)
	{
		
	}

	@Override
	public void drawShapes(ShapeRenderer batch)
	{
		
	}
	
	@Override
	public void drawLines(ShapeRenderer batch)
	{
		Color c = highlighted ? Color.BLUE : Color.BLACK;
		
		for(int i = 0; i + 2 < vertices.length; i += 2)
		{
			batch.line(
					vertices[i] + getTransform().getX(), 
					vertices[i + 1] + getTransform().getY(), 
					vertices[i + 2] + getTransform().getX(), 
					vertices[i + 3] + getTransform().getY(), 
					c, c);
		}
	}

	@Override
	public void drawPolygons(PolygonSpriteBatch batch)
	{
		polygonSprite.draw(batch, highlighted ? 0.25f : 1);
	}

	@Override
	public void drawSprites(SpriteBatch batch)
	{
		
	}

	public void setHighlighted(boolean b)
	{
		highlighted = b;
	}
}
