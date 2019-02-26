package com.superempires.game.map.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.tiling.template.TileTemplate;

public class MapGenerator
{
	// Parallel arrays
	private List<TileTemplate> tileTemplates = new ArrayList<>();
	private List<Integer> odds = new ArrayList<>();
	
	private int totalOdds = 0;
	
	/**
	 * Registers a tile template
	 * @param template The template
	 * @param odds The odds of the tile generating (not a percentage) - 1 is super small, 100 is highest
	 */
	public void registerTile(TileTemplate template, int odds)
	{
		if(odds < 1 || odds > 100)
			throw new IllegalArgumentException("You can't make the odds of tile generating below 1 or over 100.");
		
		if(this.tileTemplates.contains(template))
			throw new IllegalArgumentException("Templates already contain that template! (" + template + ")");
		
		totalOdds += odds;
		
		this.odds.add(totalOdds);
		this.tileTemplates.add(template);
	}
	
	public Tile[][] generateMap(final int WIDTH, final int HEIGHT)
	{
		Random rng = new Random();
		
		Tile[][] tiles = new Tile[HEIGHT][WIDTH];
		
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				boolean odd = x % 2 != 0;
				
				float xPos = x * 0.75f * Tile.DIMENSIONS;
				float yPos = y * Tile.DIMENSIONS - (odd ? Tile.DIMENSIONS / 2 : 0);
				
				int random = rng.nextInt(totalOdds) + 1;
				
				for(int i = 0; i < odds.size(); i++)
				{
					if(random <= odds.get(i))
					{
						tiles[y][x] = tileTemplates.get(i).createTile(xPos, yPos);
						break;
					}
				}
			}
		}
		
		return tiles;
	}
}
