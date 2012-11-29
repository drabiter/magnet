package com.coffeefury.magnet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.coffeefury.magnet.Magnet;
import com.coffeefury.magnet.audio.MusicManager.GameMusic;

public class MenuScreen extends AbstractScreen {

	public MenuScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		Image bg = new Image(getTextureRegion("titel_l,l"));
		getStage().addActor(bg);
		
		Image play = new Image(getTextureRegion("play_l,l"));
		play.x = (Gdx.graphics.getWidth() - play.getPrefWidth()) / 2;
		play.y = Gdx.graphics.getHeight() * .25f;
		play.setClickListener(new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.getLevelScreen());
			}
		});
		getStage().addActor(play);
		
//		Table table = getTable().right().padRight(100);
//		
//		TextButton select = new TextButton("Select Levels", getSkin());
//		select.setClickListener(new ClickListener() {
//			
//			@Override
//			public void click(Actor actor, float x, float y) {
//				// TODO Auto-generated method stub
//				game.setScreen(game.getLevelScreen());
//			}
//		});
//		table.add(select).right();
//		table.row();
//		
//		TextButton credit = new TextButton("Credits", getSkin());
//		credit.setClickListener(new ClickListener() {
//			
//			@Override
//			public void click(Actor actor, float x, float y) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		table.add(credit).right();
		
		fadeIn(1.5f);
		
		game.getMusicManager().play(GameMusic.BG01);
		game.getMusicManager().setVolume(.5f);
	}

}
