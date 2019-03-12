package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.IceTile;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.tiling.WaterTile;
import com.superempires.game.util.RNG;

public class OceanBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, RNG rdm)
	{
		if(Biome.isFreezing(temperature) && ((int)temperature <= 0 || rdm.getRandom().nextInt((int)temperature) < 8))
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
	public boolean isAcceptableTemperature(double temperature)
	{
		return true;
	}
}
