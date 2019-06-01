package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.tiling.IceTile;
import com.superempires.game.map.tiling.WaterTile;
import com.superempires.game.util.RNG;

public class OceanBiome extends Biome
{
	@Override
	public void generateTile(GameMap map, int x, int y, double temperature, RNG rdm)
	{
		if(Biome.isFreezing(temperature) && ((int)temperature <= 0 || rdm.getRandom().nextInt((int)temperature) < 8))
		{
			map.setTile(x, y, new IceTile(x, y, temperature, this, map));
		}
		else
			map.setTile(x, y, new WaterTile(x, y, temperature, this, map));
	}

	@Override
	public Color getShadingColor()
	{
		return Color.GREEN;
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return true;
	}
	
	@Override
	public BiomeType getType()
	{
		return BiomeType.WATER;
	}
}
