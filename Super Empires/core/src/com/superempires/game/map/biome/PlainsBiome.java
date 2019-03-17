package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.buildings.natural.Tree;
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
		{
			tiles[y][x] = new SnowTile(x, y, temperature, this);
			
			genTree(tiles, x, y, Color.WHITE, rdm);
		}
		else
		{
			tiles[y][x] = new PlainsTile(x, y, temperature, this);
			
			genTree(tiles, x, y, Color.GREEN, rdm);
		}
	}
	
	protected void genTree(Tile[][] tiles, int x, int y, Color c, RNG rdm)
	{
		int num = (int)((rdm.getNoise().noise(x * 0.004, y * 0.004) + 1) * 100 + 1);
		
		if(rdm.getRandom().nextInt(num) > 120)
		{
			tiles[y][x].setBuilding(new Tree(tiles[y][x].getTransform(), c));
		}
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
