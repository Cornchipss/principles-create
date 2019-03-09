package com.superempires.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	
	public abstract void changeFont(BitmapFont f);
	
	public void setTransform(Transform t) { this.transform = t; }
	public Transform getTransform() { return transform; }

	public GUI getGUI() { return gui; }
	public void setGUI(GUI gui) { this.gui = gui; }
}
