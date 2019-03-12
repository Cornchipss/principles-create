package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.PlainsTile;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.util.RNG;

public class PlainsBiome extends Biome
{
	@Override
	public void generateTile(Tile[][] tiles, int x, int y, double temperature, RNG rdm)
	{
		if(Biome.isFreezing(temperature))
			tiles[y][x] = new SnowTile(x, y, temperature, this);
		else
			tiles[y][x] = new PlainsTile(x, y, temperature, this);
	}

	@Override
	public Color getShadingColor()
	{
		return Color.WHITE;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return 60 <= temperature && temperature <= 90;
	}
}
