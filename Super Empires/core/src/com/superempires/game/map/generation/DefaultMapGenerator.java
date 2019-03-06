package com.superempires.game.map.generation;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.biome.BiomeType;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.screens.WorldGenerationScreen;
import com.superempires.game.util.Helper;
import com.superempires.game.util.Vector2i;

public class DefaultMapGenerator
{
	private List<Biome> biomes = new ArrayList<>();
	private Map<BiomeType, List<Biome>> types = new HashMap<>();
	
	private WorldGenerationScreen screen;
	
	public DefaultMapGenerator(WorldGenerationScreen screen)
	{
		this.screen = screen;
	}
	
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
	
	public Tile[][] generateMap(final Tile[][] tiles, long seed)
	{
		Random rdm = new Random(seed);
		
		final int WIDTH = tiles[0].length;
		final int HEIGHT = tiles.length;
		
		screen.setText("Planting Biomes");
		
		int amt = (WIDTH * HEIGHT) / 5000;
		if(amt == 0)
			amt = 1;
		
		Vector2i[] biomeCoords = new Vector2i[amt];
		Biome[] generatedBiomes = new Biome[amt];
		
		for(int i = 0; i < amt; i++)
		{
			boolean found;
			
			do
			{
				found = false;
				
				biomeCoords[i] = new Vector2i(rdm.nextInt(WIDTH), rdm.nextInt(HEIGHT));
				
				for(int j = 0; j < i; j++)
				{
					if(biomeCoords[j].equals(biomeCoords[i]))
					{
						found = true;
						break;
					}
				}
				
				screen.setSubText((i + 1) + " / " + amt + " planted!");
			}
			while(found);
			
			double temperature = calculateTemperature(biomeCoords[i].y, HEIGHT, rdm);
			
			generatedBiomes[i] = findBestBiome(temperature, rdm);
		}
		
		screen.setText("Filling Biomes");
		
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				Vector2 here = new Vector2(x, y);
				
				Biome closest = generatedBiomes[0];
				double closestDist = Helper.distanceSquared(biomeCoords[0].asVector2(), here);
				
				double temperature = calculateTemperature(y, HEIGHT, rdm);
				
				for(int i = 1; i < amt; i++)
				{
					if(generatedBiomes[i].isAcceptableTemperature(temperature))
					{
						double dist = Helper.distanceSquared(biomeCoords[i].asVector2(), here);
						
						if(dist < closestDist)
						{
							closest = generatedBiomes[i];
							closestDist = dist;
						}
					}
				}
				
				if(closest == null)
					throw new IllegalStateException("NOT ENOUGH BIOMES TO PROPERLY FILL MAP WITH TEMPERATURES!");
				
				closest.generateTile(tiles, x, y, temperature, rdm);
			}
			
			screen.setSubText("BIOME GENERATION PROGRESS: " + (y + 1) + " / " + HEIGHT);
		}
		
		screen.setText("World Generation Complete!");
		
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
		
		System.out.println(goodBiomes);
		
		System.out.println(previousRarity + " >= " + rand);
		
		throw new IllegalStateException("NO VALID BIOME: " + temperature + " degrees!");
	}
}
