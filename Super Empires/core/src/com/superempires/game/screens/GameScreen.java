package com.superempires.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.superempires.game.map.GameMap;
import com.superempires.game.map.tiling.Tile;
import com.superempires.game.render.FancyCamera;
import com.superempires.game.render.MasterBatch;
import com.superempires.game.util.Reference;

public class GameScreen implements Screen
{
	private FancyCamera cam = new FancyCamera(Reference.VIEWPORT_WIDTH, Reference.VIEWPORT_HEIGHT);
	private MasterBatch batch;

	private GameMap map;

	public GameScreen(GameMap map)
	{
		this.map = map;
	}

	@Override
	public void show()
	{
		batch = new MasterBatch();
		cam.position.set(Gdx.graphics.getWidth() / 2, map.getHeight() * Tile.DIMENSIONS - Gdx.graphics.getHeight() / 2, 0);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		map.update(cam, delta);

		batch.setProjectionMatrix(cam.getCombined());

		map.drawAll(cam, batch);

		int spd = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? 20 : 1;

		cam.translate((Gdx.input.isKeyPressed(Input.Keys.D) ? 5 : 0) * spd, (Gdx.input.isKeyPressed(Input.Keys.W) ? 5 : 0) * spd);
		cam.translate((Gdx.input.isKeyPressed(Input.Keys.A) ? -5 : 0) * spd, (Gdx.input.isKeyPressed(Input.Keys.S) ? -5 : 0) * spd);
	}

	@Override
	public void resize(int width, int height)
	{
		cam.setViewport(width, height);
	}

	@Override
	public void pause() // Called when window loses focus or is destroyed
	{
		System.out.println("focus lost");
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
	}
}
