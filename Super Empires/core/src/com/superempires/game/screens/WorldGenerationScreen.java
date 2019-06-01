package com.superempires.game.screens;

import java.lang.Thread.UncaughtExceptionHandler;

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
import com.superempires.game.map.generation.DefaultMapGenerator;
import com.superempires.game.map.generation.MapGenerator;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.util.Vector2i;

public class WorldGenerationScreen implements Screen
{
	private SpriteBatch batch;
	private BitmapFont font;
	private GlyphLayout layout, subLayout;

	private OrthographicCamera cam;

	private volatile boolean done = false;
	
	private volatile Vector2i startPosition;

	private final GameMap map;
	private final long SEED;
	
	public WorldGenerationScreen(int w, int h, long seed)
	{
		this.SEED = seed;

		map = new GameMap(new Tile[h][w]);
	}

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
		if(done())
		{
			map.setStartingPosition(startPosition);
			SuperEmpires.swapScreen(new GameScreen(map));
		}
		else
		{
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			if(batch != null)
			{
				batch.setProjectionMatrix(cam.combined);
				batch.begin();

				// BitmapFonts are super buggy and i have on idea why
				try
				{
					if(layout.width != 0)
						font.draw(batch, layout, -layout.width / 2, layout.height / 2);
					if(subLayout.width != 0)
						font.draw(batch, subLayout, -subLayout.width / 2, layout.height + 20 + subLayout.height / 2);
				}
				catch(Exception ex)
				{
					
				}

				batch.end();
			}
		}
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
		batch.dispose();
		font.dispose();

		batch = null;
		font = null;
	}

	public void generate()
	{
		final WorldGenerationScreen screen = this;

		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				MapGenerator gen = new DefaultMapGenerator(screen);

				setText("Registering Biomes");

				startPosition = gen.generateMap(map, SEED);

				done = true;
			}
		});

		thread.setName("world-gen-thread");
		thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		});
		thread.start();
	}

	public boolean done() { return done; }
	public Vector2i getStartPosition() { return startPosition; }
	
	public GameMap getMap() { return map; }
}
