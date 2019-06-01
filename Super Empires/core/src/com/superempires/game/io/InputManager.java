package com.superempires.game.io;

import com.badlogic.gdx.Gdx;

public class InputManager
{
	public static final int MAX_BTN = 4;
	
	private static boolean[] btnDown = new boolean[MAX_BTN];
	
	private InputManager() { throw new IllegalStateException("Do not initialize this class."); }
	
	public static void update()
	{
		for(int i = 0; i < MAX_BTN; i++)
			btnDown[i] = Gdx.input.isButtonPressed(i);
	}
	
	public static boolean isKeyPressed(int key)
	{
		return Gdx.input.isKeyPressed(key);
	}
	
	public static boolean isKeyJustPressed(int key)
	{
		return Gdx.input.isKeyJustPressed(key);
	}
	
	public static boolean isButtonPressed(int btn)
	{
		return Gdx.input.isButtonPressed(btn);
	}
	
	public static boolean isButtonJustPressed(int btn)
	{
		return !btnDown[btn] && isButtonPressed(btn);
	}
}
