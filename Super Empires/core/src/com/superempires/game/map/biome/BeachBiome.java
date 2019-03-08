package com.superempires.game.map.biome;

import java.util.Random;

import com.superempires.game.map.tiling.DesertTile;
import com.superempires.game.map.tiling.Tile;

public class BeachBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm)
	{
		tiles[y][x] = new DesertTile(x, y, temperature, this);
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return false;
	}
}
