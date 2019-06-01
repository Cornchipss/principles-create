package com.superempires.game.objects;

import com.superempires.game.map.GameMap;

public class GameObject
{
	private GameMap map;
	
	public GameObject(GameMap map)
	{
		this.map = map;
	}

	public GameMap getMap() { return map; }
	public void setMap(GameMap map) { this.map = map; }
}
