package com.coffeefury.magnet.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.coffeefury.magnet.Magnet;
import com.coffeefury.magnet.utils.Constants;

public class PassingScreen extends AbstractScreen {

	public PassingScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();

		String text = "STAGE ".concat(String.valueOf(Constants.level)).concat(
				"\n- click here to start -");
		TextButton next = new TextButton(text, getSkin());
		next.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				fadeOut(2f, false);
			}
		});
		getTable().add(next).center();
		
		fadeIn(1.5f);
	}

	@Override
	protected void fadedOut() {
		// TODO Auto-generated method stub
		game.setScreen(game.getGameScreen());
	}

}
