package com.superempires.game.map.biome;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.CragTile;
import com.superempires.game.map.tiling.Tile;

public class CragBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm)
	{
		tiles[y][x] = new CragTile(x, y, temperature, this);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}

	@Override
	public int getRarity(double temperature)
	{
		return 20;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return temperature >= 100;
	}

}
