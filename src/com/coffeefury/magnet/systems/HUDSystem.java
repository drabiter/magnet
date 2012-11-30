package com.coffeefury.magnet.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.coffeefury.magnet.screens.AbstractScreen;
import com.coffeefury.magnet.utils.Constants;

public class HUDSystem extends Group implements System {
	
	private static final float ALPHA = .6f;

	AbstractScreen screen;
	
	Image cloneIcon;
	Image clone;
	Image level;

	public HUDSystem(AbstractScreen screen) {
		super("hudsystem");
		this.screen = screen;
		
		level = new Image(screen.getTextureRegion("lv_".concat(String.valueOf(Constants.level)).concat("_l,l")));
		level.y = Gdx.graphics.getHeight() - level.getPrefHeight();
		
		clone = new Image(screen.getTextureRegion("nextclone_l,l"));
		clone.x = Gdx.graphics.getWidth() - (clone.getPrefWidth() + Constants.SIZE + 10);
		clone.y = Gdx.graphics.getHeight() - clone.getPrefHeight();
		cloneIcon = new Image(screen.getTextureRegion("cloner_l,l"));
		cloneIcon.x = clone.x + clone.getPrefWidth() + 10;
		cloneIcon.y = Gdx.graphics.getHeight() - Constants.SIZE;
		
		color.a = ALPHA;

		this.addActor(level);
		this.addActor(cloneIcon);
		this.addActor(clone);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if (UnitSystem.getCloneQueue() != null){
			cloneIcon.setRegion(screen.getTextureRegion(UnitSystem.getCloneQueue().getTexture()));
		}else{
			cloneIcon.setRegion(null);
		}
	}
}
