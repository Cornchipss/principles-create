package com.superempires.game.render;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class MasterBatch
{
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeBatch;
	private PolygonSpriteBatch polyBatch;
	
	public MasterBatch()
	{
		this.spriteBatch = new SpriteBatch();
		this.shapeBatch = new ShapeRenderer();
		this.polyBatch = new PolygonSpriteBatch();
	}
	
	/**
	 * Quick method of drawing a drawable object
	 * @param drawable The thing that's drawable
	 */
	public void draw(IDrawable drawable)
	{
		getPolyBatch().begin();
		drawable.drawPolygons(getPolyBatch());
		getPolyBatch().end();
		
		getShapeBatch().begin(ShapeType.Filled);
		drawable.drawShapes(getShapeBatch());
		getShapeBatch().end();
		
		getSpriteBatch().begin();
		drawable.drawSprites(getSpriteBatch());
		getSpriteBatch().end();
		
		getShapeBatch().begin(ShapeType.Line);
		drawable.drawLines(getShapeBatch());
		getShapeBatch().end();
	}
	
	public SpriteBatch getSpriteBatch() { return spriteBatch; }
	
	public ShapeRenderer getShapeBatch() { return shapeBatch; }
	
	public PolygonSpriteBatch getPolyBatch() { return polyBatch; }
	
	public void setProjectionMatrix(Matrix4 combined)
	{
		shapeBatch.setProjectionMatrix(combined);
		spriteBatch.setProjectionMatrix(combined);
		polyBatch.setProjectionMatrix(combined);
	}
	
	public void dispose()
	{
		spriteBatch.dispose();
		shapeBatch.dispose();
		polyBatch.dispose();
	}
}
