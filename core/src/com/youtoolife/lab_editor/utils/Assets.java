package com.youtoolife.lab_editor.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	public static Texture cile;
	public static TextureRegion cileRegion;
	public static Texture cileRed;
	public static TextureRegion cileRedRegion;
	


	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		
		cile = loadTexture("circle.png");
		cileRegion = new TextureRegion(cile, 0, 0, 75, 75);
		cileRed = loadTexture("circle_red.png");
		cileRedRegion = new TextureRegion(cileRed, 0, 0, 75, 75);
		
		//music01 = Gdx.audio.newMusic(Gdx.files.internal("01.mp3"));
		//music01.setLooping(true);
	}

}
