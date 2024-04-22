package com.mygdx.imageeditor;
import java.io.IOException;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.imageeditor.Button.ButtonState;

public class InputManager implements InputProcessor{
	public Array<Button> Buttons = new Array<Button>();
	public static InputManager Instance; //singleton 
	private IHoverable _currentlyHovered;  
	//i added _currenltyclicked b/s there were no instructions 
	private IClickable _currentlyClicked; 
	
	public Array<IClickable> Clickable = new Array<>();
	public Array<IHoverable> Hoverables = new Array<>();

	
	
	
	public InputManager(){
		Instance = this; 
	}
	private boolean _controlPressed;
	public boolean keyDown(int keycode) {
		if(keycode == Keys.CONTROL_LEFT) {
			System.out.println("YOU PRESSED CONTROL!");
		}
		
		if(_controlPressed && keycode == Keys.S) {
			System.out.println("YOU PRESSED CONTROL + S!");
			try {ImageInputOutput.Instance.saveImage("/Users/emelysandoval/Desktop/test.bmp");}
			catch (IOException e) {e.printStackTrace();}
		}
		if(keycode == Keys.CONTROL_LEFT) {
			_controlPressed = true;
		}
		return false;
	}
	public boolean keyUp(int keycode) {
		if(keycode == Keys.CONTROL_LEFT) _controlPressed = false;
		return false;}
	public boolean keyTyped(char character) {return false;}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector2 vector2 = new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY); 
		IClickable collision = CollisionManager.Instance.getClicked(vector2);

		if(collision != null) {
		collision.onClickDown(vector2); 
		}
		//setting variable here b/c this is were you would click down 
		_currentlyClicked = collision; 
		
		return true;}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 vector2 = new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY); 
//		Button collision = CollisionManager.Instance.getCollision(vector2);
//		if(_currentlyHovered != null) _currentlyHovered.onClickUp(vector2); 
 		if(_currentlyClicked != null)_currentlyClicked.onClickUp(vector2); 
		return true;//ch5 false b4
		}
	
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {return false;}
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouseMoved(screenX, screenY); 

		if(_currentlyClicked != null) {
			_currentlyClicked.onClickDragged(new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY));
		}
		return true;}//ch5 faslse b4
	
	
	public boolean mouseMoved(int screenX, int screenY) {
		IHoverable collision = CollisionManager.Instance.getHovered(
				new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY));
		
		if(_currentlyHovered != null && _currentlyHovered != collision ) _currentlyHovered.onHoverExit();
		if(collision != null)  collision.onHovered();
		_currentlyHovered = collision;	
		return true;
		
//		IHoverable collision = CollisionManager.Instance.getCollision(new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY));
//		if(collision != null) collision.onHovered(); 
//		if(_currentlyHovered != null && !_currentlyHovered.equals(collision)) {
//			_currentlyHovered.onHoverExit();
//		}
//		_currentlyHovered = collision; 
//		return true;
		}	
	public boolean scrolled(float amountX, float amountY) {return false;}
}