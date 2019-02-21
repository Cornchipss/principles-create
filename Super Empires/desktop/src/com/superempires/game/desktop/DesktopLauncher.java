package com.superempires.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.superempires.game.SuperEmpires;
import com.superempires.game.util.Reference;

/*
 * https://github.com/libgdx/libgdx/wiki
 */

public class DesktopLauncher {
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = Reference.VIEWPORT_WIDTH;
		config.height = Reference.VIEWPORT_HEIGHT;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		
		new LwjglApplication(new SuperEmpires(), config);
	}
}
