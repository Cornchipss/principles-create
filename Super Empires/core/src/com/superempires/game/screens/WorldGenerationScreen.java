package com.superempires.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.superempires.game.SuperEmpires;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.biome.BiomeType;
import com.superempires.game.map.biome.ColdPlainsBiome;
import com.superempires.game.map.biome.CragBiome;
import com.superempires.game.map.biome.DesertBiome;
import com.superempires.game.map.biome.HotPlainsBiome;
import com.superempires.game.map.biome.OceanBiome;
import com.superempires.game.map.biome.PlainsBiome;
import com.superempires.game.map.biome.SnowBiome;
import com.superempires.game.map.generation.DefaultMapGenerator;
import com.superempires.game.map.generation.MapGenerator;
import com.superempires.game.map.tiling.Tile;

public class WorldGenerationScreen implements Screen
{
	private SpriteBatch batch;
	private BitmapFont font;
	private GlyphLayout layout, subLayout;
	
	private OrthographicCamera cam;
	
	private volatile boolean done = false;
	
	@Override
	public void show()
	{
		batch = new SpriteBatch();
		font = new BitmapFont();
		layout = new GlyphLayout(font, "Super Empires!");
		subLayout = new GlyphLayout(font, "");
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		font.setColor(Color.WHITE);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		try
		{
			if(layout.width != 0)
				font.draw(batch, layout, -layout.width / 2, layout.height / 2);
			if(subLayout.width != 0)
				font.draw(batch, subLayout, -subLayout.width / 2, layout.height + 20 + subLayout.height / 2);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		} // BitmapFonts are super buggy and i have on idea why
		
		batch.end();
	}
	
	public void setText(String text)
	{
		layout.setText(font, text);
	}
	
	public void setSubText(String text)
	{
		subLayout.setText(font, text);
	}

	@Override
	public void resize(int width, int height)
	{
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		
		cam.update();
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void dispose()
	{
		System.out.println("DISPOSED!");
		batch.dispose();
		font.dispose();
	}

	public void generate(final Tile[][] tiles, final long seed)
	{
		final WorldGenerationScreen screen = this;
		
		Thread thread = new Thread(new Runnable()
		{	
			@Override
			public void run()
			{
				MapGenerator gen = new DefaultMapGenerator(screen);
				
				setText("Registering Biomes");
				gen.registerBiome(new PlainsBiome(), BiomeType.LAND);
				gen.registerBiome(new DesertBiome(), BiomeType.LAND);
				gen.registerBiome(new SnowBiome(), BiomeType.LAND);
				gen.registerBiome(new HotPlainsBiome(), BiomeType.LAND);
				gen.registerBiome(new CragBiome(), BiomeType.LAND);
				gen.registerBiome(new ColdPlainsBiome(), BiomeType.LAND);
				gen.registerBiome(new OceanBiome(), BiomeType.WATER);
				
				gen.generateMap(tiles, seed);
				
				done = true;
			}
		});
		
		thread.setName("world-gen-thread");
		thread.start();
	}

	public void complete(Tile[][] tiles, SuperEmpires instance)
	{
		instance.setScreen(new MainScreen(new GameMap(tiles)));
	}
	
	public boolean done() { return done; }
}
