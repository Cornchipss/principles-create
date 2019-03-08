package com.superempires.game.map.generation;

import java.util.Random;

import com.superempires.game.map.biome.BeachBiome;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.biome.CragBiome;
import com.superempires.game.map.biome.DesertBiome;
import com.superempires.game.map.biome.ForestBiome;
import com.superempires.game.map.biome.OceanBiome;
import com.superempires.game.map.biome.PlainsBiome;
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
		setText("Creating Elevation Ranges");
		
		double[] elevationRanges =
			{
					2,
					2,
					2,
					20,
					10,
					3
			};
		
		prepareRatios(elevationRanges);
		
		setText("Creating Biome Table");
		
		final Biome OCEAN = new OceanBiome();
		final Biome BEACH = new BeachBiome();
		final Biome CRAG = new CragBiome();
		final Biome DESERT = new DesertBiome();
		final Biome PLAINS = new PlainsBiome();
		final Biome FOREST = new ForestBiome();
		
		Biome[][] table =
		{
			{
				OCEAN
			},
			{
				BEACH
			},
			{
				CRAG,
				DESERT,
				PLAINS,
				FOREST
			},
			{
				DESERT,
				PLAINS,
				PLAINS,
				FOREST
			},
			{
				DESERT,
				PLAINS,
				FOREST,
				FOREST,
			},
			{
				PLAINS,
				FOREST,
				FOREST,
				PLAINS
			}
		};
		
		setText("Pre-Biome Generation Init");
		
		Random rdm = new Random(seed);
		
		SimplexNoise temperatureGenerator = new SimplexNoise(seed * 3);
		SimplexNoise elevationGenerator = new SimplexNoise(seed);
		SimplexNoise humidtyGenerator = new SimplexNoise(seed * 2); // * 2 is arbitrary, should be replaced later with something slightly more meaningful
		
		double scale = 0.01;
		
		setText("Generating Biomes");
		
		for(int y = 0; y < tiles.length; y++)
		{
			for(int x = 0; x < tiles[y].length; x++)
			{
				double elevationNoise = elevationGenerator.noise(x * scale, y * scale);
				double humidityNoise = humidtyGenerator.noise(x * scale, y * scale);
				
				double elevation = elevationNoise * 100 + 20;
				double humidity = humidityNoise * 50 + 50;
				
				Biome b = null;
				
				if(elevation > 0)
				{
					double rangesTotal = 1;
					
					for(int i = elevationRanges.length - 1; i >= 0; i--)
					{
						rangesTotal -= elevationRanges[i];
						
						if(elevation - rangesTotal * 100 >= 0)
						{
							b = table[i][(int)(humidity / (100 / table[i].length + 0.5))];
							break;
						}
					}
				}
				else
					b = table[0][(int)(humidity / (100 / table[0].length + 0.5))];
				
				double temperature = (temperatureGenerator.noise(x * scale, y * scale) + 0.8) * 50;
				
				b.generateTile(tiles, x, y, temperature, rdm);
			}
			
			setSubText("Row: " + (y + 1) + " / " + tiles.length);
		}
	}
	
	public void prepareRatios(double[] ratios)
	{
		double sum = 0;
		for(double d : ratios)
			sum += d;
		
		for(int i = 0; i < ratios.length; i++)
			ratios[i] /= sum;
	}
}