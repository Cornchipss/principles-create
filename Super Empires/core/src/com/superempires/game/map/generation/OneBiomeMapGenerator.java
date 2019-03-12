package com.superempires.game.map.generation;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.screens.WorldGenerationScreen;
import com.superempires.game.util.RNG;

public class OneBiomeMapGenerator extends MapGenerator
{
	private Biome biome;
	
	public OneBiomeMapGenerator(WorldGenerationScreen screen, Biome b)
	{
		super(screen);
		
		this.biome = b;
	}

	@Override
	public void generateMap(Tile[][] tiles, long seed)
	{
		RNG rdm = new RNG(seed);
		
		for(int y = 0; y < tiles.length; y++)
		{
			for(int x = 0; x < tiles.length; x++)
			{
				biome.generateTile(tiles, x, y, 64, rdm);
			}
		}
	}
}
