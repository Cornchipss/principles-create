package com.superempires.game.map.biome;

import java.util.Random;

import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.map.tiling.Tile;

public class SnowForestBiome extends Biome
{

	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm)
	{
		tiles[y][x] = new SnowTile(x, y, temperature, this);
	}

	@Override
	public boolean isAcceptableTemperature(double temperature) {
		// TODO Auto-generated method stub
		return false;
	}

}
