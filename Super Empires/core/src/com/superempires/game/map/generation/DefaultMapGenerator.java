package com.superempires.game.map.generation;

import java.util.Random;

import com.superempires.game.map.biome.BeachBiome;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.biome.CragBiome;
import com.superempires.game.map.biome.DesertBiome;
import com.superempires.game.map.biome.ForestBiome;
import com.superempires.game.map.biome.OceanBiome;
import com.superempires.game.map.biome.PineForestBiome;
import com.superempires.game.map.biome.PlainsBiome;
import com.superempires.game.map.biome.SnowForestBiome;
import com.superempires.game.map.biome.SnowPlainsBiome;
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
		double[] elevationRanges =
			{
					20,
					2,
					2,
					20,
					10,
					3
			};
		
		prepareRatios(elevationRanges);
		
		// Code works
		
		Biome[][] table =
		{
			{
				new OceanBiome()
			},
			{
				new BeachBiome()
			},
			{
				new CragBiome(),
				new DesertBiome(),
				new PlainsBiome(),
				new ForestBiome()
			},
			{
				new DesertBiome(),
				new PlainsBiome(),
				new PlainsBiome(),
				new ForestBiome()
			},
			{
				new DesertBiome(),
				new PlainsBiome(),
				new PlainsBiome(),
				new PineForestBiome()
			},
			{
				new SnowPlainsBiome(),
				new SnowForestBiome(),
				new SnowForestBiome(),
				new SnowForestBiome()
			}
		};
		
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
				
				b.generateTile(tiles, x, y, elevation, rdm);
			}
			
			setSubText((y + 1) + " / " + tiles.length);
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