package com.superempires.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class GUIText extends GUITextElement
{
	private int alignment;
	
	public GUIText(float x, float y, float w, float h, GUI gui, BitmapFont font)
	{
		this(x, y, w, h, gui, font, "");
	}
	
	public GUIText(float x, float y, float w, float h, GUI gui, BitmapFont font, String text)
	{
		this(x, y, w, h, gui, font, text, Align.center);
	}
	
	public GUIText(float x, float y, float w, float h, GUI gui, BitmapFont font, String text, int alignment)
	{
		super(x, y, text, gui, font);
		
		if(w <= 0)
			w = getTransform().getWidth();
		if(h <= 0)
			h = getTransform().getHeight();
		
		getTransform().setWidth(w);
		getTransform().setHeight(h);
		
		setAlignment(alignment);
		
		update();
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
		getTextBox().draw(batch, 1);
	}
	
	public int getAlignment() { return alignment; }
	public void setAlignment(int alignment)
	{
		if(alignment == -1)
			setAlignment(Align.center);
		else
		{
			getTextBox().setAlignment(alignment);
			this.alignment = alignment;
		}
	}
}
