package com.mygdx.imageeditor;

import com.badlogic.gdx.math.Vector2;

public interface IHoverable {
	public void onHovered();
	public void onHoverExit();
	public void onClickUp(Vector2 vector2); 
}