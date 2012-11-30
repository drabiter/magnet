package com.coffeefury.magnet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.OnActionCompleted;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.ArrayMap;
import com.coffeefury.magnet.Magnet;
import com.coffeefury.magnet.systems.InputSystem;

public abstract class AbstractScreen implements Screen {
	public static final int GAME_VIEWPORT_WIDTH = 480,
			GAME_VIEWPORT_HEIGHT = 320;
	
	private static ArrayMap<String, NinePatch> nines = new ArrayMap<String, NinePatch>();

	protected final Magnet game;
	protected final Stage stage;
	
	protected InputSystem inputSystem;

	private BitmapFont font;
	private SpriteBatch batch;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;
	
	protected boolean pauseAct;
	protected boolean stopMusic;

	public AbstractScreen(Magnet game) {
		this.game = game;
		this.stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		this.pauseAct = false;
	}
	
	public final Stage getStage(){
		return stage;
	}

	public BitmapFont getFont() {
		if (font == null) font = new BitmapFont();
		return font;
	}

	public SpriteBatch getBatch() {
		if (batch == null) batch = new SpriteBatch();
		return batch;
	}

	public TextureAtlas getAtlas() {
		if (atlas == null) atlas = new TextureAtlas(Gdx.files.internal("atlas/atlas.atlas"));
		return atlas;
	}

	public Skin getSkin() {
		if (skin == null) {
			FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
			skin = new Skin(skinFile, Gdx.files.internal("skin/uiskin.png"));
		}
		return skin;
	}

	protected Table getTable() {
		if (table == null) {
			table = new Table(getSkin());
			table.setFillParent(true);
			stage.addActor(table);
		}
		return table;
	}
	
	public TextureRegion getTextureRegion(String name){
		return new TextureRegion(getAtlas().findRegion(name));
	}
	
	public NinePatch getNinePatch(String name){
		NinePatch ninePatch = nines.get(name);
		if (ninePatch == null){
			nines.put(name, new NinePatch(getTextureRegion(name)));
		}
		return ninePatch;
	}

	public InputSystem getInputSystem() {
		return inputSystem;
	}
	
	// Screen implementation

	@Override
	public void show() {
		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);
//		stage.clear();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render(float delta) {
		// (1) process the game logic

		// update the actors
		if (!pauseAct) stage.act(delta);

		// (2) draw the result

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		pauseAct = true;
	}

	@Override
	public void resume() {
		pauseAct = false;
	}

	@Override
	public void dispose() {
		if (font != null)
			font.dispose();
		if (batch != null)
			batch.dispose();
		if (skin != null)
			skin.dispose();
		if (atlas != null)
			atlas.dispose(); 
		
		if (stopMusic)
			game.getMusicManager().stop();
	}
	
	public void notified(int id){
		
	}
	
	protected void fadeIn(float time){
		stage.getRoot().color.a = 0f;
		stage.getRoot().action(FadeIn.$(time).setCompletionListener(new OnActionCompleted() {
			
			@Override
			public void completed(Action action) {
				// TODO Auto-generated method stub
				fadedIn();
			}
		}));
	}

	protected void fadedIn() {
		// TODO Auto-generated method stub
		
	}
	
	protected void fadeOut(float time, boolean resetAlpha){
		if (resetAlpha)
			stage.getRoot().color.a = 1f;
		stage.getRoot().action(FadeOut.$(time).setCompletionListener(new OnActionCompleted() {
			
			@Override
			public void completed(Action action) {
				// TODO Auto-generated method stub
				fadedOut();
			}
		}));
	}

	protected void fadedOut() {
		// TODO Auto-generated method stub
		
	}

}
