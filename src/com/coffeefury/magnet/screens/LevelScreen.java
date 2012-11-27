package com.coffeefury.magnet.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.coffeefury.magnet.Magnet;
import com.coffeefury.magnet.utils.Constants;

public class LevelScreen extends AbstractScreen {

	public LevelScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	private static final int ROW = 5;

	private static final String LEVEL = "Level ";

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();

		Table table = getTable().center();

		int lvlNum = Constants.TOTAL_LEVEL;

		for (int i = 1; i <= lvlNum; i++) {
			if (i > ROW)
				table.row();
			TextButton lvl = new TextButton(LEVEL + i, getSkin().getStyle(
					"default", TextButtonStyle.class), String.valueOf(i));
			lvl.setClickListener(new ClickListener() {

				@Override
				public void click(Actor actor, float x, float y) {
					// TODO Auto-generated method stub
					Constants.level = Integer.parseInt(actor.name);
					fadeOut(2f, false);
				}
			});
			table.add(lvl).pad(20);
		}
		TextButton back = new TextButton("Back to Menu", getSkin());
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
