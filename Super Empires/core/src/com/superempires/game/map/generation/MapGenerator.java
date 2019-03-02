package com.superempires.game.map.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.util.Helper;
import com.superempires.game.util.Vector2i;

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
		Random rdm = new Random();
		
		Tile[][] tiles = new Tile[HEIGHT][WIDTH];
		
		int amt = (WIDTH * HEIGHT) / 1000;
		if(amt == 0)
			amt = 1;
		
		Vector2i[] biomeCoords = new Vector2i[amt];
		Biome[] generatedBiomes = new Biome[amt];
		
		for(int i = 0; i < amt; i++)
		{
			boolean found = false;
			
			do
			{
				biomeCoords[i] = new Vector2i(rdm.nextInt(WIDTH), rdm.nextInt(HEIGHT));
				
				for(int j = 0; j < i; j++)
				{
					if(biomeCoords[j].equals(biomeCoords[i]))
					{
						found = true;
						break;
					}
				}
			}
			while(found);
			
			double temperature = calculateTemperature(biomeCoords[i].y, HEIGHT, rdm);
			
			generatedBiomes[i] = findBestBiome(temperature, rdm);
			
//			biomes[i].generateTile(tiles, biomeCoords[i].x, biomeCoords[i].y, temperature);
		}
		
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				Biome closest = null;
				double closestDist = 0;
				
				Vector2 here = new Vector2(x, y);
				
				double temperature = calculateTemperature(y, HEIGHT, rdm);
				
				for(int i = 0; i < generatedBiomes.length; i++)
				{
					if(generatedBiomes[i].isAcceptableTemperature(temperature))
					{
						if(closest == null)
						{
							closest = generatedBiomes[i];
						}
						else
						{
							double dist = Helper.distanceSquared(biomeCoords[i].asVector2(), here);
							
							if(dist < closestDist)
							{
								closest = generatedBiomes[i];
								closestDist = dist;
							}
						}
					}
				}
				
				if(closest == null)
					throw new IllegalStateException("NOT ENOUGH BIOMES TO PROPERLY FILL MAP WITH TEMPERATURES!");
				
				closest.generateTile(tiles, x, y, temperature);
			}
			
			System.out.println(y + " / " + HEIGHT);
		}
		
		return tiles;
	}
	
	private static double calculateTemperature(int y, int worldHeight, Random rdm)
	{
		double closer = Math.min(y + 1, worldHeight - y);
		
		double min = (closer * 47.0 / worldHeight - 4.5) * 5.3 + 0.5;
		
		return rdm.nextDouble() * 4 + min;
	}
	
	protected List<Biome> findOkBiomes(double temperature)
	{
		List<Biome> goodBiomes = new ArrayList<>();
		
		for(Biome b : biomes)
		{
			if(b.isAcceptableTemperature(temperature))
				goodBiomes.add(b);
		}
		
		return goodBiomes;
	}
	
	protected Biome findBestBiome(double temperature, Random rdm)
	{
		float totalRarity = 0;
		List<Biome> goodBiomes = new ArrayList<>();
		
		for(Biome b : biomes)
		{
			boolean ok = b.isAcceptableTemperature(temperature);
			
			if(ok)
			{
				goodBiomes.add(b);
				totalRarity += b.getRarity();
			}
		}
		
		int rand = (int)(rdm.nextFloat() * totalRarity + 1);
		
		float previousRarity = 0;
		
		for(Biome b : goodBiomes)
		{
			previousRarity += b.getRarity();
			if(previousRarity >= rand)
				return b;
		}		
		
		System.out.println(goodBiomes);
		
		System.out.println(previousRarity + " >= " + rand);
		
		throw new IllegalStateException("NO VALID BIOME: " + temperature + " degrees!");
	}
}
