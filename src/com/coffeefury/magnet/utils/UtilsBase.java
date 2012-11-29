package com.coffeefury.magnet.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.coffeefury.magnet.components.Unit;
import com.coffeefury.magnet.map.Level;
import com.coffeefury.magnet.systems.TerrainSystem;

public class UtilsBase {
	
	public enum Facing{
		N(0, 1), E(1, 0), S(0, -1), W(-1, 0),
		NE(1, 1), ES(1, -1), SW(-1, -1), WN(-1, 1);
		
		public int xd, yd;
		
		private Facing(int x, int y){
			xd = x;
			yd = y;
		}
	}
	
	public static void createTerrain(TerrainSystem terrainSystem){
		for (int i = 0; i < Constants.TILE_W; i++){
			for (int j = 0; j < Constants.TILE_H; j++){
				terrainSystem.createPlain(i * Constants.SIZE, j * Constants.SIZE);
			}
		}
	}
	
	public static Facing getFacing(Unit unit, Unit target){
		if (unit.x < target.x) return Facing.E;
		else if (unit.x > target.x) return Facing.W;
		else if (unit.y < target.y) return Facing.N;
		else return Facing.S;
	}
	
	public static Level loadLevel(int lvl){
		Json json = new Json();
//		json.addClassTag("entity", Entity.class);
//		json.addClassTag("entities", ArrayList.class);
		return json.fromJson(Level.class, Gdx.files.internal("maps/" + lvl + ".json"));
	}

}
