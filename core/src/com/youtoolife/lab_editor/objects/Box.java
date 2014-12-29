package com.youtoolife.lab_editor.objects;

import com.badlogic.gdx.Gdx;
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
	String type = "";
	String img = "";

  public Box(TextureRegion textureRegion, Vector2 position, float w, float h, int i, int y) {		
	this.texture = textureRegion;
    this.position = position;
    this.height = 50;
    this.width = 50;
    this.i = i;
    this.y = y;
  }
  
  
  public void update(float delta) {
	  handleInput(delta);
  }
  
  public void handleInput(float delta) {
	  if (Gdx.input.justTouched()) {
		  
		  float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight(), 
					dw = w/1024, dh = h/764, 
					cX = Gdx.input.getX(), cY = Gdx.input.getY(),
					x = cX/dw, y = 764 - cY/dh;
		  
		  if (contains(x+MainMenu.guiCam.position.x-512, y+MainMenu.guiCam.position.y-384)) {
			  if (!turn_on) {
			  setTexture(new TextureRegion(MainMenu.images.get(MainMenu.idImg).getTexture()), 
					  MainMenu.images.get(MainMenu.idImg).getTexture().getWidth(), 
					  MainMenu.images.get(MainMenu.idImg).getTexture().getHeight());
			  this.type = MainMenu.currentType;
			  this.img = MainMenu.currentImg;
			  turn_on = true;
			  } 
			  else
			  {
				  setTexture(Assets.rectRegion, Assets.rectRegion.getRegionWidth(), Assets.rectRegion.getRegionHeight());
				  turn_on = false;
				  this.type = "";
				  this.img = "";
				  this.width = 50;
				  this.height = 50;
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
