package com.superempires.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.IDrawable;

public abstract class GUIElement implements IDrawable
{
	private GUI gui;
	private Transform transform;
	
	public GUIElement(Transform transform, GUI gui)
	{
		this.transform = transform;
		this.gui = gui;
	}
	
	public abstract void fontChangedEvent(BitmapFont f);
	
	public void setTransform(float x, float y, float width, float height) { setTransform(new Transform(x, y, width, height)); }
	public void setTransform(Transform t) { this.transform = t; }
	public Transform getTransform() { return transform; }

	public GUI getGUI() { return gui; }
	public void setGUI(GUI gui) { this.gui = gui; }

	public static Transform transformFromText(float x, float y, String text, BitmapFont font)
	{
		GlyphLayout layout = new GlyphLayout(font, text);
		return new Transform(x, y, layout.width, layout.height);
	}
	
	public void onResize(float w, float h)
	{
		
	}
	
	public void update()
	{
		
	}

	public void dispose()
	{
		
	}
}
