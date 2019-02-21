package com.superempires.game.registry;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameRegistry
{
	private static Map<String, Texture> textures = new HashMap<>();
	
	public static void registerTexture(String name, String filename)
	{
		textures.put(name, new Texture(Gdx.files.local("assets/textures/" + filename)));
	}
	
	public static Texture getTexture(String name)
	{
		return textures.get(name);
	}
}
