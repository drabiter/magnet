package com.coffeefury.magnet.systems;

import com.coffeefury.magnet.screens.GameScreen;

public class GameInputSystem extends InputSystem {
	
	private GameScreen game;

	public GameInputSystem(GameScreen game) {
		this.game = game;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		if (enabled) game.touchUp(x, y);
		return super.touchUp(x, y, pointer, button);
	}

}
