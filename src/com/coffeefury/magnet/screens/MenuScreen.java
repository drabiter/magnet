package com.coffeefury.magnet.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.coffeefury.magnet.Magnet;

public class MenuScreen extends AbstractScreen {

	public MenuScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		Table table = getTable().right().padRight(100);
		
		TextButton select = new TextButton("Select Levels", getSkin());
		select.setClickListener(new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.getLevelScreen());
			}
		});
		table.add(select).right();
		table.row();
		
		TextButton option = new TextButton("Options", getSkin());
		option.setClickListener(new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				
			}
		});
		table.add(option).right();
		
		fadeIn(1.5f);
	}

}
