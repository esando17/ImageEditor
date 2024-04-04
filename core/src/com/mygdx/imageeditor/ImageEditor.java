package com.mygdx.imageeditor;

import java.util.Vector;

//import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class ImageEditor extends ApplicationAdapter {
	SpriteBatch batch;
	
	Button button1;
	Button button2; 
	Button button3;
	Button button4;
	Button button5;

	public Vector2 ScreenSize;
	public static ImageEditor Instance; //singleton 
	
	public void create () {
		Instance = this; 
		//what is this?
		batch = new SpriteBatch();
		ScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		InputManager inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);
		Vector2 rectangleScale = new Vector2(100,50);
		button1 = new Button(
				rectangleScale,
				new Vector2(ScreenSize.x / 2f + 146f, ScreenSize.y / 2f + 108f),
				Color.BLUE);
		
		//
		button2 = new Button(
				rectangleScale,
				new Vector2(ScreenSize.x / 2f - 222f, ScreenSize.y / 2f + 108f),
				Color.RED);
//		
		button3 = new Button(
				rectangleScale,
				new Vector2(ScreenSize.x / 2f - 222f, ScreenSize.y / 2f - 132f),
				Color.ORANGE);
		button4 = new Button(
				rectangleScale,
				new Vector2(ScreenSize.x / 2f + 146f, ScreenSize.y / 2f - 132f),
				Color.GREEN);
		
		//center button 
		button5 = new Button( 
				rectangleScale,
				new Vector2(ScreenSize.x/2f-50f, ScreenSize.y/2-25f),
				Color.WHITE);
		CollisionManager collisionManager = new CollisionManager();
	    Rec2D collisionRec = collisionManager.getCollision(ScreenSize);
		}
	    
		
	public void render () {
		ScreenUtils.clear(0f, 0f, 0f, 1);
		batch.begin();
		batch.draw(button1.RecTexture, button1.Position.x, button1.Position.y);
		batch.draw(button2.RecTexture, button2.Position.x, button2.Position.y);
		batch.draw(button3.RecTexture, button3.Position.x, button3.Position.y);
		batch.draw(button4.RecTexture, button4.Position.x, button4.Position.y);
		batch.draw(button5.RecTexture, button5.Position.x, button5.Position.y);
		batch.end();
		}

}
