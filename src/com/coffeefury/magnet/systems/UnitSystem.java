package com.coffeefury.magnet.systems;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.coffeefury.magnet.components.Unit;
import com.coffeefury.magnet.map.Entity;
import com.coffeefury.magnet.map.Level;
import com.coffeefury.magnet.screens.AbstractScreen;
import com.coffeefury.magnet.utils.Constants;
import com.coffeefury.magnet.utils.UtilsBase.Facing;

public class UnitSystem extends Group implements System {

	static int unitCount = 0;

	/**
	 * The Vector2 key use Java2D Y coordinate aka mouse click.
	 */
	HashMap<String, Vector2> units;

	public enum AttackType {
		FIELDER, NULL, CLONE, ;
	}

	public enum Type {
		PULLER("puller_l,l", AttackType.FIELDER, true), PUSHER("pusher_l,l",
				AttackType.FIELDER, true), CLONER("cloner",
				AttackType.CLONE, true), SHEEP("sheep_l,l", AttackType.NULL,
				false), FINISH("target_l,l", AttackType.NULL, false), ;

		private String texture;
		private boolean playable;
		private AttackType atkType;

		private Type(String texture, AttackType atkType, boolean playable) {
			this.texture = texture;
			this.playable = playable;
			this.atkType = atkType;
		}

		public String getTexture() {
			return texture;
		}

		public boolean isPlayable() {
			return playable;
		}

		public AttackType getAtkType() {
			return atkType;
		}

	}

	AbstractScreen screen;

	public UnitSystem(AbstractScreen screen) {
		super("unitsystem");
		this.screen = screen;

		units = new HashMap<String, Vector2>();
		unitCount = 0;
	}

	/**
	 * @param type
	 * @param x
	 * @param y
	 *            - already on GL coord
	 */
	public void createUnit(Type type, float x, float y) {
		unitCount++;
		float xn = x - (x % Constants.SIZE);
		float yn = y - (y % Constants.SIZE);
		Unit unit = new Unit(screen.getTextureRegion(type.getTexture()), type,
				unitCount, xn, yn);
		units.put(unit.name, new Vector2(xn, Gdx.graphics.getHeight() - yn));
		this.addActor(unit);

		Gdx.app.log("", unit.name + " " + xn + " " + yn);
	}

	public Unit findByLocation(float x, float y) {
		return findByLocation(x, y, false, false, true, true);
	}

	public Unit findByLocation(float x, float y, boolean playable) {
		return findByLocation(x, y, playable, false, false, false);
	}

	public Unit findByLocation(float x, float y, boolean dontCare1,
			boolean dontCare2) {
		return findByLocation(x, y, false, false, dontCare1, dontCare2);
	}

	public Unit findByLocation(float x, float y, boolean playable,
			boolean played, boolean dontCare1, boolean dontCare2) {
		Unit unit = null;
		for (Entry<String, Vector2> maps : units.entrySet()) {
			Vector2 v = maps.getValue();
			if (x >= v.x && y <= v.y && x < v.x + Constants.SIZE
					&& y > v.y - Constants.SIZE) {
				unit = (Unit) this.findActor(maps.getKey());
				return ((unit.playable == playable || dontCare1) && (unit.played == played || dontCare2)) ? unit
						: null;
			}
		}
		return unit;
	}

	public void adjustUnitPosition(Unit lastUnit, Unit unit, Facing facing) {
		if (lastUnit.getAtkType() == AttackType.CLONE)
			return;
		int direction = (lastUnit.type == Type.PULLER) ? -1 : 1;
		float sx = facing.xd * Constants.SIZE;
		float sy = facing.yd * Constants.SIZE;
		float px = lastUnit.x + (sx * lastUnit.HIT);
		float py = lastUnit.y + (sy * lastUnit.HIT);
		Gdx.app.log("s p", sx + " " + sy + " " + px + " " + py);
		Gdx.app.log("l u", lastUnit.x + " " + lastUnit.y + " " + unit.x + " "
				+ unit.y);
		while (unit.x != px || unit.y != py) {
			Unit existed = findByLocation(unit.x + sx * direction,
					Gdx.graphics.getHeight() - ((unit.y + sy * direction)));
			Gdx.app.log("look up", (unit.x + sx * direction) + " "
					+ (Gdx.graphics.getHeight() - (unit.y + sy * direction)));
			if (existed == null) {
				unit.x += sx * direction;
				unit.y += sy * direction;
				Gdx.app.log("new pos", unit.x + " " + unit.y);
			}else if (existed != null && !existed.playable && !unit.playable){
				unit.x += sx * direction;
				unit.y += sy * direction;
				break;
			}else{
				Gdx.app.log("exist", existed.name);
				break;
			}
		}
		updateUnitPosition(unit);
		unit.shake();
		Gdx.app.log(unit.name, unit.x + " " + unit.y);
	}

	/**
	 * @param unit
	 *            remember to change back the Y unit to 2D. Doh!
	 */
	public void updateUnitPosition(Unit unit) {
		Vector2 v = units.get(unit.name);
		v.x = unit.x;
		v.y = Gdx.graphics.getHeight() - unit.y;
	}

	public void updateUnitPosition(String name, float x, float y) {
		Vector2 v = units.get(name);
		v.x = x;
		v.y = Gdx.graphics.getHeight() - y;
	}

	public void resetPlayableUnits() {
		for (int i = 0; i < immutableChildren.size(); i++) {
			((Unit) immutableChildren.get(i)).reset();
		}
	}

	public void isPlayableUnitsDone() {
		// TODO Auto-generated method stub
		boolean allPlayed = true;
		Unit unit;
		for (int i = 0; i < immutableChildren.size(); i++) {
			unit = ((Unit) immutableChildren.get(i));
			if (unit.playable)
				allPlayed = allPlayed && unit.played;
		}
		if (allPlayed) {
			((HUDSystem) screen.getStage().findActor("hudsystem"))
					.showTurnNotif();
		}
	}

	public void playedAll() {
		// TODO Auto-generated method stub
		Unit unit;
		for (int i = 0; i < immutableChildren.size(); i++) {
			unit = ((Unit) immutableChildren.get(i));
			if (unit.playable)
				unit.played();
		}
		((HUDSystem) screen.getStage().findActor("hudsystem")).showTurnNotif();
	}

	public void create(Level level) {
		// TODO Auto-generated method stub
		for (Entity e : level.getEntities()) {
			Type type = Type.valueOf(e.unitName);
			createUnit(type, e.x, e.y);
		}
	}

	public boolean checkWin() {
		// TODO Auto-generated method stub
		Vector2 sheep = units.get("SHEEP1");
		Vector2 finish = units.get("FINISH2");
		return sheep.equals(finish);
	}

	private static Type cloneQueue = null;

	public static Type getCloneQueue() {
		return cloneQueue;
	}

	public static void setCloneQueue(Type cloneQueue) {
		UnitSystem.cloneQueue = cloneQueue;
	}

}
