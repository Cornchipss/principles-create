package com.superempires.game.map.tiling;

import com.superempires.game.map.tiling.template.TileTemplate;
import com.superempires.game.registry.GameRegistry;

public class QuickTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile-quick", "tiles/tile3.png");
	}
	
	public QuickTile(float x, float y)
	{
		super(x, y, GameRegistry.getTexture("tile-quick"));
	}

	@Override
	public double getTravelTime()
	{
		return 0.4;
	}
	
	public static class QuickTileTemplate implements TileTemplate
	{
		@Override
		public Tile createTile(float x, float y)
		{
			return new QuickTile(x, y);
		}
	}

	@Override
	public boolean isWalkable()
	{
		return true;
	}
}
