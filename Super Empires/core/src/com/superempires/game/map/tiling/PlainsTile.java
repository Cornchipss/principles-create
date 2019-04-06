package com.superempires.game.map.tiling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.buildings.natural.Tree;
import com.superempires.game.map.tiling.actions.TileAction;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.util.Callback;

public class PlainsTile extends Tile
{
	public PlainsTile(float x, float y, double temperature, Biome biome)
	{
		super(x, y, temperature, GameRegistry.getTexture("tile-grass"), biome);
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
	public List<TileAction> getActions()
	{
		if(getBuilding() == null)
			return Arrays.asList(new TileAction("Plant Crops", GameRegistry.getTexture("button-plant"), 
			new Callback()
			{
				@Override
				public void run(Object... args)
				{
					setBuilding(new Tree(getTransform(), getBiome().getShadingColor()));
				}
			}));
		else
			return new ArrayList<>();
	}
}
