package com.superempires.game.map.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.screens.WorldGenerationScreen;

import libs.noise.SimplexNoise;

public class DefaultMapGenerator extends MapGenerator
{
	public DefaultMapGenerator(WorldGenerationScreen screen)
	{
		super(screen);
	}

	@Override
	public void generateMap(final Tile[][] tiles, long seed)
	{
		Random rdm = new Random(seed);
		
		SimplexNoise elevationGenerator = new SimplexNoise(seed);
		SimplexNoise humidtyGenerator = new SimplexNoise(seed * 2); // * 2 is arbitrary, should be replaced later with something slightly more meaningful
		
		double scale = 0.01;
		
		for(int y = 0; y < tiles.length; y++)
		{
			for(int x = 0; x < tiles[y].length; x++)
			{
				double elevationNoise = elevationGenerator.noise(x * scale, y * scale);
				double humidityNoise = humidtyGenerator.noise(x * scale, y * scale);
				
				double elevation = elevationNoise * 100;
				double humidity = humidityNoise * 50 + 50;
				
				findBestBiome(elevation, humidity, rdm).generateTile(tiles, x, y, elevation, rdm);;
			}
			
			setSubText((y + 1) + " / " + tiles.length);
		}
	}
	
	private Biome findBestBiome(double temperature, double humidity, Random rdm)
	{
		float totalRarity = 0;
		List<Biome> goodBiomes = new ArrayList<>();
		
		for(Biome b : getBiomes())
		{
			boolean ok = b.isAcceptableTemperature(temperature);
			
			if(ok)
			{
				goodBiomes.add(b);
				totalRarity += b.getRarity(temperature);
			}
		}
		
		int rand = (int)(rdm.nextFloat() * totalRarity + 1);
		
		float previousRarity = 0;
		
		for(Biome b : goodBiomes)
		{
			previousRarity += b.getRarity(temperature);
			if(previousRarity >= rand)
				return b;
		}
		
		throw new IllegalStateException("NO VALID BIOME: " + temperature + " degrees!");
	}
}