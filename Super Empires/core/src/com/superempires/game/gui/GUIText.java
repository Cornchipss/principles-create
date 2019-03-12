package com.superempires.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GUIText extends GUITextElement
{
	private Alignment alignment;
	private float scale;
	
	public GUIText(float x, float y, GUI gui, BitmapFont font)
	{
		this(x, y, gui, font, "", Color.WHITE, 1);
	}
	
	public GUIText(float x, float y, GUI gui, BitmapFont font, String text, Color color, float scale)
	{
		this(x, y, gui, font, text, color, scale, Alignment.CENTER);
	}
	
	public GUIText(float x, float y, GUI gui, BitmapFont font, String text, Color color, float scale, Alignment align)
	{
		super(x, y, text, gui, font, color);
		
		this.alignment = align;
		this.scale = scale;
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
		float x = getTransform().getX();
		float y = getTransform().getY();
		
		if(alignment == Alignment.CENTER)
			x -= getLayout().width / 2;
		
		getFont().getData().setScale(scale);
		
		getFont().draw(batch, getLayout(), x, y + getLayout().height);
	}
	
	@Override
	public void dispose()
	{
		getFont().dispose();
	}
}
