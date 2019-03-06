package com.superempires.game.map.biome;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.IceTile;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.tiling.WaterTile;

public class OceanBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm)
	{
		if(temperature < 32 && ((int)temperature <= 0 || rdm.nextInt((int)temperature) < 8))
		{
			tiles[y][x] = new IceTile(x, y, temperature, this);
		}
		else
			tiles[y][x] = new WaterTile(x, y, temperature, this);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}

	@Override
	public int getRarity(double temperature)
	{
		if(temperature <= 26)
		{
			return Math.abs((int)((temperature - 32) * 8) * 2);
		}
		else
		{
			return (int)(100 * 50 / temperature);
		}
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return true;
	}
}
