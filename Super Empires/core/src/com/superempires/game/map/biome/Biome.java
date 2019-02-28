package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.superempires.game.map.tiling.Tile;

public abstract class Biome
{
	public static final float MIN_AVERAGE_TEMPERATURE = -10f;
	
	public abstract void generateTile(Tile[][] tiles, int x, int y, double temperature);
	
	public abstract Color getShadingColor();
	
	public abstract float getPreferredAverageTemperature();
	
	public abstract int getRarity();

	public abstract boolean isAcceptableTemperature(double temperature);
}
