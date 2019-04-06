package com.superempires.game.util;

import com.badlogic.gdx.graphics.Color;

public class Colors
{
	public static final Color LIGHT_GREY_SHADING = new Color(230 / 255f, 230 / 255f, 230 / 255f, 1);
	public static final Color GREY_SHADING = new Color(210 / 255f, 210 / 255f, 210 / 255f, 1);
	public static final Color DARK_GREY_SHADING = new Color(140 / 255f, 140 / 255f, 140 / 255f, 1);
	public static final Color CLEAR_SHADING = Color.WHITE;
	
	public static Color mixShading(Color a, Color b)
	{
		return new Color((b.r + a.r) / 2, (b.g + a.g) / 2, (b.b + a.b) / 2, 1);
	}
}
