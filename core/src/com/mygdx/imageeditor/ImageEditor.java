package com.mygdx.imageeditor;

import java.io.IOException;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class ImageEditor extends ApplicationAdapter {
	SpriteBatch batch;
	public Vector2 ScreenSize;
	public static ImageEditor Instance; //singleton 
	public Array<Rec2D> Rectangles = new Array<Rec2D>();
	private EditWindow _editWindow; 
	
	public void create () {
		Util.testIntToSignedBytes();
		Instance = this; 
		new ImageInputOutput();
		//I had to add the absolute path, It wouldn't save into the assets folder 
		//Pixmap editMap = ImageInputOutput.Instance.loadImage("/Users/emelysandoval/Documents/ImageEditor/assets/blackbuck.bmp");
		
	
		InputManager inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);
		batch = new SpriteBatch();
		ScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Vector2 editWindowSize = new Vector2(500, ScreenSize.y - 50);
		_editWindow = new EditWindow(editWindowSize, new Vector2(ScreenSize.x - editWindowSize.x, 0));//), editTexture
		
		Button button = new Button(new Vector2(50,50), Vector2.Zero, Color.YELLOW);
		CollisionManager collisionManager = new CollisionManager();
		}
	    
		
	public void render () {
		ScreenUtils.clear(0f, 0f, 0f, 1);
		batch.begin();
		//batch.draw(button1.RecTexture, button1.Position.x, button1.Position.y);
		Rec2D rec;
		for(int i = 0; i < Rectangles.size; i++) {
		rec = Rectangles.get(i);
		batch.draw(rec.RecTexture, rec.Position.x, rec.Position.y, rec.Scale.x, rec.Scale.y);
		}
		batch.draw(_editWindow.DoodleTexture, _editWindow.Position.x,
				_editWindow.Position.y, _editWindow.Scale.x, _editWindow.Scale.y);
		batch.end();
		}
	public void dispose () {
		batch.dispose();
	}
	public void filesImported(String[] filePaths) {
		Pixmap map = ImageInputOutput.Instance.loadImage(filePaths[0]);
		if(map == null) return;
		_editWindow.RecTexture = new Texture(map);
	}

}
