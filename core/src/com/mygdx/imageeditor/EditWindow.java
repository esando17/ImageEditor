package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EditWindow extends Rec2D implements IClickable{	
	public static EditWindow Instance;
	public Texture DoodleTexture;
	public Pixmap DoodleMap; 
	private Vector2 _previousPaintPosition;
	public EditWindow(Vector2 scale, Vector2 position) {
		super(scale, position, Color.GRAY);
		DoodleMap = new Pixmap((int) scale.x, (int) scale.y, Format.RGBA8888);
		DoodleMap.setColor(Color.ORANGE);
		DoodleTexture = new Texture(DoodleMap);
		InputManager.Instance.Clickable.add(this); 
		Instance = this; 

	}

	public void onClickDown(Vector2 clickPosition) {
		DoodleMap.drawPixel((int) (clickPosition.x) - (int) (Position.x) , (int) (Position.y) - (int) (clickPosition.y) );
		DoodleTexture = new Texture(DoodleMap);
		if(_previousPaintPosition == null)
			_previousPaintPosition = new Vector2(clickPosition.x - Position.x,Scale.y - clickPosition.y);
		paintAtPosition(clickPosition);
	}
	@Override
	public void onClickUp(Vector2 mousePosition) {
		_previousPaintPosition = null;
		DoodleTexture = new Texture(DoodleMap);//ch5

	}
	@Override
	public void onClickDragged(Vector2 clickPosition) {
		paintAtPosition(clickPosition);
		DoodleTexture = new Texture(DoodleMap);//ch5

		}
	private void paintAtPosition(Vector2 worldPosition) {
		Vector2 paintPosition = new Vector2(worldPosition.x - Position.x,Scale.y - worldPosition.y);
		int startX = (int) _previousPaintPosition.x;
		int startY = (int)_previousPaintPosition.y;
		int endX = (int) paintPosition.x;
		int endY = (int) paintPosition.y;
		DoodleMap.drawLine(startX, startY, endX, endY);
		//draw to the left and right 
		DoodleMap.drawLine(startX + 1, startY, endX + 1, endY);
		DoodleMap.drawLine(startX - 1, startY, endX - 1, endY);
		//draw to the top and bottom 
		DoodleMap.drawLine(startX, startY+1, endX , endY+ 1);
		DoodleMap.drawLine(startX, startY - 1, endX , endY);
		_previousPaintPosition = paintPosition;		
		DoodleTexture = new Texture(DoodleMap);
		}
		
}