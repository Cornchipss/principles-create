package com.superempires.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.superempires.game.io.InputManager;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.util.Callback;

public class GUIButton extends GUITextElement implements IInteractable
{
	private boolean hovered = false;
	private Callback onClick;
	
	private Color unhoveredColor = Color.GRAY;
	private Color hoveredColor = Color.WHITE;
	
	private BitmapFont unhoveredFont, hoveredFont;
	
	public GUIButton(Transform transform, GUI gui, BitmapFont font, Callback onClick, String text)
	{
		this(transform, gui, font, font, onClick, text);
	}
	
	public GUIButton(Transform transform, GUI gui, BitmapFont unhovered, BitmapFont hovered, Callback onClick, String text)
	{
		super(transform, text, gui, unhovered);
		
		this.unhoveredFont = unhovered;
		this.hoveredFont = hovered;
		this.onClick = onClick;
	}

	@Override
	public void drawShapes(ShapeRenderer batch)
	{
		Color c = hovered ? hoveredColor : unhoveredColor;
		
		batch.rect(getTransform().getX(), getTransform().getY(), getTransform().getWidth(), getTransform().getHeight(), 
				c, c, c, c);
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
		if(!hoveredFont.equals(unhoveredFont))
		{
			if(hovered && getFont().equals(unhoveredFont))
				setFont(hoveredFont);
			else if(!hovered && getFont().equals(hoveredFont))
				setFont(unhoveredFont);
		}
		
		getFont().draw(batch, getLayout(), getTransform().getX(), getTransform().getY() + getTransform().getHeight() / 2 + getLayout().height / 2);
	}
	
	@Override
	public void tick(float delta, FancyCamera cam)
	{
		Vector2 coords = cam.screenToWorldCoords(Gdx.input.getX(), Gdx.input.getY());
		
		hovered = coords.x > getTransform().getX() && coords.x < getTransform().getX() + getTransform().getWidth() &&
				coords.y > getTransform().getY() && coords.y < getTransform().getY() + getTransform().getHeight();
		
		if(hovered && InputManager.isButtonJustPressed(Input.Buttons.LEFT))
			onClick.run(this);
	}
	
	public boolean isHovered() { return hovered; }
	
	public GUIButton setHoveredColor(Color c) { this.hoveredColor = c; return this; }
	public GUIButton setUnhoveredColor(Color c) { this.unhoveredColor = c; return this; }
	
	public Color getHoveredColor() { return hoveredColor; }
	public Color getUnhoveredColor() { return unhoveredColor; }
}
