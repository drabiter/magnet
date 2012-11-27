package com.coffeefury.magnet.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.coffeefury.magnet.utils.Constants;

public class Tile extends Image {
	
	public Tile(TextureRegion region, float x, float y){
		super(region);
		if (region == null) Gdx.app.log(null, "null");
		this.x = x;
		this.y = y;
		this.width = this.height = Constants.SIZE;
		this.color.a = .3f;
	}

}
