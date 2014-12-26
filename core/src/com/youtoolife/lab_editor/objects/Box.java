package com.youtoolife.lab_editor.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtoolife.lab_editor.screens.MainMenu;
import com.youtoolife.lab_editor.utils.Assets;

public class Box {

	TextureRegion texture;
	
	Vector2 position = new Vector2();
	float width, height;
	int i, y;
	boolean turn_on;

  public Box(TextureRegion textureRegion, Vector2 position, float w, float h, int i, int y) {		
	this.texture = textureRegion;
    this.position = position;
    this.height = h;
    this.width = w;
    this.i = i;
    this.y = y;
  }
  
  
  public void update(float delta) {
	  handleInput(delta);
  }
  
  public void handleInput(float delta) {
	  if (Gdx.input.justTouched()) {
		  if (contains(Gdx.input.getX()+MainMenu.guiCam.position.x-512, 768-Gdx.input.getY()+MainMenu.guiCam.position.y-384)) {
			  if (!turn_on) {
			  setTexture(Assets.cileRedRegion, Assets.cileRedRegion.getRegionWidth(), Assets.cileRedRegion.getRegionHeight());
			  turn_on = true;
			  } 
			  else
			  {
				  setTexture(Assets.cileRegion, Assets.cileRegion.getRegionWidth(), Assets.cileRegion.getRegionHeight());
				  turn_on = false;  
			  }
		  }
		}
  }
	
	public void render(SpriteBatch sb) {
		sb.enableBlending();
		sb.begin();
		sb.draw(
				texture,
				position.x,
				position.y,
				width,
				height
				);
		sb.end();
	}
	
	
	
	public boolean contains(float x, float y) {
		if (x >= position.x && x <= position.x+width && y >= position.y && y <= position.y+height)
			return true;
		else
			return false;
	}

	public void setPosition(Vector2 pos) {
		position = pos;
	}
	
	public void setTexture(TextureRegion tex, float w, float h) {
		this.texture = tex;
		this.width = w;
		this.height = h;
	}

  public Vector2 getPosition() {
    return position;
  }
  
  public float getWidth() {
	    return width;
  }
  
  public float getHeight() {
	    return height;
  }
  
  public int getI() {
	    return i;
  }
  
  public int getY() {
	    return y;
  }
  public boolean getState() {
	    return turn_on;
  }


}
