package com.superempires.game.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.superempires.game.render.FancyCamera;
import com.superempires.game.render.MasterBatch;
import com.superempires.game.render.RenderQue;

public class GUI
{
	private Map<RenderQue, List<GUIElement>> elementsPerPhase = new HashMap<>();
	private List<GUIElement> elements = new ArrayList<>();
	private List<IInteractable> interactables = new ArrayList<>();
	
	public void update(float delta, FancyCamera cam)
	{
		for(IInteractable i : interactables)
			i.tick(delta, cam);
	}
	
	public void onResize(float w, float h)
	{
		for(GUIElement elem : elements)
			elem.onResize(w, h);
	}
	
	public void draw(MasterBatch batch)
	{
		for(int i = 0; i < RenderQue.MAX_RENDER.getLevel(); i++)
		{			
			List<GUIElement> elems = elementsPerPhase.get(RenderQue.fromInt(i));
			
			if(elems != null)
			{
				for(GUIElement e : elems)
					e.update();
				
				batch.drawAll(elems);
			}
		}
	}
		
	public void addElement(GUIElement e, RenderQue queSpot)
	{
		e.setGUI(this);
		elements.add(e);
		
		List<GUIElement> elems = elementsPerPhase.getOrDefault(queSpot, new LinkedList<GUIElement>());
		elems.add(e);
		elementsPerPhase.put(queSpot, elems);
		
		if(e instanceof IInteractable)
			interactables.add((IInteractable)e);
	}
	
	public void removeElement(GUIElement e)
	{
		if(e.getGUI().equals(this))
			e.setGUI(null);
		
		elements.remove(e);
		
		for(RenderQue que : elementsPerPhase.keySet())
		{
			if(elementsPerPhase.get(que) != null)
				if(elementsPerPhase.get(que).remove(e))
					break;
		}
		
		if(e instanceof IInteractable)
			interactables.remove((IInteractable)e);
	}

	public void dispose()
	{
		for(GUIElement elem : elements)
			elem.dispose();
	}
}
