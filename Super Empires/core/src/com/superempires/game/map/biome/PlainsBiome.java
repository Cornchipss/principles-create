package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.PlainsTile;
import com.superempires.game.map.tiling.Tile;

public class PlainsBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature)
	{
		tiles[y][x] = new PlainsTile(x, y, temperature, this);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}
	
	@Override
	public int getRarity()
	{
		return 100;
	}

	@Override
	public float getPreferredAverageTemperature()
	{
		return 80;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return 60 <= temperature && temperature <= 90;
	}
}
