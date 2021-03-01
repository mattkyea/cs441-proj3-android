package com.assign3.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Assign3 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer shapeRenderer;
	ShapeRenderer s2;
	ArrayList<ShapeRenderer> shapeRenderers = new ArrayList<>();
	float radius;
	float xPos;
	float yPos;
	boolean expandShape = false;

	
	@Override
	public void create () {
		radius = 0;
		shapeRenderer = new ShapeRenderer();
		shapeRenderers.add(shapeRenderer);
		//GestureDetector.GestureListener gestureListener = this;
		Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureListener()
			{
				@Override
				public boolean touchDown(float x, float y, int pointer, int button) {
					return false;
				}

				@Override
				public boolean tap(float x, float y, int count, int button) {
					return false;
				}

				@Override
				public boolean longPress(float x, float y) {
					return false;
				}

				@Override
				public boolean fling(float velocityX, float velocityY, int button) {
					return false;
				}

				@Override
				public boolean pan(float x, float y, float deltaX, float deltaY) {
					return false;
				}

				@Override
				public boolean panStop(float x, float y, int pointer, int button) {
					return false;
				}

				@Override
				public boolean zoom(float initialDistance, float distance) {
					return false;
				}

				@Override
				public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
					return false;
				}

				@Override
				public void pinchStop() {

				}
			}
		)

		{
			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				System.out.println("down");
				expandShape = true;
				xPos = x;
				yPos = y;
				return false;
			}

			@Override
			public boolean touchUp(float x, float y, int pointer, int button) {
				System.out.println("up");
				expandShape = false;
				radius = 0;
				//shapeRenderer = new ShapeRenderer();

				return false;
			}

		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(1, 1, 0, 1);
		//shapeRenderer.line(0, 0, 500, 500);
		//shapeRenderer.rect(500, 500, 500, 500);
		shapeRenderer.circle(xPos, yPos, radius, 500);
		if(expandShape == true){
			radius++;
		}
		shapeRenderer.end();
		*/

		//this doesn't quite work, I'll probably try a custom class
		//each with X, Y, Len/Wid/Radius, shape, shapeRenderer
		for(ShapeRenderer s: shapeRenderers){
			s.begin(ShapeRenderer.ShapeType.Filled);
			s.setColor(1, 1, 0, 1);
			s.circle(xPos, yPos, radius, 500);
			if(expandShape == true){
				radius++;
			}
			s.end();
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
