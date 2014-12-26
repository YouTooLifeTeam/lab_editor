package com.youtoolife.lab_editor;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.youtoolife.lab_editor.screens.MainMenu;
import com.youtoolife.lab_editor.utils.Assets;

public class LabEditor extends Game {
	
public SpriteBatch batcher;
	
	@Override
	public void create () {
		batcher = new SpriteBatch();
		Assets.load();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public static void copyToSystemClipboard(String str) {
	    StringSelection ss = new StringSelection(str);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}
}
