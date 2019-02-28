package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;

public class ColdPlainsBiome extends PlainsBiome
{
	private Color shadingColor = new Color(0.8f, 0.8f, 1, 1);
	
	@Override
	public Color getShadingColor()
	{
		return shadingColor;
	}
	
	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return temperature >= 32 && temperature <= 60;
	}
}
