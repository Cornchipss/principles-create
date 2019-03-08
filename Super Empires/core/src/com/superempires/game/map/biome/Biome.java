package com.superempires.game.map.biome;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.Tile;

public abstract class Biome
{	
	public abstract void generateTile(Tile[][] tiles, int x, int y, double temperature, Random rdm);
	
	public Color getShadingColor()
	{
		return Color.WHITE;
	}
	
	public abstract boolean isAcceptableTemperature(double temperature);
}
