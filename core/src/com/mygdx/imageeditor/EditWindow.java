package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EditWindow extends Rec2D implements IClickable{	
	public Texture DoodleTexture;
	private Pixmap _doodleMap;
	
	public EditWindow(Vector2 scale, Vector2 position, Color backgroundColor) {
		super(scale, position, backgroundColor);
		_doodleMap = new Pixmap((int) scale.x, (int) scale.y, Format.RGBA8888);
		_doodleMap.setColor(Color.ORANGE);
		DoodleTexture = new Texture(_doodleMap);
		InputManager.Instance.Clickable.add(this); 
	}
	
	public void onClickDown(Vector2 clickPosition) {
		_doodleMap.drawPixel((int) (clickPosition.x) - (int) (Position.x) , (int) (Position.y) - (int) (clickPosition.y) );
		DoodleTexture = new Texture(_doodleMap);

		paintAtPosition(clickPosition);
	}
	@Override
	public void onClickUp(Vector2 mousePosition) {
		
	}
	@Override
	public void onClickDragged(Vector2 clickPosition) {
		paintAtPosition(clickPosition);
		}
	private void paintAtPosition(Vector2 worldPosition) {
		_doodleMap.drawPixel((int) (worldPosition.x - Position.x),(int) (Scale.y - worldPosition.y));
		DoodleTexture = new Texture(_doodleMap);
		}
		
}