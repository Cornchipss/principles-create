package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.superempires.game.map.tiling.Tile;

public abstract class Biome
{
	public static final float MIN_AVERAGE_TEMPERATURE = -10f;
	
	public abstract void generateTile(Tile[][] tiles, int x, int y);
	
	public abstract Color getShadingColor();
	
	public abstract Vector2 getAverageTemperatureRange();
	public abstract float getPreferredAverageTemperature();
	
	public abstract Vector2 getAverageHumidityRange();
	
	public abstract float getRarity();
}
