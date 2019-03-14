package com.superempires.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.superempires.game.SuperEmpires;
import com.superempires.game.gui.Alignment;
import com.superempires.game.gui.GUI;
import com.superempires.game.gui.GUIButton;
import com.superempires.game.gui.GUIText;
import com.superempires.game.objects.properties.Transform;
import com.superempires.game.registry.GameRegistry;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.render.MasterBatch;
import com.superempires.game.util.Callback;

public class MainMenuScreen implements Screen
{
	private FancyCamera cam;
	private MasterBatch batch;
	private GUI gui;
	
	private WorldGenerationScreen genScreen;
	
	@Override
	public void show()
	{
		batch = new MasterBatch();
		
		gui = new GUI();
		
		FreeTypeFontParameter params = new FreeTypeFontParameter();
		params.size = 48 * 2;
		params.color = Color.RED;
		
		FreeTypeFontParameter params2 = new FreeTypeFontParameter();
		params2.color = Color.BLACK;
		
		final float WINDOW_BORDER = 100;
		
		gui.addElement(
				new GUIButton(
					new Transform(-50, -Gdx.graphics.getHeight() / 2 + WINDOW_BORDER, 100, 40), 
					gui,
					GameRegistry.getFont("font-default", params2),
					new Callback()
					{
						@Override
						public void run(Object... args)
						{
							genScreen = new WorldGenerationScreen(1000, 1000, System.nanoTime());
							
							SuperEmpires.swapScreen(genScreen);
							
							genScreen.generate();
						}
					}, 
					"PLAY"));
		
		gui.addElement(new GUIText(-Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2 - WINDOW_BORDER, gui, 
				GameRegistry.getFont("font-title", params), "Super Empires", 
				Alignment.CENTER_TOP));
		
		cam = new FancyCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// This is null if this has been disposed, and no this cannot go at the top
		// Because this app is multithreaded this can happen midway through so don't put it at the top
		// Same with all the other checks
		if(batch != null)
		{
			batch.setProjectionMatrix(cam.getCombined());
		
			if(gui != null)
			{
				gui.update(delta, cam);
				
				if(batch != null)
					batch.draw(gui);
			}
		}
	}

	@Override
	public void resize(int width, int height)
	{
		cam.setViewport(width, height);
		
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
		batch = null;
		
		gui.dispose();
		gui = null;
	}
}
