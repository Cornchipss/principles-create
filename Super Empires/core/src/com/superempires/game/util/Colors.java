package com.superempires.game.util;

import com.badlogic.gdx.graphics.Color;

public class Colors
{
	public static final Color SEMI_GREY_SHADING = new Color(220 / 255f, 220 / 255f, 220 / 255f, 1);
	public static final Color GREY_SHADING = new Color(140 / 255f, 140 / 255f, 140 / 255f, 1);
	public static final Color CLEAR_SHADING = Color.WHITE;
	
	public static Color mixShading(Color a, Color b)
	{
		return new Color((b.r + a.r) / 2, (b.g + a.g) / 2, (b.b + a.b) / 2, 1);
	}
}
