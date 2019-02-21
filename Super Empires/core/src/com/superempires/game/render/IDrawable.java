package com.superempires.game.render;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IDrawable
{
	public void drawShapes(ShapeRenderer batch);
	public void drawLines(ShapeRenderer batch);
	public void drawPolygons(PolygonSpriteBatch batch);
	public void drawSprites(SpriteBatch batch);
}
