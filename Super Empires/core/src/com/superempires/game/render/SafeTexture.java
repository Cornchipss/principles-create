package com.superempires.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class SafeTexture
{
	private Texture texture = null;
	
	public SafeTexture(final FileHandle handle)
	{
		Gdx.app.postRunnable(new Runnable()
		{
			@Override
			public void run()
			{
				texture = new Texture(handle);
				
			}
		});
	}
	
	public boolean ready() { return texture != null; }
}
