package com.superempires.game.map.generation;

import com.superempires.game.map.GameMap;
import com.superempires.game.map.biome.BeachBiome;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.biome.BiomeType;
import com.superempires.game.map.biome.CragBiome;
import com.superempires.game.map.biome.DesertBiome;
import com.superempires.game.map.biome.ForestBiome;
import com.superempires.game.map.biome.OceanBiome;
import com.superempires.game.map.biome.PlainsBiome;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.units.ColonizationUnit;
import com.superempires.game.screens.WorldGenerationScreen;
import com.superempires.game.util.Helper;
import com.superempires.game.util.RNG;
import com.superempires.game.util.Vector2i;

import libs.noise.SimplexNoise;

public class DefaultMapGenerator extends MapGenerator
{
	private static final Biome OCEAN = new OceanBiome();
	private static final Biome BEACH = new BeachBiome();
	private static final Biome CRAG = new CragBiome();
	private static final Biome DESERT = new DesertBiome();
	private static final Biome PLAINS = new PlainsBiome();
	private static final Biome FOREST = new ForestBiome();

	private static final Biome[][] table =
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

	private static final double[] elevationRanges =
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
	public Vector2i generateMap(GameMap map, long seed)
	{
		Tile[][] tiles = map.getTiles();
		
		setText("Pre-Biome Generation Init");

		RNG rng = new RNG(seed * 5);
		
		SimplexNoise temperatureGenerator = new SimplexNoise(seed * 3);
		SimplexNoise elevationGenerator = new SimplexNoise(seed);
		SimplexNoise humidtyGenerator = new SimplexNoise(seed * 2);

		final double scale = 0.01;

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
				
				double temperature = (temperatureGenerator.noise(x * scale * 0.3, y * scale * 0.3) + 1) * 50;
				
				b.generateTile(map, x, y, temperature, rng);
			}
			
			setSubText("Row: " + (y + 1) + " / " + tiles.length);
		}
		
		int spawnX = -1, spawnY = -1;
		do
		{
			spawnX = rng.getRandom().nextInt(tiles[0].length);
			spawnY = rng.getRandom().nextInt(tiles.length);
		} while(tiles[spawnY][spawnX].getBiome().getType() == BiomeType.WATER);
		
		tiles[spawnY][spawnX].setUnit(new ColonizationUnit(tiles[spawnY][spawnX].getTransform(), map));
		
		System.out.println(spawnX + " , " + spawnY);
		
		return new Vector2i(spawnX, spawnY);
	}
}
