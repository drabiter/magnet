package com.coffeefury.magnet.systems;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.coffeefury.magnet.components.Terrain;
import com.coffeefury.magnet.screens.AbstractScreen;

public class TerrainSystem extends Group implements System{
	
	public enum Type{
		PLAIN;
	}
	
	AbstractScreen screen;

	public TerrainSystem(AbstractScreen screen) {
		super("terrainsystem");
		this.screen = screen;
	}
	
	public void createPlain(float x, float y){
		Terrain terrain = new Terrain(screen.getTextureRegion("terrain_l,l"), Type.PLAIN, x, y);
		this.addActor(terrain);
	}
}
