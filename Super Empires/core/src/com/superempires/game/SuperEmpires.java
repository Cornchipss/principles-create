package com.superempires.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.superempires.game.io.InputManager;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.screens.LoadingScreen;

public class SuperEmpires extends Game
{
	private static SuperEmpires instance;
	
	private long lastFPSOutput = System.currentTimeMillis();
	
	@Override
	public void create()
	{
		instance = this;

		super.setScreen(new LoadingScreen());
	}

	@Override
	public void dispose()
	{
		super.dispose();

		GameRegistry.dispose();
	}
	
	@Override
	public void render()
	{
		if(System.currentTimeMillis() - lastFPSOutput >= 1000)
		{
			System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
			lastFPSOutput = System.currentTimeMillis();
		}
		
		super.render();

		InputManager.update();
	}

	@Override
	public void setScreen(Screen screen)
	{
		if(getScreen() != null)
			getScreen().dispose();

		super.setScreen(screen);
	}
	
	public static void swapScreen(Screen screen)
	{
		getSuperEmpires().setScreen(screen);
	}

	public static SuperEmpires getSuperEmpires() { return instance; }
}
