package com.superempires.game.map.biome;

import com.superempires.game.map.tiling.DesertTile;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.util.RNG;

public class BeachBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, RNG rdm)
	{
		if(Biome.isFreezing(temperature))
			tiles[y][x] = new SnowTile(x, y, temperature, this);
		else
			tiles[y][x] = new DesertTile(x, y, temperature, this);
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return false;
	}
}
