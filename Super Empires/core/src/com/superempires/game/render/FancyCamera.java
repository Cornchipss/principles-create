package com.superempires.game.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class FancyCamera
{
	private OrthographicCamera cam;
	public final Vector3 position;
	
	public FancyCamera(float w, float h)
	{
		cam = new OrthographicCamera(w, h);
		
		position = cam.position;
	}
	
	public void update()
	{
		cam.update();
	}
	
	public void translate(float x, float y)
	{
		cam.translate(x, y);
	}
	
	public void setViewport(float w, float h)
	{
		cam.viewportWidth = w;
		cam.viewportHeight = h;
	}
	
	public Vector2 screenToWorldCoords(float x, float y)
	{
		Vector3 unproj = cam.unproject(new Vector3(x, y, 0));
		
		return new Vector2(unproj.x, unproj.y);
	}
	
	public Matrix4 getCombined() { return cam.combined; }

	public OrthographicCamera getCamera() { return cam; }
}
