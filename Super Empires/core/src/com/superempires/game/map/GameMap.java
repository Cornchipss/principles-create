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
import com.superempires.game.render.IDrawable;
import com.superempires.game.render.MasterBatch;
import com.superempires.game.util.Helper;

public class GameMap implements IDrawable
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
		
		int mouseIndexX;
		int mouseIndexY;
		
		int xIndexIsh = mouseIndexX = (int) (mouseWorldCoords.x / (0.75f * Tile.WIDTH));
		int yIndexIsh = mouseIndexY = (int) (mouseWorldCoords.y / Tile.HEIGHT);
		
		if(within(xIndexIsh, yIndexIsh))
		{
			float closest = Helper.distanceSquared(mouseWorldCoords, getTile(xIndexIsh, yIndexIsh).getTransform().getCenter());
			
			for(int dY = -1; dY <= 1; dY++)
			{
				for(int dX = -1; dX <= 1; dX++)
				{
					if(dX != 0 || dY != 0)
					{
						int indexX = xIndexIsh + dX, indexY = yIndexIsh + dY;
						
						if(within(indexX, indexY))
						{
							float dist = Helper.distanceSquared(mouseWorldCoords, getTile(indexX, indexY).getTransform().getCenter());
							if(dist < closest)
							{
								closest = dist;
								mouseIndexX = indexX;
								mouseIndexY = indexY;
							}
						}
					}
				}
			}
			
			getTile(mouseIndexX, mouseIndexY).setHighlighted(true);
		}
	}
	
	public boolean within(int indexX, int indexY)
	{
		return indexX >= 0 && indexX < WIDTH && indexY >= 0 && indexY < HEIGHT;
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
	
	@Override
	public void drawShapes(ShapeRenderer batch)
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

	@Override
	public void drawLines(ShapeRenderer batch)
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

	@Override
	public void drawPolygons(PolygonSpriteBatch batch)
	{
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
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

	@Override
	public void drawSprites(SpriteBatch batch)
	{
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
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

	public void drawAll(MasterBatch batch)
	{
		batch.getPolyBatch().begin();
		drawPolygons(batch.getPolyBatch());
		batch.getPolyBatch().end();
		
		batch.getSpriteBatch().begin();
		drawSprites(batch.getSpriteBatch());
		batch.getSpriteBatch().end();
		
		batch.getShapeBatch().begin(ShapeType.Filled);
		drawShapes(batch.getShapeBatch());
		batch.getShapeBatch().end();
		
		batch.getShapeBatch().begin(ShapeType.Line);
		drawLines(batch.getShapeBatch());
		batch.getShapeBatch().end();
	}
}
