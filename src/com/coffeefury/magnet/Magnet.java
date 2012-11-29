package com.coffeefury.magnet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.coffeefury.magnet.audio.MusicManager;
import com.coffeefury.magnet.audio.SoundManager;
import com.coffeefury.magnet.screens.GameScreen;
import com.coffeefury.magnet.screens.LevelScreen;
import com.coffeefury.magnet.screens.MenuScreen;
import com.coffeefury.magnet.screens.PassingScreen;

public class Magnet extends Game {
	
	private SoundManager soundManager;
	private MusicManager musicManager;
	
	@SuppressWarnings("unused")
	private FPSLogger fpsLogger;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		soundManager = new SoundManager();
		musicManager = new MusicManager();
		
		fpsLogger = new FPSLogger();
		
		setScreen(getMenuScreen());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		
		musicManager.dispose();
		soundManager.dispose();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		
//		fpsLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		
		if (getScreen() == null) setScreen(getMenuScreen());
	}

	public GameScreen getGameScreen(){
		return new GameScreen(this);
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public MusicManager getMusicManager() {
		return musicManager;
	}

	public PassingScreen getPassingScreen() {
		// TODO Auto-generated method stub
		return new PassingScreen(this);
	}
	
	public LevelScreen getLevelScreen() {
		// TODO Auto-generated method stub
		return new LevelScreen(this);
	}
	
	public MenuScreen getMenuScreen() {
		// TODO Auto-generated method stub
		return new MenuScreen(this);
	}
	
}
