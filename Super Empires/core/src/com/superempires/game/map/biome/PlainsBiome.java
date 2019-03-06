package com.superempires.game.map.biome;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.PlainsTile;
import com.superempires.game.map.tiling.Tile;

public class PlainsBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm)
	{
		tiles[y][x] = new PlainsTile(x, y, temperature, this);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}
	
	@Override
	public int getRarity(double temperature)
	{
		return 100;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return 60 <= temperature && temperature <= 90;
	}
}
