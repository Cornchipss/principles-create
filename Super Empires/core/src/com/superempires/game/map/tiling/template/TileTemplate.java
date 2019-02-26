package com.superempires.game.map.tiling.template;

import com.superempires.game.map.tiling.Tile;

public interface TileTemplate
{
	public Tile createTile(float x, float y);
}
