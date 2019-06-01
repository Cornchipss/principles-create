package com.superempires.game.map.generation;

import com.superempires.game.map.GameMap;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.units.ColonizationUnit;
import com.superempires.game.screens.WorldGenerationScreen;
import com.superempires.game.util.RNG;
import com.superempires.game.util.Vector2i;

public class OneBiomeMapGenerator extends MapGenerator
{
	private Biome biome;
	
	public OneBiomeMapGenerator(WorldGenerationScreen screen, Biome b)
	{
		super(screen);
		
		this.biome = b;
	}

	@Override
	public Vector2i generateMap(GameMap map, long seed)
	{
		Tile[][] tiles = map.getTiles();
		
		RNG rdm = new RNG(seed);
		
		setSubText("Generating tiles...");
		
		for(int y = 0; y < tiles.length; y++)
		{
			for(int x = 0; x < tiles.length; x++)
			{
				biome.generateTile(map, x, y, 64, rdm);
			}
			
			setText("ROW: " + (y + 1) + " / " + tiles.length);
		}
		
		int spawnX, spawnY;
		
		spawnX = rdm.getRandom().nextInt(tiles[0].length);
		spawnY = rdm.getRandom().nextInt(tiles.length);
		
		tiles[spawnY][spawnX].setUnit(new ColonizationUnit(tiles[spawnY][spawnX].getTransform(), map));
		
		return new Vector2i(spawnX, spawnY);
	}
}
