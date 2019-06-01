package com.superempires.game.map.tiling;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.actions.Action;
import com.superempires.game.map.actions.IActionable;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.buildings.Building;
import com.superempires.game.map.units.Unit;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.render.IDrawable;
import com.superempires.game.util.Colors;
import com.superempires.game.util.Helper;

public abstract class Tile extends PhysicalObject implements IDrawable, IActionable
{
	/**
	 * Dimensions (width & height) of each tile in pixels
	 */
	public static final float DIMENSIONS = 64;
	
	private static int currentTileId = 0;
	
	private int id;
	
	private static final float[] vertices = new float[]
	{
		DIMENSIONS     / 4, 0,
		DIMENSIONS * 3 / 4, 0,
		DIMENSIONS        , DIMENSIONS / 2,
		DIMENSIONS * 3 / 4, DIMENSIONS    ,
		DIMENSIONS     / 4, DIMENSIONS    ,
		0                 , DIMENSIONS / 2,
	};
	
	private static final short[] indicies = new short[]
	{
		// Make sure they connect
	    0, 5, 4,
	    4, 3, 0,
	    0, 3, 1,
	    1, 2, 3
	    // Makes a decent looking hexagon
	};
	
	private PolygonSprite polygonSprite;
	private boolean highlighted, selected;
	
	private Building building;
	private Unit unit;
	
	private double temperature;
	private Biome biome;
	
	/**
	 * Creates a fancy tile
	 * @param x Index X
	 * @param y Index Y
	 * @param texture Texture to render
	 */
	public Tile(float x, float y, double temperature, Texture texture, Biome biome, GameMap map)
	{
		super(x * 0.75f * Tile.DIMENSIONS, y * Tile.DIMENSIONS - (x % 2) * Tile.DIMENSIONS / 2, map);
		this.temperature = temperature;
		this.biome = biome;
		
		PolygonRegion polyReg = new PolygonRegion(new TextureRegion(texture), vertices, indicies);
		
		polygonSprite = new PolygonSprite(polyReg);
		polygonSprite.setPosition(getTransform().getX(), getTransform().getY());
		polygonSprite.setOrigin(getTransform().getCenter().x, getTransform().getCenter().y);
		
		setHighlighted(false);
		setSelected(false);
		
		this.id = currentTileId++;
	}
	
	@Override
	public void drawShapes(ShapeRenderer batch)
	{
		if(building != null)
			building.drawShapes(batch);
		if(unit != null)
			unit.drawShapes(batch);
	}
	
	@Override
	public void drawLines(ShapeRenderer batch)
	{
		if(building != null)
			building.drawLines(batch);
		if(unit != null)
			unit.drawLines(batch);
	}

	@Override
	public void drawPolygons(PolygonSpriteBatch batch)
	{
		polygonSprite.draw(batch);
		
		if(building != null)
			building.drawPolygons(batch);
		if(unit != null)
			unit.drawPolygons(batch);
	}

	@Override
	public void drawSprites(SpriteBatch batch)
	{
		if(building != null)
			building.drawSprites(batch);
		if(unit != null)
			unit.drawSprites(batch);
	}
	
	public void setSelected(boolean s)
	{
		selected = s;
		
		if(s)
			polygonSprite.setColor(Colors.CLEAR_SHADING);
		else
			setHighlighted(highlighted);
	}
	
	public void setHighlighted(boolean h)
	{		
		highlighted = h;
		
		if(!selected) // Selected takes prescidence over highlighting
		{
			if(h)
				polygonSprite.setColor(Colors.LIGHT_GREY_SHADING);
			else
				polygonSprite.setColor(Colors.GREY_SHADING);
		}
	}

	public Building getBuilding() { return building; }
	public void setBuilding(Building building)
	{
		if(this.building != null)
			this.building.setTile(null);
		
		this.building = building;
		
		if(building != null)
		{
			building.setTransform(getTransform());
			building.setTile(this);
		}
	}

	public Unit getUnit() { return unit; }
	public void setUnit(Unit unit)
	{
		setUnit(unit, false);
	}	
	public void setUnit(Unit unit, boolean redoRadius)
	{
		if(this.unit != null)
			removeUnit();
		
		this.unit = unit;
		
		if(unit != null)
		{
			unit.setTransform(getTransform());
			unit.setTile(this);
		}
		
		if(redoRadius)
		{
			getMap().resetMovement();
		}
	}
	
	public Biome getBiome() { return biome; }
	
	public double getTemperature() { return temperature; }

	/**
	 * The "time" it takes to travel on this tile
	 * 1 is a normal amount of time
	 * 0.5 is a path
	 * 3 is a slow tile
	 * @return the time
	 */
	public abstract double getTravelTime();
	
	/**
	 * Can the tile be walked on
	 * @return true if it can be walked on, false if not
	 */
	public abstract boolean isWalkable();
	
	/**
	 * Can the tile be sailed on
	 * @return true if it can be sailed on, false if not
	 */
	public abstract boolean isSailable();

	public boolean canHoldUnit(Unit unit) { return this.getUnit() == null; }

	@Override
	public int hashCode()
	{
		return getId();
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Tile)
		{
			Tile t = (Tile)obj;
			
			return t.getId() == getId();
		}
		
		return false;
	}
	
	public int getId() { return id; }

	@Override
	public List<Action> getActions()
	{
		return Helper.combineLists(
				getUnit() != null && getUnit().getActions() != null ? getUnit().getActions() : new ArrayList<Action>(), 
				getBuilding() != null && getBuilding().getActions() != null ? getBuilding().getActions() : new ArrayList<Action>());
	}

	public abstract String getName();

	public void removeUnit()
	{
		unit.setTile(null);
		this.unit = null;
		
		this.getMap().redrawRadius();
	}
}
