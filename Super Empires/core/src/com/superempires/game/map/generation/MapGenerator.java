package com.superempires.game.map.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.biome.BiomeType;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.screens.WorldGenerationScreen;

public abstract class MapGenerator
{
	private List<Biome> biomes = new ArrayList<>();
	private Map<BiomeType, List<Biome>> types = new HashMap<>();
	
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
	
	public abstract void generateMap(final Tile[][] tiles, long seed);
	
	/**
	 * Registers a tile template
	 * @param template The template
	 * @param odds The odds of the tile generating (not a percentage) - 1 is super small, 100 is highest
	 */
	public void registerBiome(Biome b, BiomeType type)
	{
		biomes.add(b);
		
		if(types.containsKey(type))
			types.get(type).add(b);
		else
		{
			List<Biome> biomes = new ArrayList<>();
			biomes.add(b);
			types.put(type, biomes);
		}
	}
	
	public List<Biome> getBiomes() { return biomes; }
	
	public WorldGenerationScreen getScreen() { return screen; }
}
