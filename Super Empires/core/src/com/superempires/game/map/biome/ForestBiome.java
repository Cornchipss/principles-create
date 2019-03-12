package com.superempires.game.map.biome;

import com.superempires.game.map.buildings.natural.Tree;
import com.superempires.game.map.tiling.PlainsTile;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.util.RNG;

public class ForestBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, RNG rdm)
	{
		if(Biome.isFreezing(temperature))
		{
			tiles[y][x] = new SnowTile(x, y, temperature, this);
		}
		else
		{
			tiles[y][x] = new PlainsTile(x, y, temperature, this);
			
			if(rdm.getRandom().nextBoolean())
			{
				tiles[y][x].setBuilding(new Tree(tiles[y][x].getTransform()));
			}
		}
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return false;
	}
}
