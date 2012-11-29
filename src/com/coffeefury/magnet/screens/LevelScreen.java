package com.coffeefury.magnet.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Scaling;
import com.coffeefury.magnet.Magnet;
import com.coffeefury.magnet.utils.Constants;

public class LevelScreen extends AbstractScreen {

	public LevelScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	private static final int ROW = 5;

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		stage.addActor(new Image(getTextureRegion("titel_l,l")));

		Table table = getTable().bottom();

		int lvlNum = Constants.TOTAL_LEVEL;

		for (int i = 1; i <= lvlNum; i++) {
			if (i > ROW) table.row();
			String name = String.valueOf(i);
			Image lvl = new Image(getTextureRegion("lv_".concat(name).concat("_l,l")), Scaling.stretch, Align.CENTER, name);
			lvl.setClickListener(new ClickListener() {

				@Override
				public void click(Actor actor, float x, float y) {
					// TODO Auto-generated method stub
					Constants.level = Integer.parseInt(actor.name);
					fadeOut(2f, false);
				}
			});
			table.add(lvl).pad(10);
		}
		Image back = new Image(getTextureRegion("back_l,l"));
		back.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.getMenuScreen());
			}
		});
		table.row();
		table.add(back).pad(20).spaceBottom(100).colspan(lvlNum);

		fadeIn(1.5f);
	}

	@Override
	protected void fadedOut() {
		// TODO Auto-generated method stub
		game.setScreen(game.getGameScreen());
	}

}
