package com.superempires.game.map.tiling;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.actions.Action;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.buildings.natural.Tree;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.util.Callback;
import com.superempires.game.util.Helper;

public class SnowTile extends Tile
{
	public SnowTile(float x, float y, double temperature, Biome biome, GameMap map)
	{
		super(x, y, temperature, GameRegistry.getTexture("tile-snow"), biome, map);
	}
	
	@Override
	public boolean isWalkable()
	{
		return true;
	}

	@Override
	public double getTravelTime()
	{
		return 1.5;
	}

	@Override
	public boolean isSailable()
	{
		return false;
	}

	@Override
	public String getName()
	{
		return "Snow Tile";
	}
	
	@Override
	public List<Action> getActions()
	{
		return Helper.combineLists(super.getActions(), Arrays.asList(new Action("Plant Crops", GameRegistry.getTexture("button-plant"), 
				new Callback()
				{
					@Override
					public void run(Object... args)
					{
						setBuilding(new Tree(getTransform(), getMap(), Color.WHITE));
					}
				})));
	}
}
