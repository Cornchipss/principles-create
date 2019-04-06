package com.superempires.game.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.superempires.game.gui.GUI;
import com.superempires.game.gui.GUIButton;
import com.superempires.game.gui.GUIElementHolder;
import com.superempires.game.gui.GUIText;
import com.superempires.game.map.buildings.Building;
import com.superempires.game.map.pathing.Path;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.map.units.Unit;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.render.MasterBatch;
import com.superempires.game.render.RenderQue;
import com.superempires.game.util.Callback;
import com.superempires.game.util.Helper;
import com.superempires.game.util.Vector2i;

public class GameMap
{
	private final int WIDTH, HEIGHT;
	
	private final Tile[][] tiles;
	
	private Tile[] selectedTiles = new Tile[0];
	
	private Tile hoveredTile, selectedTile;
	
	private Vector2i startPosition;
	
	private GUI gui;
	
	private RenderQue renderPhase;
	
	private GUIElementHolder holder;
	
	public GameMap(Tile[][] tiles, Vector2i startPosition)
	{
		WIDTH = tiles[0].length;
		HEIGHT = tiles.length;
		
		this.startPosition = startPosition;
		this.tiles = tiles;
		
		gui = new GUI();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.color = Color.WHITE;
		
		FreeTypeFontParameter param2 = new FreeTypeFontParameter();
		param2.color = Color.WHITE;
		
		holder = new GUIElementHolder(new Transform(-w / 2, -h / 2, w, 100), gui);
		
		holder.addElement
		(
			new GUIText
			(
				0, 80, w, 0, gui, 
				GameRegistry.getFont("font-default", param), 
				"Hello World!", 
				Align.center
			),
			Align.bottomRight,
			RenderQue.MEDIUM
		).addElement
		(
			new GUIButton
			(
				new Transform(w / 2 - 100 / 2, 10, 100, 60), gui,
				GameRegistry.getFont("font-default", param2), 
				new Callback()
				{
					@Override
					public void run(Object... args)
					{
						System.out.println("123, Do Re Me, Baby You & Me!");
					}
				}, "Do Stuff"
			).setUnhoveredColor(new Color(0, 0, 0, 0.1f)).setHoveredColor(new Color(0, 0, 0, 0.3f)),
			Align.bottomRight,
			RenderQue.MEDIUM
		).setBackground(new Color(0, 0, 0, 0.2f));
		
		gui.addElement(holder, RenderQue.LOW);
	}
	
	private Map<Unit, Double> radiusRemaining = new HashMap<>();
	private Map<Tile, Path> paths;
	
	private FancyCamera zeroCam = new FancyCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
	private float winWidth, winHeight;
	
	public void update(FancyCamera cam, float delta)
	{
		if(winWidth == 0 || winHeight == 0)
		{
			winWidth = Gdx.graphics.getWidth();
			winHeight = Gdx.graphics.getHeight();
		}
		
		if(winWidth != Gdx.graphics.getWidth() || winHeight != Gdx.graphics.getHeight())
		{
			winWidth = Gdx.graphics.getWidth();
			winHeight = Gdx.graphics.getHeight();
			
			holder.setTransform(new Transform(-winWidth / 2, -winHeight / 2, winWidth, 100));
			
			gui.onResize(winWidth, winHeight);
			
			zeroCam = new FancyCamera(winWidth, winHeight);
		}
		
		gui.update(delta, zeroCam);
		
		if(startPosition != null)
		{
			Tile tile = tiles[startPosition.y][startPosition.x];
			
			cam.position.set(tile.getTransform().getX(), tile.getTransform().getY(), 0);
			
			startPosition = null;
		}
		
	    Transform bounds = getBounds();
	    
	    cam.position.set(
    		Helper.clamp(cam.position.x, Gdx.graphics.getWidth() / 2 + bounds.getX() * Tile.DIMENSIONS, 
    				bounds.getWidth() * Tile.DIMENSIONS - Gdx.graphics.getWidth() / 2), 
    		Helper.clamp(cam.position.y, Gdx.graphics.getHeight() / 2 + bounds.getY() * 2 * Tile.DIMENSIONS, 
    				bounds.getHeight() * Tile.DIMENSIONS - Gdx.graphics.getHeight() / 2), 
    		cam.position.z);
	    
		cam.update();
		
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		
		// Mouse within window
		if(mouseX >= 0 && mouseX <= Gdx.graphics.getWidth() && mouseY >= 0 && mouseY <= Gdx.graphics.getHeight())
		{
		    Vector2 mouseWorldCoords = cam.screenToWorldCoords(Gdx.input.getX(), Gdx.input.getY());
		    
			Vector2i index = worldCoordsToTileIndex(mouseWorldCoords);
			
			boolean isInSelectedRange = isInSelectedRange(hoveredTile);
			
			if(within(index.x, index.y))
			{
				// Handles the old highlighted tile
				if(hoveredTile != null && !isInSelectedRange)
					hoveredTile.setHighlighted(false);
				
				// Gets the new highlighted tile
				hoveredTile = getTile(index.x, index.y);
				
				
				if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) 
						|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
				{
					if(selectedTile != null)
					{
						if(isInSelectedRange && selectedTile.getUnit() != null && hoveredTile.canHoldUnit(selectedTile.getUnit()))
						{
							hoveredTile.setUnit(selectedTile.getUnit());
							
							radiusRemaining.put(selectedTile.getUnit(), 
									radiusRemaining.getOrDefault(selectedTile.getUnit(), 0.0) + 
									paths.get(hoveredTile).getTravelTime());
							
							selectedTile.setUnit(null);
						}
						
						clearSelectedTiles();
					}
					
					if(!Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
					{
						hoveredTile.setSelected(true);
						selectedTile = hoveredTile; // They are selecting the one they're hovering
						
						// Show radius if it has a unit
						if(selectedTile.getUnit() != null)
						{
							paths = pathfindTiles(
									index.x, 
									index.y, 
									selectedTile.getUnit().getTravelRadius() - 
									radiusRemaining.getOrDefault(selectedTile.getUnit(), 0.0));
							
							selectedTiles = new Tile[paths.size()];
							
							int i = 0;
							for(Tile t : paths.keySet())
							{
								t.setHighlighted(true);
								
								selectedTiles[i] = t;
								i++;
							}
						}
					}
				}
				else
					hoveredTile.setHighlighted(true);
			}
		}
	}
	
	public boolean isInSelectedRange(Tile t)
	{
		if(selectedTiles != null && t != null)
			for(Tile tile : selectedTiles)
				if(tile.equals(t))
					return true;
		return false;
	}
	
	public void clearSelectedTiles()
	{
		if(selectedTile != null)
		{
			selectedTile.setSelected(false);
			
			for(Tile t : selectedTiles)
			{
				t.setHighlighted(false);
			}
			
			selectedTiles = new Tile[0];
		}
	}
	
	public boolean within(int indexX, int indexY)
	{
		return indexX >= 0 && indexX < WIDTH && indexY >= 0 && indexY < HEIGHT;
	}
	
	/**
	 * Finds a bunch of tiles in a circleish thing
	 * @param indexX The x index
	 * @param indexY The y index
	 * @param walkTime The "time" they have to walk
	 */
	public Map<Tile, Path> pathfindTiles(int indexX, int indexY, double walkTime)
	{
		Map<Tile, Path> paths = new HashMap<>();
		List<Path> currentPaths = new ArrayList<>();
		
		currentPaths.add(new Path(getTile(indexX, indexY), 0));
		
		while(currentPaths.size() != 0)
		{
			List<Path> newPaths = new ArrayList<>();
			
			for(Path path : currentPaths)
			{
				Vector2i index = getTileIndex(path.getTile());
				
				Tile[] tiles = getAdjacentTiles(index.x, index.y);
				
				for(Tile tile : tiles)
				{
					if(tile != null && tile.isWalkable())
					{
						double newTime = path.getTravelTime() + tile.getTravelTime();
						
						if(newTime <= walkTime)
						{
							if(!paths.containsKey(tile))
							{
								Path child = path.giveBirth(tile, newTime);
								newPaths.add(child);
								
								paths.put(tile, child);
							}
						}
					}
				}
			}
			
			currentPaths = newPaths;
		}
		
		return paths;
	}
	
	private Vector2i getTileIndex(Tile tile)
	{
		return new Vector2i(
				(int) (tile.getTransform().getCenter().x / (0.75f * Tile.DIMENSIONS)), 
				(int) (tile.getTransform().getCenter().y / Tile.DIMENSIONS));
	}
	
	/**
	 * Gets adjacent tiles in a semi-quick manner
	 * This only works if the radius is 3 or below.
	 * @param indexX The index x to check around
	 * @param indexY The index y to check around
	 * @param radius The radius of the search (best if 3 or below)
	 * @return The tiles in an array that are surrounding said index.
	 */
	public Tile[] getAdjacentTiles(int indexX, int indexY)
	{
		final int RADIUS = 1;
		
		int tiles = 0;
		for(int i = 0; i <= RADIUS; i++)
			tiles += 6 * i;
		
		Tile[] toReturn = new Tile[tiles];
		
		int i = 0;
		
		for(int dX = -RADIUS; dX <= RADIUS; dX++)
		{
			for(int dY = -RADIUS; dY <= RADIUS; dY++)
			{
				if(dX != 0 || dY != 0)
				{
					int ix = dX + indexX, iy = dY + indexY;
					
					if(within(ix, iy))
					{
						if(Helper.distanceSquared(getTile(indexX, indexY).getTransform().getCenter(), 
								getTile(ix, iy).getTransform().getCenter()) <= (RADIUS * Tile.DIMENSIONS) * (RADIUS * Tile.DIMENSIONS))
						{
							toReturn[i] = getTile(ix, iy);
							i++;
						}
					}
				}
			}
		}
		
		return toReturn;
	}
	
	public Vector2i worldCoordsToTileIndex(Vector2 coords)
	{
		int realIndexX, realIndexY;
		
		int xIndexIsh = realIndexX = (int) (coords.x / (0.75f * Tile.DIMENSIONS));
		int yIndexIsh = realIndexY = (int) (coords.y / Tile.DIMENSIONS);
		
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
	public Building getBuilding(int x, int y) { return getTile(x, y).getBuilding(); }
	public Unit getUnit(int x, int y) { return getTile(x, y).getUnit(); }
	
	public Tile setTile(int x, int y, Tile t) { Tile temp = tiles[y][x]; tiles[y][x] = t; return temp;  }
	public Building setBuilding(int x, int y, Building b) { Building temp = getBuilding(x, y); getTile(x, y).setBuilding(b); return temp; }
	public Unit setUnit(int x, int y, Unit u) { Unit temp = getUnit(x, y); getTile(x, y).setUnit(u); return temp; }

	public float getWidth() { return WIDTH * 0.75f + 0.25f; } // 0.75f because each hexagon is actually only 3/4 the width because of how they are layered, and + 0.25f because of the corner on the last row
	public float getHeight() { return HEIGHT; }

	public Transform getBounds()
	{
		return new Transform(0, -0.25f, getWidth(), getHeight());
	}
	
	public void drawShapes(Vector2i[] drawBounds, ShapeRenderer batch)
	{
		if(renderPhase == RenderQue.LOW)
			for(int y = drawBounds[0].y; y < drawBounds[1].y && y < HEIGHT; y++)
				for(int x = drawBounds[0].x; x < drawBounds[1].x && x < WIDTH; x++)
					if(tiles[y][x] != null)
						tiles[y][x].drawShapes(batch);
	}

	public void drawLines(Vector2i[] drawBounds, ShapeRenderer batch)
	{
		if(renderPhase == RenderQue.LOW)
			for(int y = drawBounds[0].y; y < drawBounds[1].y && y < HEIGHT; y++)
				for(int x = drawBounds[0].x; x < drawBounds[1].x && x < WIDTH; x++)
					if(tiles[y][x] != null)
						tiles[y][x].drawLines(batch);
	}

	public void drawPolygons(Vector2i[] drawBounds, PolygonSpriteBatch batch)
	{
		if(renderPhase == RenderQue.LOW)
			for(int y = drawBounds[0].y; y < drawBounds[1].y && y < HEIGHT; y++)
				for(int x = drawBounds[0].x; x < drawBounds[1].x && x < WIDTH; x++)
					if(tiles[y][x] != null)
						tiles[y][x].drawPolygons(batch);
	}
	
	public void drawSprites(Vector2i[] drawBounds, SpriteBatch batch)
	{
		if(renderPhase == RenderQue.LOW)
			for(int y = drawBounds[0].y; y < drawBounds[1].y && y < HEIGHT; y++)
				for(int x = drawBounds[0].x; x < drawBounds[1].x && x < WIDTH; x++)
					if(tiles[y][x] != null)
						tiles[y][x].drawSprites(batch);
	}
	
	public Vector2i[] getDrawBounds(FancyCamera cam)
	{
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		Vector2i pos = worldCoordsToTileIndex(cam.getPosition2().cpy().sub(width / 2 + Tile.DIMENSIONS / 4, height / 2 + Tile.DIMENSIONS / 2));
		
		// + 2/3 accounts for edges
		int maxY = (int) (pos.y + height / Tile.DIMENSIONS + 3);
		int maxX = (int) (pos.x + width / (0.75f * Tile.DIMENSIONS) + 2);
		
		return new Vector2i[] { pos, new Vector2i(maxX, maxY) };
	}

	public void drawAll(FancyCamera cam, MasterBatch batch)
	{
		Vector2i[] drawBounds = getDrawBounds(cam);
		
		for(int i = 0; i <= RenderQue.HIGHEST.getLevel(); i++)
		{
			this.renderPhase = RenderQue.fromInt(i);
			
			if(renderPhase == RenderQue.MEDIUM)
			{
				batch.setProjectionMatrix(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()).combined);
				gui.draw(batch);
				batch.setProjectionMatrix(cam.getCombined());
			}
			
			batch.begin(batch.getPolyBatch());
			drawPolygons(drawBounds, batch.getPolyBatch());
			batch.end(batch.getPolyBatch());
			
			batch.begin(batch.getSpriteBatch());
			drawSprites(drawBounds, batch.getSpriteBatch());
			batch.end(batch.getSpriteBatch());
			
			batch.begin(batch.getShapeBatch(), ShapeType.Filled);
			drawShapes(drawBounds, batch.getShapeBatch());
			batch.end(batch.getShapeBatch());
			
			batch.begin(batch.getShapeBatch(), ShapeType.Line);
			drawLines(drawBounds, batch.getShapeBatch());
			batch.end(batch.getShapeBatch());
		}
	}
}
