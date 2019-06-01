package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.buildings.natural.Tree;
import com.superempires.game.map.tiling.PlainsTile;
import com.superempires.game.map.tiling.SnowTile;
import com.superempires.game.util.RNG;

public class ForestBiome extends Biome
{
	@Override
	public void generateTile(GameMap map, int x, int y, double temperature, RNG rdm)
	{
		if(Biome.isFreezing(temperature))
		{
			map.setTile(x, y, new SnowTile(x, y, temperature, this, map));
			
			genTree(map, x, y, Color.WHITE, rdm);
		}
		else
		{
			map.setTile(x, y, new PlainsTile(x, y, temperature, this, map));
			
			genTree(map, x, y, Color.GREEN, rdm);
		}
	}
	
	protected void genTree(GameMap map, int x, int y, Color color, RNG rdm)
	{
		int num = (int)((rdm.getNoise().noise(x * 0.004, y * 0.004) + 1) * 100 + 1);
		
		if(rdm.getRandom().nextInt(num) > 120)
		{
			map.getTile(x, y).setBuilding(new Tree(map.getTile(x, y).getTransform(), map, color));
		}
	}

	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return false;
	}
	
	@Override
	public Color getShadingColor()
	{
		return Color.GREEN;
	}
	
	@Override
	public BiomeType getType()
	{
		return BiomeType.LAND;
	}
}
