package com.coffeefury.magnet.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.coffeefury.magnet.Magnet;

public class PassingScreen extends AbstractScreen {

	public PassingScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		stage.addActor(new Image(getTextureRegion("background_l,l")));
		
		Table table = getTable();
		
		table.add(new Image(getTextureRegion("win_l,l")));
		table.row();

		Image next = new Image(getTextureRegion("next_l,l"));
		next.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				fadeOut(2f, false);
			}
		});
		table.add(next).center().spaceTop(150);
		table.row();
		
		Image back = new Image(getTextureRegion("back_l,l"));
		back.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.getMenuScreen());
			}
		});
		table.add(back).center();
		
		fadeIn(1.5f);
	}

	@Override
	protected void fadedOut() {
		// TODO Auto-generated method stub
		game.setScreen(game.getGameScreen());
	}

}
