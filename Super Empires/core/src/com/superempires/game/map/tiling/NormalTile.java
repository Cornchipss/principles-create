package com.superempires.game.map.tiling;

import com.superempires.game.map.tiling.template.TileTemplate;

public class NormalTile extends Tile
{
	public NormalTile(float x, float y)
	{
		super(x, y);
	}

	@Override
	public double getTravelTime()
	{
		return 1;
	}
	
	public static class NormalTileTemplate implements TileTemplate
	{
		@Override
		public Tile createTile(float x, float y)
		{
			return new NormalTile(x, y);
		}
	}
	
	@Override
	public String toString() { return "hayt"; }
}
