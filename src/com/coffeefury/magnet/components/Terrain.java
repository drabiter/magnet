package com.coffeefury.magnet.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.coffeefury.magnet.systems.TerrainSystem.Type;
import com.coffeefury.magnet.utils.Constants;

public class Terrain extends Image{

	Type type;
	
	public Terrain(TextureRegion region, Type type, float x, float y){
		super(region);
		this.x = x;
		this.y = y;
		this.type = type;
		this.width = this.height = Constants.SIZE;
	}
	
}
