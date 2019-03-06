package com.superempires.game;

import com.badlogic.gdx.Game;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.registry.Textures;
import com.superempires.game.screens.MainScreen;
import com.superempires.game.screens.WorldGenerationScreen;

public class SuperEmpires extends Game
{
	private volatile Tile[][] tiles;
	
	private WorldGenerationScreen genScreen = new WorldGenerationScreen();
	
	@Override
	public void create()
	{
		Textures.registerAll();
		
		setScreen(genScreen);
		
		tiles = new Tile[1000][1000];
		
		genScreen.generate(tiles, System.nanoTime());
	}
	
	@Override
	public void render()
	{
		if(genScreen == null || !genScreen.done())
			super.render();
		else
		{
			setScreen(new MainScreen(new GameMap(tiles)));
			genScreen = null;
		}
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		GameRegistry.dispose();
	}
}
