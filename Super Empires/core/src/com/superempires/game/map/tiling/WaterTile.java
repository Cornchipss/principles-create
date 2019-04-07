package com.superempires.game.map.tiling;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.registry.GameRegistry;

public class WaterTile extends Tile
{
	public WaterTile(float x, float y, double temperature, Biome biome)
	{
		super(x, y, temperature, GameRegistry.getTexture("tile-water"), biome);
	}

	@Override
	public double getTravelTime()
	{
		return 0;
	}

	@Override
	public boolean isWalkable()
	{
		return false;
	}

	@Override
	public boolean isSailable()
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "Water Tile";
	}
}
