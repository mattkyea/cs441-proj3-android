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

	//setting up variables
	private Stage stage;//what we interact with on screen
	ArrayList<CustomShape> shapes = new ArrayList<>();//store all shapes to draw
	boolean buttonPressed = false;//prevent us from drawing shapes on top of buttons (Could have come up with a cleaner solution)

	//used to see what shape we should draw next
	//only 3 options, so an enum seems like a good choice
	enum Shape {
		CIRCLE,
		SQUARE,
		ELLIPSE
	}

	Shape currentShape = Shape.CIRCLE;//draw circle to start


	//I wanted to keep my code a bit neater than normal, and saw a good opportunity for some OOP

	//CustomShape is the base class
	//its abstract because it doesn't define an actual shape (e.g. circle, square, ellipse)
	public abstract class CustomShape{
		//but all CustomShapes will have these values
		public ShapeRenderer shapeRenderer;
		public float x, y;
		boolean expanding = false;

		//we initalize those values in the constructor
		CustomShape(float x, float y, boolean expanding){
			this.x = x;
			this.y = y;
			this.expanding = expanding;
			shapeRenderer = new ShapeRenderer();
		}

		//and all CustomShapes will have these functions implemented as well
		public abstract void draw();
		public abstract void  expand();

	}

	//first CustomShape implemented class - for a cicle
	public class Circle extends CustomShape{

		//circles have a radius
		public float r;

		Circle(float x, float y,  float r, boolean expanding){
			super(x, y, expanding);
			this.r = r;
		}

		//draw function will use shapeRenderer.circle
		@Override
		public void draw() {
			shapeRenderer.circle(x, y, r);
		}

		//and expand will cause the radius to grow
		@Override
		public void expand() {
			if(expanding == true) r++;
		}

	}

	//second CustomShape implemented class, for a square
	public class Square extends CustomShape{

		//square has width and height
		public float w, h;

		Square(float x, float y,  float w, float h, boolean expanding){
			super(x, y, expanding);
			this.w = w;
			this.h = h;
		}

		//draw as a rectangle
		@Override
		public void draw() {
			shapeRenderer.rect(x, y, w, h);
		}

		//expand width and height
		@Override
		public void expand() {
			if(expanding == true){ w++; h++;}
		}

	}

	//final class, for ellipse
	public class Ellipse extends CustomShape{

		//ellipse has a radius and height
		public float r, h;
		//we'll also use this boolean in expand
		public boolean flipFlop = false;

		Ellipse(float x, float y, float r, float h, boolean expanding){
			super(x, y, expanding);
			this.r = r;
			this.h = h;
		}

		//draw ellipse
		@Override
		public void draw() {
			shapeRenderer.ellipse(x, y, r, h);
		}

		//expand ellipse
		//however, incrementing height and radius together just creates a circle
		//instead, only expand the radius by 1 for every other time we expand the height
		//i.e. height grows at m = 1
		//radius grows at m = .5
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

	//now to set everything up, mostly buttons and input handling
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		//set up buttons
		//starts with a skin - a style/look for the buttons
		//found this one along with others at https://github.com/czyzby/gdx-skins
		Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

		//I have a small bug that I couldn't fix - the bottom of these buttons don't work
		//gives me something to look into next time I work with libGDX

		//set up the 3 buttons, all very similar except for text and position

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

		TextButton ellipseButton = new TextButton("Ellipse", mySkin);
		ellipseButton.setTransform(true);
		ellipseButton.setSize(200,200);
		ellipseButton.setRotation(90);
		ellipseButton.setPosition(2000,50);
		ellipseButton.getLabel().setFontScale(2f);


		//each button gets a listener as well, which sets buttonPressed (to ensure we don't draw over a button)
		//and sets currentShape so we know what shape to construct

		circleButton.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("button up");
				buttonPressed = false;
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("button down");
				buttonPressed = true;
				currentShape = Shape.CIRCLE;
				return true;
			}
		});


		squareButton.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("button up");
				buttonPressed = false;
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("button down");
				buttonPressed = true;
				currentShape = Shape.SQUARE;
				return true;
			}
		});


		ellipseButton.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("button up");
				buttonPressed = false;
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("button down");
				buttonPressed = true;
				currentShape = Shape.ELLIPSE;
				return true;
			}
		});

		//add all buttons to stage
		stage.addActor(circleButton);
		stage.addActor(squareButton);
		stage.addActor(ellipseButton);


		//now the input listener for the stage/screen itself
		//there's probably a better approach that ignores button presses, but I couldn't figure it out
		stage.addListener(new InputListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("up");
				//stop expanding the current shape (last entry in array)
				CustomShape c = shapes.get(shapes.size()-1);
				c.expanding = false;
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//make a new shape (based on last button press)
				//and add it to the array so we can start drawing it
				if(buttonPressed == false) {
					//System.out.println("down");
					CustomShape newShape;
					if(currentShape == Shape.CIRCLE){
						newShape = new Circle(x, y, 0, true);
					}
					else if (currentShape == Shape.SQUARE) {
						newShape = new Square(x, y, 0, 0, true);
					}else{
						newShape = new Ellipse(x, y, 0, 0, true);
					}
					shapes.add(newShape);
					return true;
				}
				return false;
			}

		});

	}

	//because of everything we took care of above, render is pretty simple
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//for each shape in our array
		for(CustomShape s: shapes){
			//set type and color (would have been better to add to CustomShape)
			s.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			s.shapeRenderer.setColor(1, 1, 0, 1);
			s.draw();//draw it
			s.expand();//expand it (only done if its the current shape)
			s.shapeRenderer.end();
		}
		stage.act();
		stage.draw();
	}

}
