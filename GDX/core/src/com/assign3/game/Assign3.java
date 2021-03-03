package com.assign3.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class Assign3 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer shapeRenderer;
	ShapeRenderer s2;
	private Stage stage;
	ArrayList<CustomShape> shapes = new ArrayList<>();
	float radius;
	float xPos;
	float yPos;
	BitmapFont font;
	TextButton button;
	TextButton.TextButtonStyle textButtonStyle;
	TextureAtlas buttonAtlas;
	boolean expandShape = false;

	public class CustomShape{
		public ShapeRenderer shapeRenderer;
		public float x, y, l, w, r;
		boolean expanding = false;

		CustomShape(float x, float y, float l, float w, float r, boolean expanding){
			this.x = x;
			this.y = y;
			this.l = l;
			this.w = w;
			this.r = r;
			this.expanding = expanding;
			shapeRenderer = new ShapeRenderer();
		}
	}

	@Override
	public void create () {
		radius = 0;
		shapeRenderer = new ShapeRenderer();
		//shapeRenderers.add(shapeRenderer);
		//GestureDetector.GestureListener gestureListener = this;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont();
		//System.out.println(Gdx.files.internal(""));
		Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
		Button button2 = new TextButton("Text Button",mySkin);
		button2.setSize(200,200);
		button2.setPosition(500,500);
		button2.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				//outputLabel.setText("Press a Button");
				System.out.println("button up");
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//outputLabel.setText("Pressed Text Button");
				System.out.println("button down");
				return true;
			}
		});
		stage.addActor(button2);
		stage.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				//outputLabel.setText("Press a Button");
				System.out.println("screen up");
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//outputLabel.setText("Pressed Text Button");
				System.out.println("screen down");
				return true;
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
		for(CustomShape s: shapes){
			s.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			s.shapeRenderer.setColor(1, 1, 0, 1);
			s.shapeRenderer.circle(s.x, s.y, s.r, 500);
			if(s.expanding == true){
				s.r++;
			}
			s.shapeRenderer.end();
		}

		stage.act();
		stage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
