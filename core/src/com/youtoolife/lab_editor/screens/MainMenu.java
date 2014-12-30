package com.youtoolife.lab_editor.screens;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	
	Stage stage;
	Skin skin;
	TextArea textArea;
	TextField fileField, typeCField, xField, yField;
	TextButton swipeBtn, saveBtn, loadBtn, copyBtn, editSize, clearAll, generateBtn, centerBtn;
	
	Array<Label> labels = new Array<Label>();
	Array<Sprite> types = new Array<Sprite>();
	public static Array<Sprite> images = new Array<Sprite>();
	Array<String> imageNames = new Array<String>();
	
	public static String currentType = "", currentImg = "";
	public static int idImg = 0, idType = 0;
	
	LabEditor game;
	
	ShapeRenderer shapeRender = new ShapeRenderer();
	
	public static OrthographicCamera guiCam;
	int xSize, ySize;
	
	public static Array<Box> holsts;

	public MainMenu (LabEditor game) {
		this.game = game;
		
		holsts = new Array<Box>();
		
		guiCam = new OrthographicCamera(1024, 768);
		guiCam.position.set(1024 / 2, 768 / 2, 0);
		
		createGui();
		
		refreshTypes();
		
		createHolsts();
		
	}
	
	public void createGui() {
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage(new StretchViewport(1024, 768));
		
		Gdx.input.setInputProcessor(stage);
		
		swipeBtn = new TextButton("^^^", skin);
		swipeBtn.setWidth(100);
		swipeBtn.setHeight(30);
		swipeBtn.setText("^^^");
		stage.addActor(swipeBtn);
		///
		fileField = new TextField("Untitled.chunk", skin);
		fileField.setWidth(200);
		fileField.setHeight(30);
		fileField.setPosition(130, 250 + 30);
		fileField.setVisible(false);
		stage.addActor(fileField);
		////
		typeCField = new TextField("SingleExit", skin);
		typeCField.setWidth(200);
		typeCField.setHeight(30);
		typeCField.setPosition(130, 250 + 30+31);
		typeCField.setVisible(false);
		stage.addActor(typeCField);
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
		
		textArea = new TextArea("Maze Editor ver. alpha by YouTooLife Team (c)\n<------------------>\n\n", skin);
		textArea.setPosition(0, 0);
		textArea.setWidth(1024);
		textArea.setHeight(250);
		textArea.setVisible(false);
		stage.addActor(textArea);
		
		xField = new TextField("12", skin);
		xField.setWidth(50);
		xField.setHeight(30);
		xField.setPosition(0, 768 - 30);
		stage.addActor(xField);
		yField = new TextField("12", skin);
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
					typeCField.setVisible(true);
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
					typeCField.setVisible(false);
				}
			}
		});
		saveBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//textArea.setText(textArea.getText()+"\n"+"");
				createChunk();
			}
		});
		copyBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				copyToSystemClipboard(textArea.getText());
			}
		});
		editSize.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				createHolsts();
			}
		});
		clearAll.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				holsts.clear();
				xField.setText("0");
				yField.setText("0");
				xSize = ySize = 0;
			}
		});
		generateBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				createChunk();
			}
		});
	}
	
	
	public void refreshTypes() {
		labels.clear();
		types.clear();
		images.clear();
		FileHandle file = Gdx.files.local("Types");
		System.out.println(file.isDirectory());
		if (file.isDirectory()) {
		FileHandle[] files = file.list();
		System.out.println(files.length);
		for (int i = 0; i < files.length; i++) {
			if (!files[i].name().contains(".")) {
				Sprite sprite = new Sprite(Assets.field);
				sprite.setSize(100, 20);
				sprite.setPosition(10, 764-25*i-100);
				types.add(sprite);
				Label label = new Label(files[i].name(), skin);
				label.setPosition(sprite.getX()+sprite.getWidth()/2-label.getWidth()/2, sprite.getY()+sprite.getHeight()/2-label.getHeight()/2);
				labels.add(label);
				stage.addActor(labels.get(labels.size-1));
			}
		}
		currentType = labels.get(0).getText().toString();
		System.out.println(currentType);
		idType = 0;
		}
		refreshImages(currentType);
	}
	
	
	public void refreshImages(String type) {
		images.clear();
		imageNames.clear();
		FileHandle file = Gdx.files.local("Types/"+type);
		System.out.println(file.isDirectory());
		if (file.isDirectory()) {
		FileHandle[] files = file.list();
		System.out.println(files.length);
		int posX = 0, posY = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].name().contains(".png")
					||files[i].name().contains(".PNG")
					||files[i].name().contains(".jpg")
					||files[i].name().contains(".JPG")) {
				System.out.println(files[i]);
				Sprite sprite = new Sprite(new Texture(files[i]));
				sprite.setSize(50, 50);
				sprite.setPosition(1024-50*3-5*3+55*posX, 764-55*posY -150);
				images.add(sprite);
				imageNames.add(files[i].nameWithoutExtension());
				System.out.println(files[i].nameWithoutExtension());
				posX++;
				if (posX > 2) { posX = 0; posY++; }
			}
		}
		currentImg = imageNames.get(0);
		System.out.println(currentImg);
		idImg = 0;
		}
	}
	
	public static void copyToSystemClipboard(String str) {
	    StringSelection ss = new StringSelection(str);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

	public void update (float delta) {
		inputHandler(delta);
		if (swipeBtn.getY() == 0)
		for (int i = 0; i < holsts.size; i++) {
			holsts.get(i).update(delta);
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glEnable(GL20.GL_BLEND);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		gl.glClearColor(0.20f, 0.19f, 0.19f, 1.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		
		for (int i = 0; i < holsts.size; i++) {
			holsts.get(i).render(game.batcher);
		}
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		
		game.batcher.end();
		
		game.batcher.enableBlending();
		game.batcher.begin();
		for (Sprite sprite:types) {
			sprite.draw(game.batcher);
		}
		for (Sprite sprite:images) {
			sprite.draw(game.batcher);
		}
		game.batcher.end();
		
		shapeRender.setProjectionMatrix(guiCam.combined);
		shapeRender.begin(ShapeType.Line);
		shapeRender.setColor(0.f, 1.f, 0.f, 0.f);
		shapeRender.box(types.get(idType).getX(), types.get(idType).getY(), 0, 
				types.get(idType).getWidth(), types.get(idType).getHeight(), 0);
		shapeRender.box(images.get(idImg).getX(), images.get(idImg).getY(), 0, 
				images.get(idImg).getWidth(), images.get(idImg).getHeight(), 0);
		shapeRender.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	public void createChunk() {
		swipeBtn.setPosition(0, 250);
		swipeBtn.setText("X");
		textArea.setVisible(true);
		saveBtn.setVisible(true);
		loadBtn.setVisible(true);
		copyBtn.setVisible(true);
		fileField.setVisible(true);
		typeCField.setVisible(true);
		
		
		DocumentBuilder builder = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      try { builder = factory.newDocumentBuilder(); }
	      catch (ParserConfigurationException e) { e.printStackTrace(); }
	      
	      Document doc = builder.newDocument();
	      Element RootElement=doc.createElement("Chunk");
	      RootElement.setAttribute("name", fileField.getText().substring(0, fileField.getText().indexOf(".")));
	      RootElement.setAttribute("type", typeCField.getText());
	 
	        for (int i = 0; i < holsts.size; i++) {
	        		Element NameElementTitle=doc.createElement("Block");
	        		//NameElementTitle.appendChild(doc.createTextNode("true"));
	        		NameElementTitle.setAttribute("type", holsts.get(i).type);
	        		NameElementTitle.setAttribute("img", holsts.get(i).img);
	        		NameElementTitle.setAttribute("x", String.valueOf((holsts.get(i).getI())));
	        		NameElementTitle.setAttribute("y", String.valueOf((holsts.get(i).getY())));
	        		RootElement.appendChild(NameElementTitle);
			} 
	        doc.appendChild(RootElement);
	 
	        Transformer t = null;
			try {
				t = TransformerFactory.newInstance().newTransformer();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
	        	t.setOutputProperty(OutputKeys.METHOD, "xml");
	        	t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileField.getText())));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        textArea.setText(textArea.getText()+"\n"+"File '"+fileField.getText()+"' has been created!");
		/*textArea.clear();
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
		textArea.setText(textArea.getText()+"\n}");*/
	}
	
	public void createHolsts() {
		if (xSize < Integer.parseInt(xField.getText())) {
			for (int i = (xSize>0?(xSize-1):0); i < Integer.parseInt(xField.getText()); i++) {
				for (int y = 0; y < Integer.parseInt(yField.getText()); y++) {
					Box led = new Box(Assets.rectRegion,new Vector2(212+i*50,609-y*50), Assets.rectRegion.getRegionWidth(), Assets.rectRegion.getRegionHeight(), i, y);
					holsts.add(led);
				}
			}
		}
		if (ySize < Integer.parseInt(yField.getText())) {
			for (int i = 0; i < xSize; i++) {
				for (int y = (ySize>0?(ySize-1):0); y < Integer.parseInt(yField.getText()); y++) {
					Box led = new Box(Assets.rectRegion,new Vector2(212+i*50,609-y*50), Assets.rectRegion.getRegionWidth(), Assets.rectRegion.getRegionHeight(), i, y);
					holsts.add(led);
				}
			}
		}

		if (xSize > Integer.parseInt(xField.getText()) || ySize > Integer.parseInt(yField.getText())) {
			
			Array<Box> delholsts = new Array<Box>();
			
			for (int i = 0; i < holsts.size; i++) {
				if (holsts.get(i).getI() >= Integer.parseInt(xField.getText())) {
					delholsts.add(holsts.get(i));
					//holsts.removeIndex(i);
				}
				if (holsts.get(i).getY() >= Integer.parseInt(yField.getText())) {
					//holsts.removeIndex(i);
					delholsts.add(holsts.get(i));
				}
			}
			
			for (int i = 0; i < delholsts.size; i++) {
				holsts.removeValue(delholsts.get(i), true);
			}
			delholsts.clear();
		}
		xSize = Integer.parseInt(xField.getText());
		ySize = Integer.parseInt(yField.getText());
	}
	
	public void inputHandler(float delta) {
		
		if (Gdx.input.justTouched()) {
			
			float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight(), 
			dw = w/1024, dh = h/764, 
			cX = Gdx.input.getX(), cY = Gdx.input.getY(),
			x = cX/dw, y = 764 - cY/dh;
			
			//StretchViewport svp = new StretchViewport(w, h);
			
			//System.out.println(x+" - "+y);
			for (Sprite sprite:types)
			if (sprite.getBoundingRectangle().contains(x,y)) {
				idType = types.indexOf(sprite, false);
				currentType = labels.get(idType).getText().toString();
				System.out.println(currentType);
				refreshImages(currentType);
			}
		}
		
		if (Gdx.input.justTouched()) {
			   
			float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight(), 
			dw = w/1024, dh = h/764, 
			cX = Gdx.input.getX(), cY = Gdx.input.getY(),
			x = cX/dw, y = 764 - cY/dh;
			
			//System.out.println(x+" - "+y);
			for (Sprite sprite:images)
			if (sprite.getBoundingRectangle().contains(x,y)) {
				idImg = images.indexOf(sprite, false);
				currentImg = imageNames.get(idImg);
				System.out.println(currentImg);
			}
		}
		
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
	
	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height);
		System.out.println(stage.getViewport().getViewportWidth());
	}
}
