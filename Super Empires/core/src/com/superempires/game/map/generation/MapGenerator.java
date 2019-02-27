package com.superempires.game.map.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.tiling.template.TileTemplate;

public class MapGenerator
{
	private List<Biome> biomes = new ArrayList<>();
	
	/**
	 * Registers a tile template
	 * @param template The template
	 * @param odds The odds of the tile generating (not a percentage) - 1 is super small, 100 is highest
	 */
	public void registerBiome(Biome b)
	{
		biomes.add(b);
	}
	
	public Tile[][] generateMap(final int WIDTH, final int HEIGHT)
	{
		Random rng = new Random();
		
		Tile[][] tiles = new Tile[HEIGHT][WIDTH];
		
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				
			}
		}
		
		return tiles;
	}
}
