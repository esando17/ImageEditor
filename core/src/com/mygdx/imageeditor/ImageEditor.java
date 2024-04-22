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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.buttons.Button;
import com.mygdx.buttons.ClearDoodleButton;
import com.mygdx.buttons.ColorButton;
import com.mygdx.buttons.ExitButton;
import com.mygdx.buttons.SaveButton;

import Utility.CollisionManager;
import Utility.ImageInputOutput;
import Utility.InputManager;

public class ImageEditor extends ApplicationAdapter {
	SpriteBatch batch;
	public Vector2 ScreenSize;
	public static ImageEditor Instance; //singleton 
	public Array<Rec2D> Rectangles = new Array<Rec2D>();
	private EditWindow _editWindow; 
		
	public void create () {
		Instance = this;
		initializeUtilityClasses();
		createGraphicalElements();
		}
	private BitmapFont _font;
	private void initializeUtilityClasses() {
		new CollisionManager();
		new ImageInputOutput();
		InputManager inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);
		_font = new BitmapFont();
	}
	private void createGraphicalElements() {
		ScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Vector2 editWindowSize = new Vector2(500, ScreenSize.y - 50);
		ColorButton button = new ColorButton(new Vector2(42, 42), Vector2.Zero, Color.GOLD);
		ColorButton Orange = new ColorButton(new Vector2(42, 42), new Vector2(42, 0), Color.ORANGE);
		ColorButton white = new ColorButton(new Vector2(42, 42), new Vector2(0, 42), Color.WHITE);
		ColorButton blue = new ColorButton(new Vector2(42, 42), new Vector2(42, 42), Color.BLUE);
		ColorButton red = new ColorButton(new Vector2(42, 42), new Vector2(42, 84), Color.RED);
		ColorButton green = new ColorButton(new Vector2(42, 42), new Vector2(0, 84), Color.GREEN);
		ColorButton pink = new ColorButton(new Vector2(42, 42), new Vector2(0, 126), Color.PINK);
		ColorButton purple = new ColorButton(new Vector2(42, 42), new Vector2(42, 126), Color.PURPLE);
		ColorButton brown = new ColorButton(new Vector2(42, 42), new Vector2(0, 168), Color.BROWN);
		ColorButton lime = new ColorButton(new Vector2(42, 42), new Vector2(42, 168), Color.LIME);
		ColorButton yellow = new ColorButton(new Vector2(42, 42), new Vector2(42, 210), Color.YELLOW);
		SaveButton save = new SaveButton(new Vector2(75,25), new Vector2(0, ScreenSize.y - 25), Color.GRAY);
		ExitButton exit = new ExitButton(new Vector2(75,25), new Vector2(80,ScreenSize.y-25), Color.GRAY);
		ClearDoodleButton clear = new ClearDoodleButton(new Vector2(75,25), new Vector2(160,ScreenSize.y-25), Color.GRAY);
		batch = new SpriteBatch();
		_editWindow = new EditWindow(editWindowSize, new Vector2(ScreenSize.x - editWindowSize.x, 0));
	}
		    
		
	public void render () {
		ScreenUtils.clear(0f, 0f, 0f, 1);
		batch.begin();
		Rec2D rec;
		for(int i = 0; i < Rectangles.size; i++) {
			rec = Rectangles.get(i);
			batch.draw(rec.RecTexture, rec.Position.x, rec.Position.y, rec.Scale.x, rec.Scale.y);
		}
		batch.draw(_editWindow.DoodleTexture, _editWindow.Position.x,
				_editWindow.Position.y, _editWindow.Scale.x, _editWindow.Scale.y);
		for(int i = 0; i < Rectangles.size; i++) {
			rec = Rectangles.get(i);
			batch.draw(rec.Outline.OutlineTex, rec.Position.x, rec.Position.y, rec.Scale.x, rec.Scale.y);
			}
		for(int i = 0; i < Rectangles.size; i++) {
			rec = Rectangles.get(i);
			if(rec instanceof Button) {
			Button button = (Button) rec;
			if(button.ButtonText == null) continue;
			_font.draw(batch, button.ButtonText, button.Position.x, button.Position.y + button.Scale.y * 0.75f,
			button.Scale.x, Align.center, false);
			}
		}
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
