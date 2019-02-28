package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.map.tiling.Tile;

public class SnowBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature)
	{
		tiles[y][x] = new SnowTile(x, y, temperature, this);
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
		return 10;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return temperature <= 32;
	}
	
	@Override
	public String toString() { return "Snow Biome @ " + hashCode(); }
}
