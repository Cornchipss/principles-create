package com.superempires.game.map.tiling;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.registry.GameRegistry;

public class DesertTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile-desert", "tiles/tile-sand.png");
	}
	
	public DesertTile(float x, float y, double temperature, Biome biome)
	{
		super(x, y, temperature, GameRegistry.getTexture("tile-desert"), biome);
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
