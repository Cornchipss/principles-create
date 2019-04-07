package com.superempires.game.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.render.MasterBatch;
import com.superempires.game.render.RenderQue;

public class GUI
{
	private Map<RenderQue, List<GUIElement>> elementsPerPhase = new HashMap<>();
	private List<GUIElement> elements = new ArrayList<>();
	private List<IInteractable> interactables = new ArrayList<>();
	
	private List<GUIElement> removes = new ArrayList<>(), adds = new ArrayList<>();
	private List<RenderQue> renderQues = new ArrayList<>();
	
	private boolean locked = false;
	
	public void update(float delta, FancyCamera cam)
	{
		locked = true;
		for(IInteractable i : interactables)
			i.tick(delta, cam);
		locked = false;
		
		clearGarbage();
	}
	
	public void onResize(float w, float h)
	{
		for(GUIElement elem : elements)
			elem.onResize(w, h);
	}
	
	public void draw(MasterBatch batch)
	{
		locked = true;
		for(int i = 0; i < RenderQue.MAX_RENDER.getLevel(); i++)
		{			
			List<GUIElement> elems = elementsPerPhase.get(RenderQue.fromInt(i));
			
			if(elems != null)
			{
				for(GUIElement e : elems)
					e.update();
				
				batch.begin(batch.getPolyBatch());
				for(GUIElement drawable : elems)
					if(!drawable.isHidden())
						drawable.drawPolygons(batch.getPolyBatch());
				batch.end(batch.getPolyBatch());
				
				batch.begin(batch.getShapeBatch(), ShapeType.Filled);
				for(GUIElement drawable : elems)
					if(!drawable.isHidden())
						drawable.drawShapes(batch.getShapeBatch());
				batch.end(batch.getShapeBatch());
				
				batch.begin(batch.getSpriteBatch());
				for(GUIElement drawable : elems)
					if(!drawable.isHidden())
						drawable.drawSprites(batch.getSpriteBatch());
				batch.end(batch.getSpriteBatch());
				
				batch.begin(batch.getShapeBatch(), ShapeType.Line);
				for(GUIElement drawable : elems)
					if(!drawable.isHidden())
						drawable.drawLines(batch.getShapeBatch());
				batch.end(batch.getShapeBatch());
			}
		}
		
		locked = false;
		
		clearGarbage();
	}
		
	public void addElement(GUIElement e, RenderQue queSpot)
	{
		if(!locked)
		{
			e.setGUI(this);
			elements.add(e);
			
			List<GUIElement> elems = elementsPerPhase.getOrDefault(queSpot, new LinkedList<GUIElement>());
			elems.add(e);
			elementsPerPhase.put(queSpot, elems);
			
			if(e instanceof IInteractable)
				interactables.add((IInteractable)e);
		}
		else
		{
			adds.add(e);
			renderQues.add(queSpot);
		}
	}
	
	public void removeElement(GUIElement e)
	{
		if(!locked)
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
			
			e.dispose();
		}
		else
			removes.add(e);
	}

	public void dispose()
	{
		for(GUIElement elem : elements)
			elem.dispose();
	}

	public boolean isWithin(Vector2 coords)
	{
		for(GUIElement elem : elements)
			if(!elem.isHidden() && elem.getTransform().isWithin(coords))
				return true;
		return false;
	}
	
	private void clearGarbage()
	{
		while(adds.size() > 0)
			addElement(adds.remove(adds.size() - 1), renderQues.remove(renderQues.size() - 1));
		
		while(removes.size() > 0)
			removeElement(removes.remove(removes.size() - 1));
	}
}
