package com.superempires.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.screens.LoadingScreen;

public class SuperEmpires extends Game
{
	private static SuperEmpires instance;
	
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
	public void setScreen(Screen screen)
	{
		throw new RuntimeException("Screen cannot be set directly; Use SuperEmpires.swapScreen");
	}

	private void setScrn(Screen screen)
	{
		if(getScreen() != null)
			getScreen().dispose();
		
		super.setScreen(screen);
	}
	
	public static void swapScreen(Screen screen)
	{
		getSuperEmpires().setScrn(screen);
	}
	
	public static SuperEmpires getSuperEmpires() { return instance; }
}
