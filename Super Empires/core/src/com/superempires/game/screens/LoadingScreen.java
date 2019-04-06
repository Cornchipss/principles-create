package com.superempires.game.screens;

import com.badlogic.gdx.Screen;
import com.superempires.game.SuperEmpires;
import com.superempires.game.registry.Registerer;

public class LoadingScreen implements Screen
{
	@Override
	public void show()
	{		
		Registerer.registerAll();
		
		SuperEmpires.swapScreen(new MainMenuScreen());
	}

	@Override
	public void render(float delta)
	{
		
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void dispose()
	{
		
	}

}
