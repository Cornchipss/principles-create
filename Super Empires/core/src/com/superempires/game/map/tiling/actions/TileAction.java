package com.superempires.game.map.tiling.actions;

import com.badlogic.gdx.graphics.Texture;
import com.superempires.game.util.Callback;

public class TileAction
{
	private String name;
	private Texture buttonTexture;
	private Callback action;
	
	public TileAction(String name, Texture texture, Callback action)
	{
		this.name = name;
		this.buttonTexture = texture;
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Texture getButtonTexture() {
		return buttonTexture;
	}

	public void setButtonTexture(Texture buttonTexture) {
		this.buttonTexture = buttonTexture;
	}
	
	public void setAction(Callback c) { this.action = c; }

	public Callback getAction()
	{
		return action;
	}
}
