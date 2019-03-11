package com.superempires.game.map.generation;

import com.superempires.game.map.tiling.Tile;
import com.superempires.game.screens.WorldGenerationScreen;

public abstract class MapGenerator
{	
	private WorldGenerationScreen screen;
	
	public MapGenerator(WorldGenerationScreen screen)
	{
		this.screen = screen;
	}
	
	protected void setText(String txt)
	{
		getScreen().setText(txt);
	}
	
	protected void setSubText(String txt)
	{
		getScreen().setSubText(txt);
	}
	
	/**
	 * <p>Fills the given tile array with a world</p>
	 * <p>No tiles are left null</p>
	 * <p>One seed should always generate the same exact pattern of terrain</p>
	 * @param tiles The tiles array to fill
	 * @param seed The seed to use
	 */
	public abstract void generateMap(final Tile[][] tiles, long seed);
	
	public WorldGenerationScreen getScreen() { return screen; }
}