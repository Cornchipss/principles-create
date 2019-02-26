package com.superempires.game.map.tiling;

import com.superempires.game.map.tiling.template.TileTemplate;
import com.superempires.game.registry.GameRegistry;

public class SlowTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile-slow", "tiles/tile2.png");
	}
	
	public SlowTile(float x, float y)
	{
		super(x, y, GameRegistry.getTexture("tile-slow"));
	}
	
	@Override
	public double getTravelTime()
	{
		return 3;
	}
	
	public static class SlowTileTemplate implements TileTemplate
	{
		@Override
		public Tile createTile(float x, float y)
		{
			return new SlowTile(x, y);
		}
	}

	@Override
	public boolean isWalkable()
	{
		return true;
	}
}
