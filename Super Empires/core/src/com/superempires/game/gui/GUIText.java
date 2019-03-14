package com.superempires.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GUIText extends GUITextElement
{
	private Alignment alignment;
	
	public GUIText(float x, float y, float w, float h, GUI gui, BitmapFont font)
	{
		this(x, y, w, h, gui, font, "");
	}
	
	public GUIText(float x, float y, float w, float h, GUI gui, BitmapFont font, String text)
	{
		this(x, y, w, h, gui, font, text, Alignment.CENTER);
	}
	
	public GUIText(float x, float y, float w, float h, GUI gui, BitmapFont font, String text, Alignment alignment)
	{
		super(x, y, text, gui, font);
		
		if(w <= 0)
			w = getTransform().getWidth();
		if(h <= 0)
			h = getTransform().getHeight();
		
		getTransform().setWidth(w);
		getTransform().setHeight(h);
		
		setAlignment(alignment);
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
	
	@Override
	public void dispose()
	{
		getFont().dispose();
	}
	
	public Alignment getAlignment() { return alignment; }
	public void setAlignment(Alignment alignment)
	{
		if(alignment == null)
			setAlignment(Alignment.CENTER);
		
		float x = getTransform().getX();
		float y = getTransform().getY();
		
		if(getAlignment() != null)
		{			
			if(alignment.center())
				x -= getTransform().getWidth() / 2;
			else if(alignment.right())
				x -= getTransform().getWidth();
			
			if(alignment.bottom())
				y += getTransform().getHeight() / 2 - getLayout().height / 2;
			if(alignment.top())
				y -= getTransform().getHeight() / 2 - getLayout().height / 2;
		}
		
		this.alignment = alignment;
		
		if(alignment.center())
			x += getTransform().getWidth() / 2 - getLayout().width / 2;
		else if(alignment.right())
			x += getTransform().getWidth() - getLayout().width;
		
		if(alignment.bottom())
			y -= getTransform().getHeight() / 2 - getLayout().height / 2;
		if(alignment.top())
			y += getTransform().getHeight() / 2 - getLayout().height / 2;
		
		getTextBox().setBounds(x, y, getTransform().getWidth(), getTransform().getHeight());
		
		this.alignment = alignment;
	}
}
