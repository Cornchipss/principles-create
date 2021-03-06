package com.superempires.game.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Helper
{
	public static int clamp(int v, int min, int max)
	{
		return v < min ? min : v > max ? max : v;
	}

	public static float clamp(float v, float min, float max)
	{
		return v < min ? min : v > max ? max : v;
	}

	public static double clamp(double v, double min, double max)
	{
		return v < min ? min : v > max ? max : v;
	}

	public static float distanceSquared(Vector2 pointA, Vector2 pointB)
	{
		float distX = pointA.x - pointB.x;
		float distY = pointA.y - pointB.y;

		return distX * distX + distY * distY;
	}

	public static int linearSearch(Object[] arr, Object o)
	{
		for(int i = 0; i < arr.length; i++)
			if(arr[i].equals(o))
				return i;
		return -1;
	}

	public static void arrayToRatios(double[] ratios)
	{
		double sum = 0;
		for(double d : ratios)
			sum += d;
		
		for(int i = 0; i < ratios.length; i++)
			ratios[i] /= sum;
	}
	
	public static <T> List<T> combineLists(List<? extends T> l1, List<? extends T> l2)
	{
		List<T> list = new ArrayList<>(l1.size() + l2.size());
		
		list.addAll(l1);
		list.addAll(l2);
		
		return list;
	}
}
