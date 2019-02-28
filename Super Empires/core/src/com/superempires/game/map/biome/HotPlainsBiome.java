package com.superempires.game.map.biome;

import com.badlogic.gdx.graphics.Color;

public class HotPlainsBiome extends PlainsBiome
{
	private Color shadingColor = new Color(255 / 255f, 180 / 255f, 180 / 255f, 1);
	
	@Override
	public boolean isAcceptableTemperature(double temperature)
	{
		return 90 <= temperature && temperature <= 100;
	}
	
	@Override
	public Color getShadingColor()
	{
		return shadingColor;
	}
}
