package com.superempires.game.map.biome;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.map.tiling.Tile;

public class SnowPlainsBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm)
	{
		tiles[y][x] = new SnowTile(x, y, temperature, this);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return temperature <= 32;
	}
	
	@Override
	public String toString() { return "Snow Biome @ " + hashCode(); }
}
