package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.tiling.DesertTile;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.util.RNG;

public class BeachBiome extends Biome
{
	@Override
	public void generateTile(GameMap map, int x, int y, double temperature, RNG rdm)
	{
		if(Biome.isFreezing(temperature))
			map.setTile(x, y, new SnowTile(x, y, temperature, this, map));
		else
			map.setTile(x, y, new DesertTile(x, y, temperature, this, map));
	}

	@Override
	public Color getShadingColor()
	{
		return Color.GREEN;
	}
	
	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return false;
	}

	@Override
	public BiomeType getType()
	{
		return BiomeType.LAND;
	}
}
