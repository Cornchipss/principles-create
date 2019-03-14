package com.superempires.game.gui;

public enum Alignment
{
	LEFT_TOP   (0b0100),
	LEFT       (0b0000),
	LEFT_BOTTOM(0b1000),
	
	CENTER_TOP   (0b0101),
	CENTER       (0b0001),
	CENTER_BOTTOM(0b1001),
	
	RIGHT_TOP   (0b0110),
	RIGHT       (0b0010),
	RIGHT_BOTTOM(0b1010);
	
	private final int ALIGNMENT_CODE;
	
	private Alignment(int a)
	{
		ALIGNMENT_CODE = a;
	}
	
	/**
	 * Returns true if this should be aligned to the top
	 * @return true if this should be aligned to the top
	 */
	public boolean top()
	{
		return (ALIGNMENT_CODE & 0b0100) == 0b0100;
	}
	
	/**
	 * Returns true if this should be aligned to the bottom
	 * @return true if this should be aligned to the bottom
	 */
	public boolean bottom()
	{
		return (ALIGNMENT_CODE & 0b1000) == 0b1000;
	}
	
	/**
	 * Returns true if this should be aligned to the middle of the y axis
	 * @return true if this should be aligned to the middle of the y axis
	 */
	public boolean middle()
	{
		return (ALIGNMENT_CODE & 0b1100) == 0;
	}
	
	/**
	 * Returns true if this should be aligned to the left
	 * @return true if this should be aligned to the left
	 */
	public boolean left()
	{
		return (ALIGNMENT_CODE & 0b00) == 0;
	}
	
	/**
	 * Returns true if this should be aligned to the center of the x axis
	 * @return true if this should be aligned to the center of the x axis
	 */
	public boolean center()
	{
		return (ALIGNMENT_CODE & 0b01) == 0b01;
	}
	
	/**
	 * Returns true if this should be aligned to the right
	 * @return true if this should be aligned to the right
	 */
	public boolean right()
	{
		return (ALIGNMENT_CODE & 0b10) == 0b10;
	}
}
