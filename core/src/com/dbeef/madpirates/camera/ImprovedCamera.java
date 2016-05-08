package com.dbeef.madpirates.camera;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ImprovedCamera extends OrthographicCamera{
	
	double timer = 0;
	double acceleration;
	
	static double maxZoom = 2f;
	static double minZoom = 0.45f;
	
	public ImprovedCamera (float viewportWidth, float viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
		this.near = 0;

		this.zoom = 2f;
		update();
	}
	public void takeZoomDelta(double deltaZoom){

		acceleration += deltaZoom;

		if (acceleration > 0)
			acceleration -= Gdx.graphics.getDeltaTime() * 0.2f;
		if (acceleration < 0)
			acceleration += Gdx.graphics.getDeltaTime() * 0.2f;

		this.zoom += acceleration;

		if (this.zoom > maxZoom) {
			this.zoom = (float) maxZoom;
			acceleration = 0;
		}
		if (this.zoom < minZoom) {
			this.zoom = (float) minZoom;
			acceleration = 0;
		}

	}
	
}