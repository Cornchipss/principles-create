package com.superempires.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.util.Callback;

public class GUIButton extends GUIElement implements IInteractable
{
	private GlyphLayout layout;
	private String text;
	private boolean hovered = false;
	private Callback onClick;
	
	public GUIButton(Transform transform, GUI gui, Callback onClick, String text)
	{
		super(transform, gui);
		
		this.onClick = onClick;
		this.text = text;
		
		layout = new GlyphLayout(getGUI().getFont(), text, Color.BLACK, getTransform().getWidth(), 1, false);
	}

	@Override
	public void drawShapes(ShapeRenderer batch)
	{
		Color c = hovered ? Color.WHITE : Color.GRAY;
		
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
		getGUI().getFont().draw(batch, layout, getTransform().getX(), getTransform().getY() + getTransform().getHeight() / 2 + layout.height / 2);
	}
	
	@Override
	public void tick(float delta, FancyCamera cam)
	{
		Vector2 coords = cam.screenToWorldCoords(Gdx.input.getX(), Gdx.input.getY());
		
		hovered = coords.x > getTransform().getX() && coords.x < getTransform().getX() + getTransform().getWidth() &&
				coords.y > getTransform().getY() && coords.y < getTransform().getY() + getTransform().getHeight();
				
		if(hovered && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			onClick.run(this);
	}
	
	public void setText(String text)
	{
		this.text = text;
		layout.setText(getGUI().getFont(), text);
	}
	
	public String getText() { return text; }

	@Override
	public void changeFont(BitmapFont f)
	{
		layout = new GlyphLayout(f, text);
	}
}
