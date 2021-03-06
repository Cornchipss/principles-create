package com.superempires.game.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.RenderQue;

public class GUIElementHolder extends GUIElement
{
	private List<GUIElement> elems = new ArrayList<>();
	private Map<GUIElement, Integer> alignments = new HashMap<>();
	
	private Color background = new Color(0, 0, 0, 0);
	
	public GUIElementHolder(Transform transform, GUI gui)
	{
		super(transform, gui);
	}
	
	public GUIElementHolder setBackground(Color c)
	{
		this.background = c;
		return this;
	}
	
	public GUIElementHolder addElement(GUIElement elem, RenderQue que)
	{
		return addElement(elem, Align.left, que);
	}
	
	public GUIElementHolder addElement(GUIElement elem, int align, RenderQue que)
	{
		elems.add(elem);
		
		elem.getTransform().setX(getTransform().getX() + elem.getTransform().getX());
		elem.getTransform().setY(getTransform().getY() + elem.getTransform().getY());
		
		getGUI().addElement(elem, que);
		
		
		alignments.put(elem, align);
		
		return this;
	}
	
	public void removeElement(GUIElement elem)
	{
		elems.remove(elem);
		alignments.remove(elem);

		getGUI().removeElement(elem);
	}
	
	@Override
	public void setTransform(Transform t)
	{
		for(GUIElement elem : elems)
		{
			float difY = getTransform().getY() - t.getY();
			
			elem.getTransform().setY(elem.getTransform().getY() - difY);
		}
		
		super.setTransform(t);
	}
	
	@Override
	public void setHidden(boolean b)
	{
		super.setHidden(b);
		
		for(GUIElement elem : elems)
			elem.setHidden(b);
	}
	
	@Override
	public void drawShapes(ShapeRenderer batch)
	{		
		if(background.a != 0)
			batch.rect(getTransform().getX(), getTransform().getY(), 
					getTransform().getWidth(), getTransform().getHeight(), 
					background, background, background, background);
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
		
	}

	@Override
	public void fontChangedEvent(BitmapFont f)
	{
		
	}

	public List<GUIElement> getElements()
	{
		return elems;
	}

	public void removeAllElements()
	{
		while(elems.size() > 0)
		{
			removeElement(elems.get(elems.size() - 1));
		}
	}
}
