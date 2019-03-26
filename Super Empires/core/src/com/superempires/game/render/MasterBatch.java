package com.superempires.game.render;

import java.util.List;

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
	
	public void drawAll(List<? extends IDrawable> elems)
	{
		getPolyBatch().begin();
		for(IDrawable drawable : elems)
			drawable.drawPolygons(getPolyBatch());
		getPolyBatch().end();
		
		getShapeBatch().begin(ShapeType.Filled);
		for(IDrawable drawable : elems)
			drawable.drawShapes(getShapeBatch());
		getShapeBatch().end();
		
		getSpriteBatch().begin();
		for(IDrawable drawable : elems)
			drawable.drawSprites(getSpriteBatch());
		getSpriteBatch().end();
		
		getShapeBatch().begin(ShapeType.Line);
		for(IDrawable drawable : elems)
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
