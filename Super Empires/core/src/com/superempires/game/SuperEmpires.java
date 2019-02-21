package com.superempires.game;

import com.badlogic.gdx.Game;
import com.superempires.game.screens.MainScreen;

public class SuperEmpires extends Game
{
	@Override
	public void create ()
	{
		setScreen(new MainScreen());
	}
}
