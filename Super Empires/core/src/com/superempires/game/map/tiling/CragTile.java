package com.superempires.game.map.tiling;

import com.superempires.game.map.GameMap;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.registry.GameRegistry;

public class CragTile extends Tile
{
	public CragTile(float x, float y, double temperature, Biome biome, GameMap map)
	{
		super(x, y, temperature, GameRegistry.getTexture("tile-crag"), biome, map);
	}

	@Override
	public double getTravelTime()
	{
		return 1;
	}

	@Override
	public boolean isWalkable()
	{
		return true;
	}

	@Override
	public boolean isSailable()
	{
		return false;
	}

	@Override
	public String getName()
	{
		return "Crag Tile";
	}
}
