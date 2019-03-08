package com.superempires.game.map.biome;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.CragTile;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.map.tiling.Tile;

public class CragBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm)
	{
		if(Biome.isFreezing(temperature))
			tiles[y][x] = new SnowTile(x, y, temperature, this);
		else
			tiles[y][x] = new CragTile(x, y, temperature, this);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return temperature >= 100;
	}

}
