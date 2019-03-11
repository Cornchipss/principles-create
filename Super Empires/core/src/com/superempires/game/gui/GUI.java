package com.superempires.game.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.render.IDrawable;

public class GUI implements IDrawable
{
	private List<GUIElement> elements = new ArrayList<>();
	private List<IInteractable> interactables = new ArrayList<>();
	
	private BitmapFont font = new BitmapFont();
	
	public void update(float delta, FancyCamera cam)
	{
		for(IInteractable i : interactables)
			i.tick(delta, cam);
	}
	
	@Override
	public void drawShapes(ShapeRenderer batch)
	{
		for(GUIElement elem : elements)
			elem.drawShapes(batch);
	}

	@Override
	public void drawLines(ShapeRenderer batch)
	{
		for(GUIElement elem : elements)
			elem.drawLines(batch);
	}

	@Override
	public void drawPolygons(PolygonSpriteBatch batch)
	{
		for(GUIElement elem : elements)
			elem.drawPolygons(batch);
	}

	@Override
	public void drawSprites(SpriteBatch batch)
	{
		for(GUIElement elem : elements)
			elem.drawSprites(batch);
	}
	
	public void addElement(GUIElement e)
	{
		e.setGUI(this);
		elements.add(e);
		
		if(e instanceof IInteractable)
			interactables.add((IInteractable)e);
	}
	
	public void removeElement(GUIElement e)
	{
		if(e.getGUI().equals(this))
			e.setGUI(null);
		
		elements.remove(e);
		
		if(e instanceof IInteractable)
			interactables.remove((IInteractable)e);
	}
	
	public BitmapFont getFont() { return font; }
	public void setFont(BitmapFont f) { this.font = f; }
}
