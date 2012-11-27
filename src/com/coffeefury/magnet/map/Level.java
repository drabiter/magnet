package com.coffeefury.magnet.map;

import com.badlogic.gdx.utils.Array;

public class Level{
	
	private Array<Entity> entities = null;

	public Array<Entity> getEntities() {
		return entities;
	}

	public void setEntities(Array<Entity> entities) {
		this.entities = entities;
	}

}
