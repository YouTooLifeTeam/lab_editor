package com.youtoolife.lab_editor.screens;


import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.youtoolife.lab_editor.LabEditor;
import com.youtoolife.lab_editor.objects.Box;
import com.youtoolife.lab_editor.utils.Assets;

public class MainMenu extends ScreenAdapter {
	Texture img;
	Stage stage;
	Skin skin;
	TextArea textArea;
	TextField fileField, xField, yField;
	TextButton swipeBtn, saveBtn, loadBtn, copyBtn, editSize, clearAll, generateBtn, centerBtn;
	LabEditor game;
	public static OrthographicCamera guiCam;
	int xSize, ySize;
	
	public static Array<Box> leds;

	public MainMenu (LabEditor game) {
		this.game = game;
		
		leds = new Array<Box>();
		
		guiCam = new OrthographicCamera(1024, 768);
		guiCam.position.set(1024 / 2, 768 / 2, 0);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage(new StretchViewport(1024, 768));
		
		Gdx.input.setInputProcessor(stage);
		
		swipeBtn = new TextButton("^^^", skin);
		swipeBtn.setWidth(100);
		swipeBtn.setHeight(30);
		swipeBtn.setText("^^^");
		stage.addActor(swipeBtn);
		///
		fileField = new TextField("LED.c", skin);
		fileField.setWidth(200);
		fileField.setHeight(30);
		fileField.setPosition(130, 250 + 30);
		fileField.setVisible(false);
		stage.addActor(fileField);
		///
		saveBtn = new TextButton("SAVE", skin);
		saveBtn.setWidth(100);
		saveBtn.setHeight(30);
		saveBtn.setPosition(130, 250);
		saveBtn.setVisible(false);
		stage.addActor(saveBtn);
		///
		loadBtn = new TextButton("LOAD", skin);
		loadBtn.setWidth(100);
		loadBtn.setHeight(30);
		loadBtn.setPosition(230, 250);
		loadBtn.setVisible(false);
		stage.addActor(loadBtn);
		///
		copyBtn = new TextButton("CopyToClipboard", skin);
		copyBtn.setWidth(200);
		copyBtn.setHeight(30);
		copyBtn.setPosition(360, 250);
		copyBtn.setVisible(false);
		stage.addActor(copyBtn);
		
		textArea = new TextArea("LED[][] = {{}};", skin);
		textArea.setPosition(0, 0);
		textArea.setWidth(1024);
		textArea.setHeight(250);
		textArea.setVisible(false);
		stage.addActor(textArea);
		
		xField = new TextField("8", skin);
		xField.setWidth(50);
		xField.setHeight(30);
		xField.setPosition(0, 768 - 30);
		stage.addActor(xField);
		yField = new TextField("8", skin);
		yField.setWidth(50);
		yField.setHeight(30);
		yField.setPosition(60, 768 - 30);
		stage.addActor(yField);
		
		xSize = ySize = 0;
		
		editSize = new TextButton("EDIT SIZE", skin);
		editSize.setWidth(100);
		editSize.setHeight(30);
		editSize.setPosition(120, 768 - 30);
		//editSize.setVisible(false);
		stage.addActor(editSize);
		clearAll = new TextButton("CLEAR ALL", skin);
		clearAll.setWidth(100);
		clearAll.setHeight(30);
		clearAll.setPosition(230, 768 - 30);
		//editSize.setVisible(false);
		stage.addActor(clearAll);
		generateBtn = new TextButton("GENERATE", skin);
		generateBtn.setWidth(100);
		generateBtn.setHeight(30);
		generateBtn.setPosition(340, 768 - 30);
		//editSize.setVisible(false);
		stage.addActor(generateBtn);
		
		swipeBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if (swipeBtn.getY() == 0) {
					swipeBtn.setPosition(0, 250);
					swipeBtn.setText("X");
					textArea.setVisible(true);
					saveBtn.setVisible(true);
					loadBtn.setVisible(true);
					copyBtn.setVisible(true);
					fileField.setVisible(true);
				}
				else
				{
					swipeBtn.setPosition(0, 0);
					swipeBtn.setText("^^^");
					textArea.setVisible(false);
					saveBtn.setVisible(false);
					loadBtn.setVisible(false);
					copyBtn.setVisible(false);
					fileField.setVisible(false);
				}
			}
		});
		saveBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				OutputStreamWriter myfile = null;
				try {
					try {
						myfile = new OutputStreamWriter( new FileOutputStream(fileField.getText()),"KOI8-R");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myfile.write(textArea.getText());
					myfile.close();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		copyBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				copyToSystemClipboard(textArea.getText());
			}
		});
		editSize.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				createLeds();
			}
		});
		clearAll.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				leds.clear();
				xField.setText("0");
				yField.setText("0");
				xSize = ySize = 0;
			}
		});
		generateBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				ledToBin();
			}
		});
		createLeds();
		
	}
	
	public static void copyToSystemClipboard(String str) {
	    StringSelection ss = new StringSelection(str);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

	public void update (float delta) {
		inputHandler(delta);
		for (int i = 0; i < leds.size; i++) {
			leds.get(i).update(delta);
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glEnable(GL20.GL_BLEND);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		gl.glClearColor(0.20f, 0.19f, 0.19f, 1.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		
		for (int i = 0; i < leds.size; i++) {
			leds.get(i).render(game.batcher);	
		}
		
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		
		game.batcher.end();
		
		game.batcher.enableBlending();
		game.batcher.begin();

		game.batcher.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	public void ledToBin() {
		swipeBtn.setPosition(0, 250);
		swipeBtn.setText("X");
		textArea.setVisible(true);
		saveBtn.setVisible(true);
		loadBtn.setVisible(true);
		copyBtn.setVisible(true);
		fileField.setVisible(true);
		for (int i = 0; i < 100; i++) {
			textArea.setText(textArea.getText()+"10\n");
		}
		boolean[][] arr = new boolean[ySize][xSize];
		for (int i = 0; i < leds.size; i++) {
			arr[leds.get(i).getY()][leds.get(i).getI()] = leds.get(i).getState();
		}
		textArea.clear();
		textArea.setText("boolean["+ySize+"]["+xSize+"] = {\n");
		for (int y = 0; y < ySize; y++) {
			textArea.setText(textArea.getText()+"{");
			for (int i = 0; i < xSize; i++) {
				textArea.setText(textArea.getText()+(arr[y][i]?"1":"0"));
				if (i != (xSize-1)) textArea.setText(textArea.getText()+",");
			}
			textArea.setText(textArea.getText()+"}");
			if (y != (ySize-1)) textArea.setText(textArea.getText()+",\n");
		}
		textArea.setText(textArea.getText()+"\n}");
	}
	
	public void createLeds() {
		if (xSize < Integer.parseInt(xField.getText())) {
			for (int i = (xSize>0?(xSize-1):0); i < Integer.parseInt(xField.getText()); i++) {
				for (int y = 0; y < Integer.parseInt(yField.getText()); y++) {
					Box led = new Box(Assets.cileRegion,new Vector2(212+i*75,609-y*75), Assets.cileRegion.getRegionWidth(), Assets.cileRegion.getRegionHeight(), i, y);
					leds.add(led);
				}
			}
		}
		if (ySize < Integer.parseInt(yField.getText())) {
			for (int i = 0; i < xSize; i++) {
				for (int y = (ySize>0?(ySize-1):0); y < Integer.parseInt(yField.getText()); y++) {
					Box led = new Box(Assets.cileRegion,new Vector2(212+i*75,609-y*75), Assets.cileRegion.getRegionWidth(), Assets.cileRegion.getRegionHeight(), i, y);
					leds.add(led);
				}
			}
		}

		if (xSize > Integer.parseInt(xField.getText()) || ySize > Integer.parseInt(yField.getText())) {
			
			Array<Box> delLeds = new Array<Box>();
			
			for (int i = 0; i < leds.size; i++) {
				if (leds.get(i).getI() >= Integer.parseInt(xField.getText())) {
					delLeds.add(leds.get(i));
					//leds.removeIndex(i);
				}
				if (leds.get(i).getY() >= Integer.parseInt(yField.getText())) {
					//leds.removeIndex(i);
					delLeds.add(leds.get(i));
				}
			}
			
			for (int i = 0; i < delLeds.size; i++) {
				leds.removeValue(delLeds.get(i), true);
			}
			delLeds.clear();
		}
		xSize = Integer.parseInt(xField.getText());
		ySize = Integer.parseInt(yField.getText());
	}
	
	public void inputHandler(float delta) {
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			System.exit(0);
		}
		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			
		}
		int speed = 350;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)||Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			speed = 1500;
		}
		else
		{
			speed = 350;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			guiCam.position.x = guiCam.position.x + speed * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			guiCam.position.x = guiCam.position.x - speed * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			guiCam.position.y = guiCam.position.y + speed * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			guiCam.position.y = guiCam.position.y - speed * delta;
		}
	}
	

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause () {
	}
}
