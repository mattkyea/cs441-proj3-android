package com.assign3.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class Assign3 extends ApplicationAdapter {
	private Stage stage;
	ArrayList<CustomShape> shapes = new ArrayList<>();
	boolean buttonPressed = false;
	boolean expandShape = false;
	String currentShape = "Circle";


	public abstract class CustomShape{
		public ShapeRenderer shapeRenderer;
		public float x, y;
		boolean expanding = false;

		CustomShape(float x, float y, boolean expanding){
			this.x = x;
			this.y = y;
			this.expanding = expanding;
			shapeRenderer = new ShapeRenderer();
		}

		public abstract void draw();
		public abstract void  expand();

	}

	public class Circle extends CustomShape{

		public float r;

		Circle(float x, float y,  float r, boolean expanding){
			super(x, y, expanding);
			this.r = r;
		}

		@Override
		public void draw() {
			shapeRenderer.circle(x, y, r);
		}

		@Override
		public void expand() {
			if(expanding == true) r++;
		}

	}

	public class Square extends CustomShape{

		public float w, h;

		Square(float x, float y,  float w, float h, boolean expanding){
			super(x, y, expanding);
			this.w = w;
			this.h = h;
		}

		@Override
		public void draw() {
			shapeRenderer.rect(x, y, w, h);
		}

		@Override
		public void expand() {
			if(expanding == true){ w++; h++;}
		}

	}

	public class Cone extends CustomShape{

		public float z, r, h;
		public boolean flipFlop = false;

		Cone(float x, float y,  float z, float r, float h, boolean expanding){
			super(x, y, expanding);
			this.r = r;
			this.z = z;
			this.h = h;
		}

		@Override
		public void draw() {
			//shapeRenderer.triangle(x, y, z, r, h, 5);
			shapeRenderer.ellipse(x, y, r, h);
		}

		@Override
		public void expand() {
			if(expanding == true){
				h++;
				if(flipFlop == false){
					r++;
					flipFlop = true;
				}else{
					flipFlop = false;
				}
			}
		}

	}

	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		//set up buttons
		Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));


		//BUG - bottom of buttons don't work
		TextButton circleButton = new TextButton("Circle", mySkin);
		circleButton.setTransform(true);
		circleButton.setSize(200,200);
		circleButton.setRotation(90);
		circleButton.setPosition(2000,850);
		circleButton.getLabel().setFontScale(2f);

		TextButton squareButton = new TextButton("Square", mySkin);
		squareButton.setTransform(true);
		squareButton.setSize(200,200);
		squareButton.setRotation(90);
		squareButton.setPosition(2000,450);
		squareButton.getLabel().setFontScale(2f);

		TextButton coneButton = new TextButton("Triangle", mySkin);
		coneButton.setTransform(true);
		coneButton.setSize(200,200);
		coneButton.setRotation(90);
		coneButton.setPosition(2000,50);
		coneButton.getLabel().setFontScale(2f);





		circleButton.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button up");
				buttonPressed = false;
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button down");
				buttonPressed = true;
				currentShape = "Circle";
				return true;
			}
		});


		squareButton.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button up");
				buttonPressed = false;
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button down");
				buttonPressed = true;
				currentShape = "Square";
				return true;
			}
		});


		coneButton.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button up");
				buttonPressed = false;
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button down");
				buttonPressed = true;
				currentShape = "Triangle";
				return true;
			}
		});

		stage.addActor(circleButton);
		stage.addActor(squareButton);
		stage.addActor(coneButton);


		stage.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("up");
				CustomShape c = shapes.get(shapes.size()-1);
				c.expanding = false;
				//radius = 0;
				//shapeRenderer = new ShapeRenderer();
				//shapeRenderers.add(new ShapeRenderer());
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(buttonPressed == false) {
					System.out.println("down");

					CustomShape newShape;

					if(currentShape == "Circle"){
						newShape = new Circle(x, y, 0, true);
					}
					else if (currentShape == "Square") {
						newShape = new Square(x, y, 0, 0, true);
					}else{
						newShape = new Cone(x, y, 0, 0, 0, true);
					}

					shapes.add(newShape);
					return true;
				}
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


		for(CustomShape s: shapes){
			s.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			s.shapeRenderer.setColor(1, 1, 0, 1);

			s.draw();
			s.expand();



			s.shapeRenderer.end();
		}

		stage.act();
		stage.draw();

	}
	
	@Override
	public void dispose () {

	}
}
