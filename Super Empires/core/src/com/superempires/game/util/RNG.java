package com.superempires.game.util;

import java.util.Random;

import libs.noise.SimplexNoise;

public class RNG
{
	private long seed;
	private SimplexNoise noise;
	private Random rdm;
	
	public RNG(long seed)
	{
		this.seed = seed;
		
		rdm = new Random(seed);
		noise = new SimplexNoise(seed);
	}
	
	public Random getRandom() { return rdm; }
	public SimplexNoise getNoise() { return noise; }
	
	public long getSeed() { return seed; }
}
