package com.superempires.game.render;

public enum RenderQue
{
	LOWEST(0),
	LOW(1),
	LOW_MEDIUM(2),
	MEDIUM(3),
	HIGH_MEDIUM(4),
	HIGH(5),
	HIGHEST(6), 
	MAX_RENDER(HIGHEST.getLevel());
	
	private int n;
	
	RenderQue(int n)
	{
		this.n = n;
	}
	
	public static RenderQue fromInt(int i)
	{
		switch(i)
		{
		case 0:
			return LOWEST;
		case 1:
			return LOW;
		case 2:
			return LOW_MEDIUM;
		case 3:
			return MEDIUM;
		case 4:
			return HIGH_MEDIUM;
		case 5:
			return HIGH;
		case 6:
			return HIGHEST;
		default:
			return null;
		}
	}
	
	public int getLevel() { return n; }
}
