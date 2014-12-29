package com.youtoolife.lab_editor.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	public static Texture rect;
	public static TextureRegion rectRegion;
	public static Texture field;
	public static TextureRegion fieldRegion;
	


	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		
		rect = loadTexture("rect.png");
		rectRegion = new TextureRegion(rect, 0, 0, 75, 75);
		field = loadTexture("field.png");
		fieldRegion = new TextureRegion(field, 0, 0, 75, 75);
		
		//music01 = Gdx.audio.newMusic(Gdx.files.internal("01.mp3"));
		//music01.setLooping(true);
	}

}
