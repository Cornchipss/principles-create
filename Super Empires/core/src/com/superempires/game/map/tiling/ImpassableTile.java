package com.superempires.game.map.tiling;

import com.superempires.game.map.tiling.template.TileTemplate;
import com.superempires.game.registry.GameRegistry;

public class ImpassableTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("impassable-tile", "tiles/tile-impassable.png");
	}
	
	public ImpassableTile(float x, float y)
	{
		super(x, y, GameRegistry.getTexture("impassable-tile"));
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
	
	public static class ImpassableTileTemplate implements TileTemplate
	{
		@Override
		public Tile createTile(float x, float y)
		{
			return new ImpassableTile(x, y);
		}
	}
}
