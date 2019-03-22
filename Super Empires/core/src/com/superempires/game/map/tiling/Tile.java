package com.superempires.game.map.tiling;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superempires.game.map.biome.Biome;
import com.superempires.game.map.buildings.Building;
import com.superempires.game.map.units.Unit;
import com.superempires.game.objects.PhysicalObject;
import com.superempires.game.render.IDrawable;
import com.superempires.game.util.Colors;

public abstract class Tile extends PhysicalObject implements IDrawable
{
	/**
	 * Dimensions (width & height) of each tile in pixels
	 */
	public static final float DIMENSIONS = 64;
	
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
	public Tile(float x, float y, double temperature, Texture texture, Biome biome)
	{
		super(x * 0.75f * Tile.DIMENSIONS, y * Tile.DIMENSIONS - (x % 2) * Tile.DIMENSIONS / 2);
		this.temperature = temperature;
		this.biome = biome;
		
		PolygonRegion polyReg = new PolygonRegion(new TextureRegion(texture), vertices, indicies);
		
		polygonSprite = new PolygonSprite(polyReg);
		polygonSprite.setPosition(getTransform().getX(), getTransform().getY());
		polygonSprite.setOrigin(getTransform().getCenter().x, getTransform().getCenter().y);
		
		setHighlighted(false);
		setSelected(false);
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
//		Gdx.gl.glLineWidth(2);
//		
//		for(int i = 0; i + 2 < vertices.length; i += 2)
//		{
//			batch.line(
//					vertices[i] + getTransform().getX(), 
//					vertices[i + 1] + getTransform().getY(), 
//					vertices[i + 2] + getTransform().getX(), 
//					vertices[i + 3] + getTransform().getY(), 
//					Color.BLACK, Color.BLACK);
//		}
		
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
			polygonSprite.setColor(Colors.mixShading(biome.getShadingColor(), Colors.CLEAR_SHADING));
		else
			setHighlighted(highlighted);
	}
	
	public void setHighlighted(boolean h)
	{		
		highlighted = h;
		
		if(!selected) // Selected takes prescidence over highlighting
		{
			if(h)
				polygonSprite.setColor(Colors.mixShading(biome.getShadingColor(), Colors.SEMI_GREY_SHADING));
			else
				polygonSprite.setColor(Colors.mixShading(biome.getShadingColor(), Colors.GREY_SHADING));
		}
	}

	public Building getBuilding() { return building; }
	public void setBuilding(Building building)
	{
		if(this.building != null)
			this.building.setTile(null);
		
		this.building = building;
		building.setTile(this);
	}

	public Unit getUnit() { return unit; }
	public void setUnit(Unit unit)
	{
		if(this.unit != null)
			this.unit.setTile(null);
		
		this.unit = unit;
		unit.setTransform(getTransform());
		unit.setTile(this);
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
}
