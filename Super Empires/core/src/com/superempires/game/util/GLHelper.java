package com.superempires.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GLHelper
{
	public static void enableTransparency()
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
}
