package com.superempires.game.render;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.superempires.game.util.GLHelper;

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
		begin(getPolyBatch());
		for(IDrawable drawable : elems)
			drawable.drawPolygons(getPolyBatch());
		end(getPolyBatch());
		
		begin(getShapeBatch(), ShapeType.Filled);
		for(IDrawable drawable : elems)
			drawable.drawShapes(getShapeBatch());
		end(getShapeBatch());
		
		begin(getSpriteBatch());
		for(IDrawable drawable : elems)
			drawable.drawSprites(getSpriteBatch());
		end(getSpriteBatch());
		
		begin(getShapeBatch(), ShapeType.Line);
		for(IDrawable drawable : elems)
			drawable.drawLines(getShapeBatch());
		end(getShapeBatch());
	}
	
	public SpriteBatch getSpriteBatch() { return spriteBatch; }
	
	public ShapeRenderer getShapeBatch() { return shapeBatch; }
	
	public PolygonSpriteBatch getPolyBatch() { return polyBatch; }
	
	public void begin(Object batch, ShapeType... type)
	{
		if(type.length > 0 && type[0] != null && batch instanceof ShapeRenderer)
			((ShapeRenderer)batch).begin(type[0]);
		else
		{
			if(batch instanceof Batch)
				((Batch)batch).begin();
			else
				throw new IllegalArgumentException("First argument must be of type Batch or ShapeRenderer");
		}
		GLHelper.enableTransparency();
	}
	
	public void end(Object batch)
	{
		if(batch instanceof Batch)
			((Batch)batch).end();
		else if(batch instanceof ShapeRenderer)
			((ShapeRenderer) batch).end();
		else
			throw new IllegalArgumentException("First argument must be of type Batch or ShapeRenderer");
	}
	
	public void setProjectionMatrix(Matrix4 combined)
	{
		getShapeBatch().setProjectionMatrix(combined);
		getSpriteBatch().setProjectionMatrix(combined);
		getPolyBatch().setProjectionMatrix(combined);
	}
	
	public void dispose()
	{
		getSpriteBatch().dispose();
		getShapeBatch().dispose();
		getPolyBatch().dispose();
		
		spriteBatch = null;
		shapeBatch = null;
		polyBatch = null;
	}
}
