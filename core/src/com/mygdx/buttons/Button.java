package com.mygdx.buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.imageeditor.Rec2D;

import Utility.IClickable;
import Utility.IHoverable;
import Utility.InputManager;

public class Button extends Rec2D implements IClickable, IHoverable {
	public String ButtonText;
	protected Color _startColor; 
	private Color _hoveredColor; 
	public enum ButtonState {Clicked, Hovered, None};
	private ButtonState _state;

	public Button(Vector2 scale, Vector2 position, Color recColor) {
		super(scale, position, recColor);
		_startColor = recColor; 
		_hoveredColor =  new Color(_startColor.r / 2f, _startColor.g / 2f, _startColor.b / 2f, 1);
		_state = ButtonState.None; 
		InputManager.Instance.Buttons.add(this);
		InputManager.Instance.Clickable.add(this); 
		InputManager.Instance.Hoverables.add(this); 

		
		
	}
	
	public void onClickUp(Vector2 clickPosition){
		_state = ButtonState.Hovered; //added
		_recColor = _hoveredColor; 
		generateTexture();
	}
	
	public void onClickDown(Vector2 clickPosition) { //previously onPressed();  
		if(_state == ButtonState.Clicked)return ; 
		_state = ButtonState.Clicked; //
		_recColor = new Color(_startColor.r / 4f, _startColor.g / 4f , _startColor.b / 4f, 1);	
		generateTexture(); 
	}
	
	
	public void onHovered() {
		if(_state == ButtonState.Clicked || _state == ButtonState.Hovered)return; //
		_state = ButtonState.Hovered; //
		_recColor = _hoveredColor;		
		generateTexture(); 
	}
	
	public void onHoverExit() {
		_recColor = _startColor;
		_state = ButtonState.None; //
		generateTexture();
		}
	@Override
	public void onClickDragged(Vector2 clickPosition) {
	// TODO Auto-generated method stub
	}

}

