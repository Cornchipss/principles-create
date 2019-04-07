package com.superempires.game.registry;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameRegistry
{
	private static Map<String, Texture> textures = new HashMap<>();
	private static Map<String, FreeTypeFontGenerator> fonts = new HashMap<>();
	private static Map<String, BitmapFont> builtFonts = new HashMap<>();
	
	public static void registerTexture(String name, String filename)
	{
		textures.put(name, new Texture(Gdx.files.local("assets/textures/" + filename)));
	}
	
	public static void registerFont(String name, String file)
	{
		fonts.put(name, new FreeTypeFontGenerator(Gdx.files.local("assets/fonts/" + file)));
	}
	
	public static FreeTypeFontGenerator getFontGenerator(String name)
	{
		return fonts.get(name);
	}
	
	public static BitmapFont buildFont(String name, FreeTypeFontParameter params)
	{
		if(params == null)
			params = new FreeTypeFontParameter();
		
		return getFontGenerator(name).generateFont(params);
	}
	
	public static Texture getTexture(String name)
	{
		return textures.get(name);
	}
	
	public static void dispose()
	{
		// Fonts
		for(String s : builtFonts.keySet())
			builtFonts.get(s).dispose();
		
		for(String s : fonts.keySet())
			fonts.get(s).dispose();
		
		// Textures
		for(String s : textures.keySet())
			textures.get(s).dispose();
	}

	public static BitmapFont getFont(String string)
	{
		return builtFonts.get(string);
	}

	public static void registerBitmapFont(String string, BitmapFont buildFont)
	{
		builtFonts.put(string, buildFont);
	}
}
