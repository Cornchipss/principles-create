package com.superempires.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.superempires.game.map.buildings.Building;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.troops.Unit;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.render.MasterBatch;
import com.superempires.game.util.Helper;
import com.superempires.game.util.Vector2i;

public class GameMap
{
	private final int WIDTH, HEIGHT;
	
	private final Tile[][] tiles;
	private final Building[][] buildings;
	private final Unit[][] units;
	
	public GameMap(int w, int h)
	{
		WIDTH = w;
		HEIGHT = h;
		
		tiles = new Tile[h][w];
		buildings = new Building[h][w];
		units = new Unit[h][w];
		
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				boolean odd = x % 2 != 0;
				
				float xPos = x * 4f / 4 * Tile.WIDTH - (Tile.WIDTH / 4f * x);
				float yPos = y * Tile.HEIGHT - (odd ? Tile.HEIGHT / 2 : 0);
				
				tiles[y][x] = new Tile(xPos, yPos);
				
//				if(Math.random() < 0.05)
//				{
//					buildings[y][x] = new TestBuilding(x * Tile.WIDTH, y * Tile.HEIGHT);
//					units[y][x] = new Unit(x * Tile.WIDTH, y * Tile.HEIGHT);
//				}
			}
		}
	}
	
	public void update(FancyCamera cam, float delta)
	{
	    Vector2 mouseWorldCoords = cam.screenToWorldCoords(Gdx.input.getX(), Gdx.input.getY());
	    
	    Transform bounds = getBounds();
	    
	    cam.position.set(
    		Helper.clamp(cam.position.x, Gdx.graphics.getWidth() / 2 + bounds.getX() * Tile.WIDTH, 
    				bounds.getWidth() * Tile.WIDTH - Gdx.graphics.getWidth() / 2), 
    		Helper.clamp(cam.position.y, Gdx.graphics.getHeight() / 2 + bounds.getY() * 2 * Tile.HEIGHT, 
    				bounds.getHeight() * Tile.HEIGHT - Gdx.graphics.getHeight() / 2), 
    		cam.position.z);
	    
		cam.update();
		
		Vector2i index = worldCoordsToTileIndex(mouseWorldCoords);
		
		if(within(index.x, index.y))
		{			
			getTile(index.x, index.y).setHighlighted(true);
		}
	}
	
	public boolean within(int indexX, int indexY)
	{
		return indexX >= 0 && indexX < WIDTH && indexY >= 0 && indexY < HEIGHT;
	}
	
	public Vector2i worldCoordsToTileIndex(Vector2 coords)
	{
		int realIndexX, realIndexY;
		
		int xIndexIsh = realIndexX = (int) (coords.x / (0.75f * Tile.WIDTH));
		int yIndexIsh = realIndexY = (int) (coords.y / Tile.HEIGHT);
		
		if(within(xIndexIsh, yIndexIsh))
		{
			float closest = Helper.distanceSquared(coords, getTile(xIndexIsh, yIndexIsh).getTransform().getCenter());
			
			for(int dY = -1; dY <= 1; dY++)
			{
				for(int dX = -1; dX <= 1; dX++)
				{
					if(dX != 0 || dY != 0)
					{
						int indexX = xIndexIsh + dX, indexY = yIndexIsh + dY;
						
						if(within(indexX, indexY))
						{
							float dist = Helper.distanceSquared(coords, getTile(indexX, indexY).getTransform().getCenter());
							if(dist < closest)
							{
								closest = dist;
								realIndexX = indexX;
								realIndexY = indexY;
							}
						}
					}
				}
			}
		}
		
		return new Vector2i(realIndexX != -1 ? realIndexX : 0, realIndexY != -1 ? realIndexY : 0);
	}
	
	public Tile getTile(int x, int y) { return tiles[y][x]; }
	public Building getBuilding(int x, int y) { return buildings[y][x]; }
	public Unit getUnit(int x, int y) { return units[y][x]; }
	
	public Tile setTile(int x, int y, Tile t) { Tile temp = tiles[y][x]; tiles[y][x] = t; return temp;  }
	public Building setBuilding(int x, int y, Building b) { Building temp = buildings[y][x]; buildings[y][x] = b; return temp; }
	public Unit setUnit(int x, int y, Unit u) { Unit temp = units[y][x]; units[y][x] = u; return temp; }

	public float getWidth() { return WIDTH * 0.75f + 0.25f; } // 0.75f because each hexagon is actually only 3/4 the width because of how they are layered, and + 0.25f because of the corner on the last row
	public float getHeight() { return HEIGHT; } // +0.25 same reason

	public Transform getBounds()
	{
		return new Transform(0, -0.25f, getWidth(), getHeight());
	}
	
	public void drawShapes(FancyCamera cam, ShapeRenderer batch)
	{
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				if(tiles[y][x] != null)
					tiles[y][x].drawShapes(batch);
				if(buildings[y][x] != null)
					buildings[y][x].drawShapes(batch);
				if(units[y][x] != null)
					units[y][x].drawShapes(batch);
			}
		}
	}

	public void drawLines(FancyCamera cam, ShapeRenderer batch)
	{
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				if(tiles[y][x] != null)
					tiles[y][x].drawLines(batch);
				if(buildings[y][x] != null)
					buildings[y][x].drawLines(batch);
				if(units[y][x] != null)
					units[y][x].drawLines(batch);
			}
		}
	}

	public void drawPolygons(FancyCamera cam, PolygonSpriteBatch batch)
	{
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		Vector2i pos = worldCoordsToTileIndex(cam.getPosition2().cpy().sub(width / 2 + Tile.WIDTH / 4, height / 2 + Tile.HEIGHT / 2));
		
		float maxY = Math.min(getHeight(), pos.y + height / Tile.HEIGHT + 2);
		float maxX = Math.min(getWidth() , pos.x + width / Tile.WIDTH + 0);
		
		for(int y = pos.y; y < maxY; y++)
		{
			for(int x = pos.x; x < maxX; x++)
			{
				if(y != -1)
				{
					if(tiles[y][x] != null)
						tiles[y][x].drawPolygons(batch);
					if(buildings[y][x] != null)
						buildings[y][x].drawPolygons(batch);
					if(units[y][x] != null)
						units[y][x].drawPolygons(batch);
				}
			}
		}
	}

	public void drawSprites(FancyCamera cam, SpriteBatch batch)
	{
		Vector2i pos = worldCoordsToTileIndex(cam.getPosition2());
		
		for(int y = pos.y; y < HEIGHT; y++)
		{
			for(int x = pos.x; x < WIDTH; x++)
			{
				if(tiles[y][x] != null)
					tiles[y][x].drawSprites(batch);
				if(buildings[y][x] != null)
					buildings[y][x].drawSprites(batch);
				if(units[y][x] != null)
					units[y][x].drawSprites(batch);
			}
		}
	}

	public void drawAll(FancyCamera cam, MasterBatch batch)
	{
		batch.getPolyBatch().begin();
		drawPolygons(cam, batch.getPolyBatch());
		batch.getPolyBatch().end();
		
		batch.getSpriteBatch().begin();
		drawSprites(cam, batch.getSpriteBatch());
		batch.getSpriteBatch().end();
		
		batch.getShapeBatch().begin(ShapeType.Filled);
		drawShapes(cam, batch.getShapeBatch());
		batch.getShapeBatch().end();
		
		batch.getShapeBatch().begin(ShapeType.Line);
		drawLines(cam, batch.getShapeBatch());
		batch.getShapeBatch().end();
	}
}
