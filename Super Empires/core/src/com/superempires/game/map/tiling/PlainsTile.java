package com.superempires.game.map.tiling;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.registry.GameRegistry;

public class PlainsTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile-grass", "tiles/tile-grass.png");
	}
	
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
}
