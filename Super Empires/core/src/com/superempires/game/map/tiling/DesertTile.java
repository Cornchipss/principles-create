package com.superempires.game.map.tiling;

import com.superempires.game.map.tiling.template.TileTemplate;
import com.superempires.game.registry.GameRegistry;

public class DesertTile extends Tile
{
	static
	{
		GameRegistry.registerTexture("tile-desert", "tiles/tile-sand.png");
	}
	
	public DesertTile(float x, float y)
	{
		super(x, y, GameRegistry.getTexture("tile-desert"));
	}
	
	public static class DesertTileTemplate implements TileTemplate
	{
		@Override
		public Tile createTile(float x, float y)
		{
			return new DesertTile(x, y);
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
