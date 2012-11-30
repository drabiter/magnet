package com.coffeefury.magnet.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.coffeefury.magnet.Magnet;
import com.coffeefury.magnet.audio.SoundManager.GameSound;

public class LosingScreen extends AbstractScreen {

	public LosingScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		stage.addActor(new Image(getTextureRegion("background_l,l")));
		
		Table table = getTable();
		
		table.add(new Image(getTextureRegion("lose_l,l")));
		table.row();
		
		Image back = new Image(getTextureRegion("back_l,l"));
		back.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				game.getSoundManager().play(GameSound.CONFIRM);
				game.setScreen(game.getMenuScreen());
			}
		});
		table.add(back).center().spaceTop(50);
		
		fadeIn(1.5f);
	}

	@Override
	protected void fadedOut() {
		// TODO Auto-generated method stub
		game.setScreen(game.getGameScreen());
	}

}
