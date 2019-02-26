package com.superempires.game.map.tiling;

import com.superempires.game.registry.GameRegistry;

public class TestTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile2", "tiles/tile2.png");
	}
	
	public TestTile(float x, float y)
	{
		super(x, y, GameRegistry.getTexture("tile2"));
	}
	
	@Override
	public double getTravelTime()
	{
		return 5;
	}
}
