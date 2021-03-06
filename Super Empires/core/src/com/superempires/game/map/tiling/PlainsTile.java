package com.superempires.game.map.tiling;

import java.util.Arrays;
import java.util.List;

import com.superempires.game.map.GameMap;
import com.superempires.game.map.actions.Action;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.buildings.natural.Tree;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.util.Callback;
import com.superempires.game.util.Helper;

public class PlainsTile extends Tile
{
	public PlainsTile(float x, float y, double temperature, Biome biome, GameMap map)
	{
		super(x, y, temperature, GameRegistry.getTexture("tile-grass"), biome, map);
	}

	@Override
	public boolean isWalkable()
	{
		return true;
	}

	@Override
	public double getTravelTime()
	{
		return 1;
	}

	@Override
	public boolean isSailable()
	{
		return false;
	}
	
	@Override
	public String getName()
	{
		return "Plains Tile";
	}
	
	@Override
	public List<Action> getActions()
	{
		if(getBuilding() == null)
			return Helper.combineLists(super.getActions(), Arrays.asList(new Action("Plant Tree", GameRegistry.getTexture("button-plant"), 
			new Callback()
			{
				@Override
				public void run(Object... args)
				{
					setBuilding(new Tree(getTransform(), getMap(), getBiome().getShadingColor()));
				}
			}),
			new Action("sysout", GameRegistry.getTexture("tile-crag"), 
			new Callback()
			{
				@Override
				public void run(Object... args)
				{
					System.out.println("Plains Button Pressed");
				}
			})));
		else
			return super.getActions();
	}
}
