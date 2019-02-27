package com.superempires.game.map.tiling;

import com.superempires.game.map.tiling.template.TileTemplate;
import com.superempires.game.registry.GameRegistry;

public class SnowTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile-snow", "tiles/tile-snow.png");
	}
	
	public SnowTile(float x, float y)
	{
		super(x, y, GameRegistry.getTexture("tile-snow"));
	}
	
	public static class SnowTileTemplate implements TileTemplate
	{
		@Override
		public Tile createTile(float x, float y)
		{
			return new SnowTile(x, y);
		}
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
}
