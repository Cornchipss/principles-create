package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.superempires.game.map.tiling.DesertTile;
import com.superempires.game.map.tiling.Tile;

public class DesertBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y)
	{
		tiles[y][x] = new DesertTile(x, y);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}

	@Override
	public Vector2 getAverageTemperatureRange()
	{
		return new Vector2(90, 130);
	}

	@Override
	public Vector2 getAverageHumidityRange()
	{
		return new Vector2(0, 30);
	}

	@Override
	public float getRarity()
	{
		return 100;
	}

	@Override
	public float getPreferredAverageTemperature()
	{
		return 100;
	}
}
