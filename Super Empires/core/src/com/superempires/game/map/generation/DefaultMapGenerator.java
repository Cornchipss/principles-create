package com.superempires.game.map.generation;

import com.badlogic.gdx.math.Vector2;
import com.superempires.game.map.biome.BeachBiome;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.biome.CragBiome;
import com.superempires.game.map.biome.DesertBiome;
import com.superempires.game.map.biome.ForestBiome;
import com.superempires.game.map.biome.OceanBiome;
import com.superempires.game.map.biome.PlainsBiome;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.screens.WorldGenerationScreen;
import com.superempires.game.util.Helper;
import com.superempires.game.util.RNG;

import libs.noise.SimplexNoise;

public class DefaultMapGenerator extends MapGenerator
{
	private final static Biome OCEAN = new OceanBiome();
	private final static Biome BEACH = new BeachBiome();
	private final static Biome CRAG = new CragBiome();
	private final static Biome DESERT = new DesertBiome();
	private final static Biome PLAINS = new PlainsBiome();
	private final static Biome FOREST = new ForestBiome();

	private final static Biome[][] table =
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

	private static double[] elevationRanges =
		{
				2,
				2,
				2,
				20,
				10,
				3
		};

	static
	{
		Helper.arrayToRatios(elevationRanges);
	}
	
	public DefaultMapGenerator(WorldGenerationScreen screen)
	{
		super(screen);
	}
	
	@Override
	public void generateMap(final Tile[][] tiles, long seed)
	{
		setText("Pre-Biome Generation Init");

		RNG rng = new RNG(seed * 5);
		
		SimplexNoise temperatureGenerator = new SimplexNoise(seed * 3);
		SimplexNoise elevationGenerator = new SimplexNoise(seed);
		SimplexNoise humidtyGenerator = new SimplexNoise(seed * 2); // * 2 is arbitrary, should be replaced later with something slightly more meaningful

		double scale = 0.01;

		setText("Generating Biomes");
		
		// For Pi day (3/14/19)
		float radius = (float)(tiles[0].length * 0.75 * Tile.DIMENSIONS + tiles.length * 0.5 * Tile.DIMENSIONS) / 2f;
		Vector2 center = new Vector2((float)(0.75 * tiles[0].length * Tile.DIMENSIONS), (float)(0.5 * tiles.length * Tile.DIMENSIONS));
		
		double within = 0;
		double without = 0;
		
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

				b.generateTile(tiles, x, y, temperature, rng);
				
				float xx = x * 0.75f * Tile.DIMENSIONS, yy = y * Tile.DIMENSIONS - (x % 2) * Tile.DIMENSIONS / 2;
				
				// For Pi day (3/14/19)
				if(Helper.distanceSquared(new Vector2(xx, yy), center) <= radius * radius)
					within++;
				else
					without++;
			}
			
			setSubText("Row: " + (y + 1) + " / " + tiles.length);
		}
		
		// For Pi day (3/14/19)
		System.out.println("Pi is approximately: " + within / without);
	}
}
