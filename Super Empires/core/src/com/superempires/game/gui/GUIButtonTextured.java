package com.superempires.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.util.Callback;

public class GUIButtonTextured extends GUIButton
{
	private Texture texture;
	
	public GUIButtonTextured(Transform transform, GUI gui, BitmapFont font, Callback onClick, String text, Texture texture)
	{
		super(transform, gui, font, onClick, text);
		
		setHoveredColor(Color.WHITE);
		setUnhoveredColor(Color.WHITE);
		
		this.texture = texture;
	}
	
	@Override
	public void drawSprites(SpriteBatch batch)
	{
		batch.setColor(isHovered() ? getHoveredColor() : getUnhoveredColor());
		
		batch.draw(texture, getTransform().getX(), getTransform().getY(), getTransform().getWidth(), getTransform().getHeight());
		
		batch.setColor(Color.WHITE);
		
		super.drawSprites(batch); // Draws the text
	}
}
