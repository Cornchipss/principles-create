package com.superempires.game.map.tiling;

import com.superempires.game.map.tiling.template.TileTemplate;
import com.superempires.game.registry.GameRegistry;

public class PlainsTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile-grass", "tiles/tile-grass.png");
	}
	
	public PlainsTile(float x, float y)
	{
		super(x, y, GameRegistry.getTexture("tile-grass"));
	}
	
	public static class PlainsTileTemplate implements TileTemplate
	{
		@Override
		public Tile createTile(float x, float y)
		{
			return new PlainsTile(x, y);
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
		return 1;
	}
}
