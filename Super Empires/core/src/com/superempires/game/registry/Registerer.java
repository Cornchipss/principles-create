package com.superempires.game.registry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Registerer
{
	public static void registerAll()
	{
		///
		// FONTS
		///
		GameRegistry.registerFont("font-title", "MeathFLF.ttf");
		GameRegistry.registerFont("font-default", "CELTG___.TTF");
		
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.color = Color.WHITE;
		
		GameRegistry.registerBitmapFont("font-default-white", GameRegistry.buildFont("font-default", param));
		
		///
		// TEXTURES
		///
		
		// GUI
		GameRegistry.registerTexture("button-plant", "gui/buttons/button-plant.png");
		
		// Tiles
		GameRegistry.registerTexture("tile-crag", "tiles/tile-crag.png");
		GameRegistry.registerTexture("tile-snow", "tiles/tile-snow.png");
		GameRegistry.registerTexture("tile-desert", "tiles/tile-sand.png");
		GameRegistry.registerTexture("tile-grass", "tiles/tile-grass.png");
		GameRegistry.registerTexture("tile-ice", "tiles/tile-ice.png");
		GameRegistry.registerTexture("tile-water", "tiles/tile-water.png");
		
		// Buildings
		GameRegistry.registerTexture("building-tree-top", "buildings/building-tree-top.png");
		GameRegistry.registerTexture("building-tree-bottom", "buildings/building-tree-bottom.png");
		GameRegistry.registerTexture("building-town-hall", "buildings/building-town-hall.png");
		
		// Units
		GameRegistry.registerTexture("unit-colonization", "units/unit-colonization.png");
		GameRegistry.registerTexture("unit-troop-basic", "units/unit-troop-basic.png");
	}
}
